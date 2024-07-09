import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lp2g36.biblioteca.*;

public class P4nX extends JFrame {
    private JTextField usuariosField;
    private JTextField livrosField;

    public P4nX(){
        LocalDateTime tempoDia = LocalDateTime.now();
        LocalTime hora = tempoDia.toLocalTime();

        if (hora.isBefore(LocalTime.NOON)) {
            setTitle("Ola, bom dia, insira o nome dos arquivos correspondentes");
        } else if (hora.isBefore(LocalTime.of(18, 0))) {
            setTitle("Ola, boa tarde, insira o nome dos arquivos correspondentes");
        } else {
            setTitle("Ola, boa noite, insira o nome dos arquivos correspondentes");
        }  

        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel usuariosLabel = new JLabel("Banco de dados de usuarios(Padrao-> 'u.dat'):");
        usuariosField = new JTextField();
        JLabel livrosLabel = new JLabel("Banco de dados de Livros(Padrao-> 'l.dat'):");
        livrosField = new JTextField();

        panel.add(usuariosLabel);
        panel.add(usuariosField);
        panel.add(livrosLabel);
        panel.add(livrosField);

        JButton iniciarButton = new JButton("Carregar biblioteca");
        iniciarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String arquivoUsuarios = usuariosField.getText().trim();
                String arquivoLivros = livrosField.getText().trim();

                try {
                    Biblioteca biblioteca = new Biblioteca(arquivoUsuarios, arquivoLivros);
                    BibliotecaGUI gui = new BibliotecaGUI(biblioteca);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(P4nX.this, "Erro ao carregar arquivos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(iniciarButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new P4nX();
            }
        });
    }
}
