package lp2g36.biblioteca;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Emprest implements Serializable{
    private GregorianCalendar dataEmprest, dataDev;
    private int codigoLivro;


    public Emprest(int codigoLivro) {
        this.dataEmprest = new GregorianCalendar();
        this.codigoLivro = codigoLivro;
        this.dataDev = null;
    }

    public void setDataDev(){
        this.dataDev = new GregorianCalendar();
    }

    public GregorianCalendar getDataDev() {
        return dataDev;
    }

    public int getCodigoLivro() {
        return codigoLivro;
    }

    @Override
    public String toString() {
        if(dataDev==null){
            return "[Emprestado no dia -> " + dataEmprest.get(GregorianCalendar.DAY_OF_MONTH) + "/" +dataEmprest.get(GregorianCalendar.MONTH)+ "/" + dataEmprest.get(GregorianCalendar.YEAR) +
            "\n               Devolvido em -> Pendente" + "\n               Codigo do livro -> " + codigoLivro + "]\n";
        }
            return "[Emprestado no dia -> " + dataEmprest.get(GregorianCalendar.DAY_OF_MONTH) + "/" +dataEmprest.get(GregorianCalendar.MONTH)+ "/" + dataEmprest.get(GregorianCalendar.YEAR) +
            "\n               Devolvido em -> " + dataDev.get(GregorianCalendar.DAY_OF_MONTH) + "/" +dataDev.get(GregorianCalendar.MONTH)+ "/" + dataDev.get(GregorianCalendar.YEAR) + 
            "\n               Codigo do livro -> " + codigoLivro + "]\n";
    }
    public GregorianCalendar getDataEmprest() {
        return dataEmprest;
    }
    
}
