package lp2g36.biblioteca;

import java.util.GregorianCalendar;

public class ValidaData {
    private static GregorianCalendar dataAtual = new GregorianCalendar();

    public static boolean isDataValida(int dia, int mes , int ano){
        boolean bissexto = (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);

        if (mes < 1 || mes > 12) {
            return false;
        }

        switch (mes) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return (dia >= 1 && dia <= 31);
            case 4: case 6: case 9: case 11:
                return (dia >= 1 && dia <= 30);
            case 2:
                if (bissexto) {
                    return (dia >= 1 && dia <= 29);
                } else {
                    return (dia >= 1 && dia <= 28);
                }
            default:
                return false;
        }
    }

    public static boolean isDia(int dia) {
        return dia >= 1 && dia <= 31;
    }

    public static boolean isDia(String dia) {
        try {
            int d = Integer.parseInt(dia);
            return isDia(d);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isMes(int mes) {
        return mes >= 1 && mes <= 12;
    }

    public static boolean isMes(String mes) {
        try {
            int m = Integer.parseInt(mes);
            return isMes(m);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isAno(int ano) {
        if(ano<(dataAtual.get(GregorianCalendar.YEAR)-120) || ano>dataAtual.get(GregorianCalendar.YEAR)){
            return false;
        }
        return ano >= 0;
    }

    public static boolean isAno(String ano) {
        int a = Integer.parseInt(ano);

        if(a<(dataAtual.get(GregorianCalendar.YEAR)-120)){
            return false;
        }
        
        try {
            return isAno(a);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
