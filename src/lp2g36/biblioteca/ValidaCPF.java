package lp2g36.biblioteca;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaCPF {

    public static boolean isCPF(String CPF) {
        String CPFConvertido = "";

        String regex = "^[0-9]{3}[. ]?[0-9]{3}[. ]?[0-9]{3}[- /]?[0-9]{2}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(CPF);

        if(matcher.matches()){
            for(int i=0; i<CPF.length(); i++){
            if(CPF.charAt(i) != '.' && CPF.charAt(i) != ' ' && CPF.charAt(i) != '/' && CPF.charAt(i) != '-' && !Character.isDigit(CPF.charAt(i))){
                return false;
            }else if(Character.isDigit(CPF.charAt(i))){
                CPFConvertido += CPF.charAt(i);
                }
            }
        }else{
            return false;
        }

        // considera-se erro CPF"s formados por uma sequencia de numeros iguais
        if (CPFConvertido.equals("00000000000") ||
            CPFConvertido.equals("11111111111") ||
            CPFConvertido.equals("22222222222") || CPFConvertido.equals("33333333333") ||
            CPFConvertido.equals("44444444444") || CPFConvertido.equals("55555555555") ||
            CPFConvertido.equals("66666666666") || CPFConvertido.equals("77777777777") ||
            CPFConvertido.equals("88888888888") || CPFConvertido.equals("99999999999") ||
            (CPFConvertido.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere "0" no inteiro 0
        // (48 eh a posicao de "0" na tabela ASCII)
            num = (int)(CPFConvertido.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = "0".charAt(0);
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPFConvertido.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = "0".charAt(0);
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPFConvertido.charAt(9)) && (dig11 == CPFConvertido.charAt(10)))
                 return(true);
            else return(false);
                } catch (Exception erro) {
                return(false);
            }
        }

        public static String imprimeCPF(String CPF) {
            String CPFConvertido = "";

            for(int i=0; i<CPF.length(); i++){
                if(Character.isDigit(CPF.charAt(i)))
                    CPFConvertido += CPF.charAt(i);
            }
            return(CPFConvertido.substring(0, 3) + "." + CPFConvertido.substring(3, 6) + "." +
            CPFConvertido.substring(6, 9) + "-" + CPFConvertido.substring(9, 11));
        }

        public static long toLong(String CPF){
            String CPF_NUMERICO = "";
            long CPF_toLong;

            if(isCPF(CPF) == false){
                throw new IllegalArgumentException("CPF invalido");
            }else{

            for(int i=0; i<CPF.length(); i++)
                if(Character.isDigit(CPF.charAt(i)))
                    CPF_NUMERICO += CPF.charAt(i);
            
            CPF_toLong = Long.parseLong(CPF_NUMERICO);
            return CPF_toLong;
            }
        }
    }
    