package lp2g36.biblioteca;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import lp2g36.biblioteca.*;

public class BibliotecaGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable tabelaUsr, tabelaLivro;
    private Biblioteca biblioteca;
    private JPanel inputPanelUsuario, inputPanelLivro;
    private JTextField campoNome, campoCPF, campoEndereco, campoSobrenome, campoDataNasc, campoTitulo, campoCodLivro, campoQNTDCop, campoGeneroLivro;

    public BibliotecaGUI(Biblioteca biblioteca) {
        setTitle("Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.biblioteca = biblioteca;

        tabbedPane = new JTabbedPane();

        tabelaUsr = new JTable();
        tabelaLivro = new JTable();

        DefaultTableModel modeloUsr = new DefaultTableModel(new Object[]{"Nome", "Data nascimento", "CPF", "Endereco"}, 0);
        tabelaUsr.setModel(modeloUsr);

        TableRowSorter<DefaultTableModel> ordenarUsr = new TableRowSorter<>(modeloUsr);
        tabelaUsr.setRowSorter(ordenarUsr);

        DefaultTableModel modeloLivro = new DefaultTableModel(new Object[]{"Codigo", "Categoria", "Titulo", "Quantidade de Copias"}, 0);
        tabelaLivro.setModel(modeloLivro);

        TableRowSorter<DefaultTableModel> ordenarLivro = new TableRowSorter<>(modeloLivro);
        tabelaLivro.setRowSorter(ordenarLivro);


        tabbedPane.addTab("Usuarios", new JScrollPane(tabelaUsr));
        mostrarPainelUsuario(); 

        JButton salvarUsuariosButton = new JButton("Salvar Usuarios");
        salvarUsuariosButton.addActionListener(e -> salvarUsuarios());
        inputPanelUsuario.add(salvarUsuariosButton);

        tabbedPane.addTab("Livros", new JScrollPane(tabelaLivro));

        JLabel painelInstrucoes = new JLabel("Para ordenar as tabelas, clique nos cabecalhos das colunas.");
        painelInstrucoes.setHorizontalAlignment(SwingConstants.CENTER);
        add(painelInstrucoes, BorderLayout.NORTH);

        add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addChangeListener(new ChangeListener() {
            private boolean botaoSalvarLivro = false;

            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane.getSelectedIndex();
                if (index == 0) { 
                    mostrarPainelUsuario();
                } else if (index == 1) { 
                    mostrarPainelLivro();
                    
                    if (!botaoSalvarLivro) {
                        JButton salvarLivrosButton = new JButton("Salvar Livros");
                        salvarLivrosButton.addActionListener(l -> salvarLivros());
                        inputPanelLivro.add(salvarLivrosButton);
                        
                        botaoSalvarLivro = true;
                    }
                }
            }
        });

        carregarDados();

        setVisible(true);
    }

    private void carregarDados() {
        Hashtable<String, Usuario> usuarios = biblioteca.getUsuarios(); 
        DefaultTableModel modeloUsr = (DefaultTableModel) tabelaUsr.getModel();

        for (Usuario usuario : usuarios.values()) {
            Date dataNasc = converterData(usuario.getDiaNasc(), usuario.getMesNasc(), usuario.getAnoNasc());
            String dataNascStr = formatarData(dataNasc);
            modeloUsr.addRow(new Object[]{usuario.getNome()+" "+usuario.getSobreNome(), dataNascStr, usuario.getNumCPF(), usuario.getEndereco()});
        }

        Hashtable<String, Livro> livros = biblioteca.getLivro(); 
        DefaultTableModel modeloLivro = (DefaultTableModel) tabelaLivro.getModel();
        for (Livro livro : livros.values()) {
            modeloLivro.addRow(new Object[]{livro.getCodigoLivro(), livro.getCategoria(), livro.getTitulo(), livro.getQntdCopias()});
        }

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloUsr);
        sorter.setComparator(1, (strData1, strData2) -> {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date data1 = dateFormat.parse((String) strData1);
                Date data2 = dateFormat.parse((String) strData2);
                return data1.compareTo(data2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });
        tabelaUsr.setRowSorter(sorter);
        
    }

    private Date converterData(int dia, int mes, int ano) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return dateFormat.parse(String.format("%02d/%02d/%04d", dia, mes, ano));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void adicionarUsuario() {
        try {
            String nome = campoNome.getText().trim();
            String sobrenome = campoSobrenome.getText().trim();
            int dia = Integer.parseInt(campoDataNasc.getText().trim().substring(0,2));
            int mes = Integer.parseInt(campoDataNasc.getText().trim().substring(3,5));
            int ano = Integer.parseInt(campoDataNasc.getText().trim().substring(6,10));
            long CPF = Long.parseLong(campoCPF.getText().trim());
            String endereco = campoEndereco.getText().trim();

            if (nome.isEmpty() || sobrenome.isEmpty() || !nome.matches("[a-zA-Z]+") || !sobrenome.matches("[a-zA-Z]+")) {
                throw new IllegalArgumentException("Nome invalido para o usuario.");
            }

            if(!ValidaCPF.isCPF(String.valueOf(CPF))){
                throw new IllegalArgumentException("CPF invalido para o usuario.");
            }

            if(!ValidaData.isDataValida(dia, mes, ano)){
                throw new IllegalArgumentException("Data de nascimento invalida para o usuario.");
            }

            Usuario novoUsuario = new Usuario(nome, sobrenome, dia, mes, ano, CPF, endereco);
            biblioteca.cadastraUsuario(novoUsuario);

            DefaultTableModel modeloUsr = (DefaultTableModel) tabelaUsr.getModel();
            modeloUsr.addRow(new Object[]{novoUsuario.getNome()+" "+novoUsuario.getSobreNome(), novoUsuario.getDataNasc(), novoUsuario.getNumCPF(), novoUsuario.getEndereco()});

            campoNome.setText("");
            campoSobrenome.setText("");
            campoDataNasc.setText("");
            campoCPF.setText("");
            campoEndereco.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar usuario: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarLivro() {
        try {
            int codLivro = Integer.parseInt(campoCodLivro.getText().trim());
            String titulo = campoTitulo.getText().trim();
            String genero = campoGeneroLivro.getText().trim().toUpperCase();
            int qntdCop = Integer.parseInt(campoQNTDCop.getText().trim());

            if (titulo.isEmpty() || genero.isEmpty()) {
                throw new IllegalArgumentException("Dados invalidos para o livro.");
            }

            Livro novoLivro = new Livro(codLivro, titulo, genero, qntdCop);
            biblioteca.cadastraLivro(novoLivro);

            DefaultTableModel modeloLivro = (DefaultTableModel) tabelaLivro.getModel();
            modeloLivro.addRow(new Object[]{novoLivro.getCodigoLivro(), novoLivro.getCategoria(), novoLivro.getTitulo(), novoLivro.getQntdCopias()});

            campoTitulo.setText("");
            campoCodLivro.setText("");
            campoGeneroLivro.setText("");
            campoQNTDCop.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel criarPanelUsuario() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        panel.add(new JLabel("Nome do usuario: "));
        campoNome = new JTextField();
        panel.add(campoNome);

        panel.add(new JLabel("Sobrenome do usuario: "));
        campoSobrenome = new JTextField();
        panel.add(campoSobrenome);

        panel.add(new JLabel("Data de nascimento (Formato dd/mm/aaaa): "));
        campoDataNasc = new JTextField();
        panel.add(campoDataNasc);

        panel.add(new JLabel("CPF do usuario(Sem caracteres especiais): "));
        campoCPF = new JTextField();
        panel.add(campoCPF);

        panel.add(new JLabel("Endereço do usuario: "));
        campoEndereco = new JTextField();
        panel.add(campoEndereco);

        JButton addUserButton = new JButton("Adicionar usuario");
        addUserButton.addActionListener(e -> adicionarUsuario());
        panel.add(addUserButton);

        return panel;
    }

    private JPanel criarPanelLivro() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        panel.add(new JLabel("Titulo do livro: "));
        campoTitulo = new JTextField();
        panel.add(campoTitulo);

        panel.add(new JLabel("Codigo do livro: "));
        campoCodLivro = new JTextField();
        panel.add(campoCodLivro);

        panel.add(new JLabel("Categoria do livro: "));
        campoGeneroLivro = new JTextField();
        panel.add(campoGeneroLivro);

        panel.add(new JLabel("Quantidade de copias disponiveis: "));
        campoQNTDCop = new JTextField();
        panel.add(campoQNTDCop);

        JButton addBookButton = new JButton("Adicionar Livro");
        addBookButton.addActionListener(e -> adicionarLivro());
        panel.add(addBookButton);

        return panel;
    }

    private void mostrarPainelUsuario() {
        if (inputPanelLivro != null) {
            inputPanelLivro.setVisible(false);

        }
        if (inputPanelUsuario == null) {
            inputPanelUsuario = criarPanelUsuario();
            add(inputPanelUsuario, BorderLayout.SOUTH);

        } else {
            inputPanelUsuario.setVisible(true);
        }

        revalidate();
        repaint();
    }
    
    private void mostrarPainelLivro() {
        if (inputPanelUsuario != null) {
            inputPanelUsuario.setVisible(false);

        }
        if (inputPanelLivro == null) {
            inputPanelLivro = criarPanelLivro();
            add(inputPanelLivro, BorderLayout.SOUTH);

        } else {
            inputPanelLivro.setVisible(true);
        }

        revalidate();
        repaint();
    }

    private void salvarLivros() {
        try {
            JFileChooser escolheArquivo = new JFileChooser();
            int escolha = escolheArquivo.showSaveDialog(this);
            if (escolha == JFileChooser.APPROVE_OPTION) {
                String nomeArquivo = escolheArquivo.getSelectedFile().getAbsolutePath();
                biblioteca.salvaArquivo("LIVROS", nomeArquivo);
                JOptionPane.showMessageDialog(this, "Livros salvos com sucesso!");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar livros: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarUsuarios() {
        try {
            JFileChooser escolheArquivo = new JFileChooser();
            int escolha = escolheArquivo.showSaveDialog(this);
            if (escolha == JFileChooser.APPROVE_OPTION) {
                String nomeArquivo = escolheArquivo.getSelectedFile().getAbsolutePath();
                biblioteca.salvaArquivo("USUARIOS", nomeArquivo);
                JOptionPane.showMessageDialog(this, "Usuarios salvos com sucesso!");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar usuarios: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatarData(Date data) {
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dataFormatada.format(data);
    }
}