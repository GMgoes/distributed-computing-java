package com.clientpeertopeer;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
public class Main {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 21; // everything reminds me u
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket serverConnection = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
             PrintWriter serverWriter = new PrintWriter(serverConnection.getOutputStream(), true)) {

            System.out.println("Connected to the FTP Server");

            while (true) {
                System.out.print("Enter 'get' to request a file, 'register' to register a new file, or 'quit' to exit: ");
                String input = scanner.nextLine();

                if ("get".equalsIgnoreCase(input)) {
                    System.out.print("Enter the name of the desired file: ");
                    String desiredFile = scanner.nextLine();

                    serverWriter.println("GET " + desiredFile);

                    String response = serverReader.readLine();
                    System.out.println(response);

                    if (response.startsWith("OWNER ")) {
                        String[] parts = response.split(" ");
                        String fileOwnerIP = parts[1];
                        int fileOwnerPort = Integer.parseInt(parts[2]);

                        Socket fileOwnerSocket = new Socket(fileOwnerIP, fileOwnerPort);
                        PrintWriter fileOwnerWriter = new PrintWriter(fileOwnerSocket.getOutputStream(), true);
                        fileOwnerWriter.println("REQUEST " + desiredFile);

                        InputStream inputStream = fileOwnerSocket.getInputStream();
                        FileOutputStream fileOutputStream = new FileOutputStream(desiredFile);
                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, bytesRead);
                        }

                        fileOutputStream.close();
                        inputStream.close();

                        fileOwnerWriter.close();
                        fileOwnerSocket.close();
                    } else {
                        System.out.println("File not found or error in response.");
                    }
                } else if ("register".equalsIgnoreCase(input)) {
                    // Código para registrar um arquivo no servidor (como você já tem)
                    // ...
                } else if ("quit".equalsIgnoreCase(input)) {
                    break;
                } else {
                    System.out.println("Invalid command");
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}