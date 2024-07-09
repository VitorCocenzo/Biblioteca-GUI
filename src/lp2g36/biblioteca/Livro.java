package lp2g36.biblioteca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Livro implements Serializable{
    private int codigoLivro;
    private String titulo;
    private String categoria;
    private ArrayList<EmprestPara> hist = new ArrayList<>();
    private int qntdCopias;
    private int emprestados;

    public Livro(){
    }

    public Livro(int codigoLivro, String titulo, String categoria, int qntdCopias) {
        if(codigoLivro>999 || codigoLivro<1){
            throw new IllegalArgumentException("ERRO! O codigo do livro e invalido");
        }else{
            this.codigoLivro = codigoLivro;
        }

        this.titulo = titulo;
        categoria = categoria.toUpperCase();

        switch (categoria) {
            case "ROMANCE":
                this.categoria = "ROMANCE";
                break;
            case "FICCAO":
                this.categoria = "FICCAO";
                break;
            case "AVENTURA":
                this.categoria = "AVENTURA";
                break;
            case "NOVELA":
                this.categoria = "NOVELA";
                break;
            case "DRAMA":
                this.categoria = "DRAMA";
                break;
            case "CONTO":
                this.categoria = "CONTO";
                break;
            case "CRONICA":
                this.categoria = "CRONICA";
                break;
            case "POESIA":
                this.categoria = "POESIA";
                break;
            case "CARTA":
                this.categoria = "CARTA";
                break;
            case "BIOGRAFIA":
                this.categoria = "BIOGRAFIA";
                break;
            case "HQ":
                this.categoria = "HQ";
                break;
            case "LITERATURA":
                this.categoria = "LITERATURA";
                break;
            case "TERROR":
                this.categoria = "TERROR";
                break;
            case "REALISMO":
                this.categoria = "REALISMO";
                break;
            case "ARTIGO":
                this.categoria = "ARTIGO";
                break;
            default:
                throw new IllegalArgumentException("ERRO! Categoria invalida.");
        }

        this.categoria = categoria;
        this.qntdCopias = qntdCopias;
        this.emprestados=0;
    }

    public void empresta() throws CopiaNaoDisponivelEX{
        if(this.emprestados>=this.qntdCopias){
            throw new CopiaNaoDisponivelEX("ERRO! Todas as copias ja foram emprestadas.");
        }else{
            this.emprestados++;
        }
    }  
    
    public void devolve() throws NenhumaCopiaEmprestadaEX{
        if(this.emprestados<=0){
            throw new NenhumaCopiaEmprestadaEX("ERRO! Nao ha nenhuma copia emprestada.");
        }else{
            this.emprestados--;
        }
    }
    
    public void addUsuarioHist(String CPF){
        hist.add(new EmprestPara(CPF));
    }

    public int getCodigoLivro() {
        return codigoLivro;
    }

    public ArrayList<EmprestPara> getHist() {
        return hist;
    }

    @Override
    public String toString() {

        return "Codigo do livro\n Titulo -> " + titulo + ",\n Categoria -> " + categoria + ", \n\n Historico -> "
                     + hist + " \n Quantidade de copias -> " + qntdCopias + ", \n Emprestados -> " + emprestados + "]\n";
    }

    public void setHist(String CPF) {
        for (EmprestPara chave : hist) {
            if (chave.getCodUsuario().equals(CPF)) {
                chave.setDataDev();
                break;
            }
        }
    }

    public GregorianCalendar getHist(String CPF) {
        for (EmprestPara chave : hist) {
            if (chave.getCodUsuario().equals(CPF)) {
                return chave.getDataEmprest();
                }
            }
        return new GregorianCalendar();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getQntdCopias() {
        return qntdCopias;
    }

    public int getEmprestados() {
        return emprestados;
    }

}