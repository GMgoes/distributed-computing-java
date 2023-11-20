package com;

import java.net.Socket;
import java.util.Base64;
import java.io.PrintWriter;
import javax.crypto.Cipher;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class ServidorSocket {

    static String IV = "AAAAAAAAAAAAAAAA";
    static String key = "0123456789abcdef";

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(21);
            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            while (true) {
                String clientMessage = in.readLine();
                byte[] encryptedTextFromClient = Base64.getDecoder().decode(clientMessage);
                String decryptedText = decrypt(encryptedTextFromClient, key);
                System.out.println(decryptedText);

                String responseToClient = new BufferedReader(new InputStreamReader(System.in)).readLine();
                byte[] encryptedTextToClient = encrypt(responseToClient, key);
                String encryptedText64 = Base64.getEncoder().encodeToString(encryptedTextToClient);
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