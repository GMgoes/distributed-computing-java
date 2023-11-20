package com;

import java.net.Socket;
import java.util.Base64;
import java.io.PrintWriter;
import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class ClienteSocket {

    static String IV = "AAAAAAAAAAAAAAAA";
    static String key = "0123456789abcdef";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 21);

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                while (true) {
                    try {
                        String serverMessage = in.readLine();
                        byte[] encryptedText = Base64.getDecoder().decode(serverMessage);
                        String decryptedText = decrypt(encryptedText, key);
                        System.out.println(decryptedText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while (true) {
                String message = consoleInput.readLine();
                byte[] encryptedText = encrypt(message, key);
                String encryptedText64 = Base64.getEncoder().encodeToString(encryptedText);
                out.println(encryptedText64);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // This code originates from: https://www.devmedia.com.br/amp/utilizando-criptografia-simetrica-em-java/31170
    public static byte[] encrypt(String textoPuro, String chaveEncriptacao) throws Exception {
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec chave = new SecretKeySpec(chaveEncriptacao.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, chave, new IvParameterSpec(IV.getBytes("UTF-8")));
        return encripta.doFinal(textoPuro.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] textoEncriptado, String chaveEncriptacao) throws Exception {
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec chave = new SecretKeySpec(chaveEncriptacao.getBytes("UTF-8"), "AES");
        decripta.init(Cipher.DECRYPT_MODE, chave, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(decripta.doFinal(textoEncriptado), "UTF-8");
    }
}