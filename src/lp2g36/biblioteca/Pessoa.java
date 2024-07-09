package lp2g36.biblioteca;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Pessoa implements Serializable {
    private String nome,sobreNome;
    private GregorianCalendar dataNasc;
    private long numCPF;
    private static int numPessoas;


    protected Pessoa(){
    }

    public Pessoa(String nome, String sobreNome, int dia, int mes, int ano, long numCPF){
        if(!ValidaData.isAno(ano) || !ValidaData.isDia(dia) || !ValidaData.isMes(mes)){
            throw new IllegalArgumentException("Data invalida");
        }

        if(!ValidaCPF.isCPF(Long.toString(numCPF))){
            throw new IllegalArgumentException("CPF invalidado");
        }

        numPessoas++;
        this.setNome(nome);
        this.setSobreNome(sobreNome);
        this.setNumCPF(numCPF);
        this.dataNasc = new GregorianCalendar(ano, mes-1, dia);

    }

    public static int numPessoas(){
        return numPessoas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    /*public GregorianCalendar getDataNasc() {
        return dataNasc;
    }*/

    public void setDataNasc(int dia, int mes, int ano) {
        this.dataNasc = new GregorianCalendar(ano, mes-1, dia);
    }

    public long getNumCPF() {
        return numCPF;
    }

    public void setNumCPF(long numCPF) {
        this.numCPF = numCPF;
    }

    @Override
    public String toString() {
        return "CPF\n Nome -> " + nome +" " + sobreNome + "\n Data de Nascimento -> " + dataNasc.get(GregorianCalendar.DAY_OF_MONTH)+ "/" + 
        (dataNasc.get(GregorianCalendar.MONTH)+1)+"/"+ dataNasc.get(GregorianCalendar.YEAR);
    }

    public static int getNumPessoas() {
        return numPessoas;
    }
    
    public String getDataNasc(){
        return dataNasc.get(GregorianCalendar.DAY_OF_MONTH)+ "/" + 
        (dataNasc.get(GregorianCalendar.MONTH)+1)+"/"+ dataNasc.get(GregorianCalendar.YEAR);
    }

    public int getDiaNasc(){
        return dataNasc.get(GregorianCalendar.DAY_OF_MONTH);
    }
    public int getMesNasc(){
        return dataNasc.get(GregorianCalendar.MONTH);
    }
    public int getAnoNasc(){
        return dataNasc.get(GregorianCalendar.YEAR);
    }
}
