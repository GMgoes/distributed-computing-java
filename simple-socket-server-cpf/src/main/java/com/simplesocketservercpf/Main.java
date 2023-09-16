package com.simplesocketservercpf;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.simplesocketservercpf.ValidateCPF.ValidateCPF;

public class Main {
    public static void main(String[] args) {
        try {
        int port = 12345;
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server running at port: "+ port);

        while(true) {
            Socket client = server.accept();
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String message = new String(buffer, 0, bytesRead, "UTF-8");
                String[] splitedMessage = message.split(",");
                
                String result;
                if(ValidateCPF.validar(splitedMessage[3])){
                    result = "O CPF informado está correto";
                }else {
                    result = "O CPF informado está incorreto";
                }

                byte[] response = result.getBytes("UTF-8");
                outputStream.write(response);
            }
            client.close();
            server.close();
            }
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}