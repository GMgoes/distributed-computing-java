package com.simplesocketserver.ThreadHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String message = new String(buffer, 0, bytesRead, "UTF-8");
                System.out.println("Message received: " + message + " from " + clientSocket.toString());
                String reversedMessage = new StringBuilder(message).reverse().toString();
                System.out.println("Reversed message: " + reversedMessage + " from " + Thread.currentThread().getName());
            
                byte[] response = (reversedMessage + "\n").getBytes("UTF-8"); // Adicione "\n" à resposta
                outputStream.write(response);
                outputStream.flush();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}