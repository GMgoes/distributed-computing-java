package com.simplesocketserver;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args) {
    try {
        int port = 12345;
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server running at port: "+ port);
        while(true) {
            Socket client = server.accept();
            InputStream inputStream = client.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String message = new String(buffer, 0, bytesRead, "UTF-8");
                System.out.println("Message received: " + message + " from " + client.toString());
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
