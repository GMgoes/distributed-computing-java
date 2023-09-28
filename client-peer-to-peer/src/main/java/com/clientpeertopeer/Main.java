package com.clientpeertopeer;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
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
                    System.out.print("Enter the name of the desidered file: ");
                    String desideredFile = scanner.nextLine();

                    serverWriter.println("GET " + desideredFile);

                    String response = serverReader.readLine();
                    System.out.println(response);

                } else if ("register".equalsIgnoreCase(input)) {
                    System.out.print("Enter the file name to register: ");
                    String fileName = scanner.nextLine();
                    InetAddress address = InetAddress.getLocalHost();
                    String ipAddress = address.getHostAddress();
                    serverWriter.println("REGISTER " + fileName + " " + ipAddress);

                    String response = serverReader.readLine();
                    System.out.println(response);
                    
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