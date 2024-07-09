package lp2g36.biblioteca;

public class UsuarioNaoCadastradoEX extends Exception{
    public UsuarioNaoCadastradoEX(){
        super();
    }

    public UsuarioNaoCadastradoEX(String mensagem){
        super(mensagem);
    }
}
