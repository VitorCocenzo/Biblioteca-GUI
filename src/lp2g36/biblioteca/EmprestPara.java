package lp2g36.biblioteca;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class EmprestPara implements Serializable {
    private GregorianCalendar dataEmprest, dataDev;
    private String codUsuario;


    public EmprestPara(String codUsuario) {
        this.dataEmprest = new GregorianCalendar();
        this.codUsuario = codUsuario;
        this.dataDev = null;
    }

    public void setDataDev(){
        this.dataDev = new GregorianCalendar();
    }

    public GregorianCalendar getDataDev() {
        return dataDev;
    }

    public String getCodUsuario(){
        return codUsuario;
    }

    @Override
    public String toString() {
        if(dataDev==null){
            return "[Emprestado no dia -> " + dataEmprest.get(GregorianCalendar.DAY_OF_MONTH) + "/" +dataEmprest.get(GregorianCalendar.MONTH)+ "/" + dataEmprest.get(GregorianCalendar.YEAR) +
            "\n               Devolvido em -> Pendente" + "\n               Codigo do usuario -> " + codUsuario + "]\n";
        }
            return "[Emprestado no dia -> " + dataEmprest.get(GregorianCalendar.DAY_OF_MONTH) + "/" +dataEmprest.get(GregorianCalendar.MONTH)+ "/" + dataEmprest.get(GregorianCalendar.YEAR) +
            "\n               Devolvido em -> " + dataDev.get(GregorianCalendar.DAY_OF_MONTH) + "/" +dataDev.get(GregorianCalendar.MONTH)+ "/" + dataDev.get(GregorianCalendar.YEAR) + 
            "\n               Codigo do usuario -> " + codUsuario + "]\n";
    }

    public GregorianCalendar getDataEmprest() {
        return dataEmprest;
    }
}
