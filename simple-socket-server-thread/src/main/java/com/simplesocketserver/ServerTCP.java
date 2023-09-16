package com.simplesocketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.simplesocketserver.ThreadHandler.ClientHandler;

public class ServerTCP {
    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            int port = 12345;
             server = new ServerSocket(port);
            System.out.println("Server running at port: " + port);

            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected: " + client.toString());

                Thread clientThread = new Thread(new ClientHandler(client));
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing server socket: " + e.getMessage());
            }
        }
    }
}
