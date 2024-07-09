package lp2g36.biblioteca;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Biblioteca {
    private Hashtable<String, Usuario> cadastroUsuarios;
    private Hashtable<String, Livro> cadastroLivros;
    private boolean restringirAtraso;
    private int maximoDiasComLivro;
    private float valorMulta;
    private GregorianCalendar dataAtual;
    private int maximoNumCop;
    private Properties properties;



    public Biblioteca(String arquivoUsr, String arquivoLivros) throws IOException, ClassNotFoundException {
        valorMulta = 0;
        leArquivo(arquivoUsr, "usuario");
        leArquivo(arquivoLivros,"livro");
        properties = new Properties();
        loadProperties();
    }

    public Biblioteca() {
        this.cadastroUsuarios = new Hashtable<>();
        this.cadastroLivros = new Hashtable<>();
        properties = new Properties();
        loadProperties();
    }

    public void cadastraUsuario(Usuario usuario) throws IllegalArgumentException {
        String id = String.valueOf(usuario.getNumCPF());

        if (cadastroUsuarios.containsKey(id)) {
            throw new IllegalArgumentException("ERRO! Usuário com o mesmo código já existe.");
        }

        cadastroUsuarios.put(id, usuario);
    }

    public void cadastraLivro(Livro livro) throws IllegalArgumentException {
        String id = String.valueOf(livro.getCodigoLivro());

        if (cadastroLivros.containsKey(id)) {
            throw new IllegalArgumentException("ERRO! Livro com o mesmo código já existe.");
        }

        cadastroLivros.put(id, livro);
    }

    public void salvaArquivo(String tipo, String nomeArquivo) throws IOException {
        tipo = tipo.toUpperCase();

        if (tipo.equals("USUARIO") || tipo.equals("USUARIOS")) {
            try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
                writer.writeObject(cadastroUsuarios);
            }

        }else if(tipo.equals("LIVROS") || tipo.equals("LIVRO")){
            try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
                writer.writeObject(cadastroLivros);
            }

        }else {
            throw new IllegalArgumentException("ERRO! Tipo invalido");
        }

    }

    public void leArquivo(String nomeArquivo, String tipo) throws IOException, ClassNotFoundException {
        tipo = tipo.toUpperCase();

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            if (tipo.equals("USUARIO") || tipo.equals("USUARIOS")) {
                this.cadastroUsuarios = (Hashtable<String, Usuario>) reader.readObject();

            } else if (tipo.equals("LIVROS") || tipo.equals("LIVRO")) {
                this.cadastroLivros = (Hashtable<String, Livro>) reader.readObject();

            } else {
                throw new IllegalArgumentException("ERRO! Tipo invalido");
            }
        }
    }

    public Hashtable<String, Livro> getLivro(){
        return cadastroLivros;
    }

    public Hashtable<String, Usuario> getUsuarios() {
        return cadastroUsuarios;
    }

    public String imprimeLivros() {
        List<Map.Entry<String, Livro>> listaOrdenada = new ArrayList<>(this.cadastroLivros.entrySet());
        Collections.sort(listaOrdenada, (livro1, livro2) -> livro1.getKey().compareTo(livro2.getKey()));

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Livro> entry : listaOrdenada) {
            sb.append("\n[" + entry.toString());
        }
        return sb.toString();
    }

    public String imprimeUsuarios() {
        List<Map.Entry<String, Usuario>> listaOrdenada = new ArrayList<>(this.cadastroUsuarios.entrySet());
        Collections.sort(listaOrdenada, (usuario1, usuario2) -> usuario1.getKey().compareTo(usuario2.getKey()));

        StringBuilder auxString = new StringBuilder();

        for (Map.Entry<String, Usuario> entry : listaOrdenada) {
            auxString.append("\n[" + entry.toString());
        }
        return auxString.toString();
    }

    public void emprestaLivro(Usuario usr, Livro livro) throws CopiaNaoDisponivelEX{
        String auxCPF = String.valueOf(usr.getNumCPF());
        dataAtual = new GregorianCalendar();

        if(usr.getCopiasPegas()>=maximoNumCop){
            throw new IllegalArgumentException("Este usuario ja atingiu o numero maximo de " + maximoNumCop +" numero de copias");
        }
        if(restringirAtraso){
            for (Map.Entry<String, Livro> entry : cadastroLivros.entrySet()) {
                Livro auxLivro = entry.getValue();
    
                for (Emprest emprestimo : usr.getHist()) {
                    if (emprestimo.getCodigoLivro() == auxLivro.getCodigoLivro()) {
                        long tempoEmMil1 = auxLivro.getHist(auxCPF).getTimeInMillis();
                        long tempoEmMil2 = dataAtual.getTimeInMillis();
                        long diferencaEmMil = tempoEmMil2-tempoEmMil1;
                        long diferencaEmDias = TimeUnit.MILLISECONDS.toDays(diferencaEmMil);
    
                        if(diferencaEmDias>maximoDiasComLivro){
                            throw new IllegalArgumentException("O usuario nao pode pegar outro livro pois possui um atraso na devolucao do livro: "+auxLivro.getCodigoLivro()+"\n");
                        }
                    }
                }
            }
        }

        livro.empresta();
        livro.addUsuarioHist(auxCPF);
        usr.addLivroHist(livro.getCodigoLivro());
        usr.pegouEmprestado();
    }

    public void devolveLivro(Usuario usr, Livro livro) throws NenhumaCopiaEmprestadaEX{
        String auxCPF = String.valueOf(usr.getNumCPF());
        dataAtual = new GregorianCalendar();

        livro.setHist(auxCPF);
        livro.devolve();
        usr.setHist(livro.getCodigoLivro());
        usr.devolveuOLivro();

        long tempoEmMil1 = livro.getHist(auxCPF).getTimeInMillis();
        long tempoEmMil2 = dataAtual.getTimeInMillis();
        long diferencaEmMil = tempoEmMil2-tempoEmMil1;
        long diferencaEmDias = TimeUnit.MILLISECONDS.toDays(diferencaEmMil);

        if(maximoDiasComLivro != -1){
            if(diferencaEmDias>maximoDiasComLivro){
                System.out.print("O usuario tem uma multa, devido ao atraso, no valor de R$");
                String formattedNumber = String.format("%.2f", valorMulta);
                System.out.println(formattedNumber);
            }
        }
    }

    public int getMaximoDiasComLivro() {
        return maximoDiasComLivro;
    }

    public float getValorMulta() {
        return valorMulta;
    }

    public void setMaximoDiasComLivro(int maximoDiasComLivro) {
        this.maximoDiasComLivro = maximoDiasComLivro;
        properties.setProperty("maximoDiasComLivro", Integer.toString(maximoDiasComLivro));
        saveProperties();
    }

    public void setValorMulta(float valorMulta) {
        this.valorMulta = valorMulta;
        properties.setProperty("valorMulta", Float.toString(valorMulta));
        saveProperties();
    }

    public int getMaximoNumCop() {
        return maximoNumCop;
    }

    public void setMaximoNumCop(int maximoNumCop) {
        this.maximoNumCop = maximoNumCop;
        properties.setProperty("maximoNumCop", Integer.toString(maximoNumCop));
        saveProperties();

    }
    public void setRestringirAraso(boolean boleano) {
        this.restringirAtraso = boleano;
        properties.setProperty("restringirAtraso", String.valueOf(boleano));
        saveProperties();

    }

    public Hashtable<String, Usuario> getCadastroUsuarios() {
        return cadastroUsuarios;
    }

    public void setCadastroUsuarios(Hashtable<String, Usuario> cadastroUsuarios) {
        this.cadastroUsuarios = cadastroUsuarios;
    }

    public Hashtable<String, Livro> getCadastroLivros() {
        return cadastroLivros;
    }

    public void setCadastroLivros(Hashtable<String, Livro> cadastroLivros) {
        this.cadastroLivros = cadastroLivros;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Arquivo properties nao encontrado");
                return;
            }

            properties.load(input);
            maximoDiasComLivro = Integer.parseInt(properties.getProperty("maximoDiasComLivro", "-1"));
            valorMulta = Float.parseFloat(properties.getProperty("valorMulta", "0.0"));
            maximoNumCop = Integer.parseInt(properties.getProperty("maximoNumCop", "10000"));
            restringirAtraso = Boolean.parseBoolean(properties.getProperty("restringirAtraso", "false"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveProperties() {
        try (FileOutputStream output = new FileOutputStream("P3nX/src/config.properties")) {
            properties.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
