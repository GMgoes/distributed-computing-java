package com.simplesocketservercpf.ValidateCPF;

public class ValidateCPF {
     public static boolean validar(String cpf){
         if(formata_cpf(cpf)[9] == valida_digito(10,cpf) && formata_cpf(cpf)[10] == valida_digito(11,cpf)){
             return true;
         }else{
             return false;
         }
    }
    public static int valida_digito(int peso, String cpf){
        int [] vetor_formatado = formata_cpf(cpf);
        int tamanho = peso-1;
        int soma = 0, quociente = 0, resto = 0, digito = 0;

        for (int i = 0; i < tamanho ; i++) {
            soma = soma + (vetor_formatado[i]*peso);
            peso--;
        }
        quociente = soma / 11;
        resto = soma % 11;
        if(resto < 2){
            digito = 0;
        }else{
            digito = 11 - resto;
        }
        return digito;
    }
    public static int [] formata_cpf(String cpf){

         String formatacao = cpf.replace('-',' ');
         formatacao = formatacao.replace('.',' ');
         formatacao = formatacao.replaceAll("\\s","");

         int [] vetor_cpf = new int[formatacao.length()];

         // formatacao = 06032059170 <- {0,6,0,3,2,0,5,9,1,7,0} <- Caracteres
        //                              0 1 2 3 4 5 6 7 8 9 10
        // '0'
        // "0"
        // 0
        for (int i = 0; i < vetor_cpf.length; i++) {
            vetor_cpf[i] = Integer.parseInt(String.valueOf(formatacao.charAt(i)));
        }
         return vetor_cpf;
    }

}
