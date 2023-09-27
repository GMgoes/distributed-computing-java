package com.peertopeer;

import java.net.Socket;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        final int PORT = 21; // everything reminds me u
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("FTP server waiting for connections on port " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Connection established with " + clientSocket.getInetAddress());

                    BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                    String requestedFile = inputBuffer.readLine();
                    System.out.println("Requested file: " + requestedFile);

                    String userWithFile = verifyFileExists(requestedFile);

                    if (!userWithFile.equals("")) {
                        outputWriter.println(userWithFile);
                    } else {
                        outputWriter.println("File not found");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String verifyFileExists(String requestedFileName) {
        String generalFolder = "src//main//general//users-info.txt";
    
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(generalFolder))) {
            String row;
            while ((row = bufferReader.readLine()) != null) {
                String[] fields = row.split(" ");
                if (fields.length == 3) {
                    String user = fields[0];
                    String nameFile = fields[1];
                    String ip = fields[2];
    
                    if (requestedFileName.equals(nameFile)) {
                        return user + " com IP " + ip;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}