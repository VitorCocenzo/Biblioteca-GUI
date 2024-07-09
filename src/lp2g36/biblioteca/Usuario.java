package lp2g36.biblioteca;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Usuario extends Pessoa{
    private String endereco;
    private ArrayList<Emprest> hist;
    private int copiasPegas;

    public Usuario(){
    }

    public Usuario(String nome, String sobreNome, int dia, int mes, int ano, long numCPF, String endereco) {
        super(nome, sobreNome, dia, mes, ano, numCPF);
        this.endereco = endereco;
        this.hist = new ArrayList<Emprest>();
        this.copiasPegas=0;
    }

    public void addLivroHist(int codigo){
        hist.add(new Emprest(codigo));
    }

    @Override
    public String toString() {
        return super.toString() + "\n Historico -> " + hist + "\n Endereco -> " + endereco + "\n Copias pegas -> " + copiasPegas +"]\n";

    }
    
    public void setHist(int codigoLivro) {
        for (Emprest chave : hist) {
            if (chave.getCodigoLivro()==codigoLivro) {
                chave.setDataDev();
                break;
            }
        }
    }

    public String getEndereco() {
        return endereco;
    }

    public int getCopiasPegas() {
        return copiasPegas;
    }

    public void pegouEmprestado() {
        this.copiasPegas++;
    }
    public void devolveuOLivro() {
        this.copiasPegas--;
    }

    public ArrayList<Emprest> getHist() {
        return hist;
    }
    
}
