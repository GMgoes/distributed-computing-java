package com.simplesocketclientcpf;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import com.simplesocketclientcpf.FormDTO.FormDTO;

public class Main {
    public static void main(String[] args) {
        try {
            String host = "127.0.0.1"; int port = 12345;
            Socket client = new Socket(host, port);
        
            Scanner input = new Scanner(System.in);

            OutputStream outputStream = client.getOutputStream();
            InputStream inputStream = client.getInputStream();

            FormDTO form = new FormDTO();

            boolean flag = true;

            while(flag){
                System.out.println("Type your name");
                form.setName(input.next());

                System.out.println("Type your age");
                form.setAge(input.nextInt());

                System.out.println("Type your email");
                form.setEmail(input.next());

                System.out.println("Type your CPF");
                form.setCpf(input.next());

                byte[] data = form.toString().getBytes("UTF-8");
                outputStream.write(data);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    String message = new String(buffer, 0, bytesRead, "UTF-8");
                    System.out.println("Message received: " + message + " from " + client.toString());

                    if(!message.contains("incorreto")){
                        flag = false;
                    }
                    break;
                }
            }
            input.close();
            client.close();
        }
        catch(Exception e) {
        System.out.println("Error: " + e.getMessage());
        }
    }
}