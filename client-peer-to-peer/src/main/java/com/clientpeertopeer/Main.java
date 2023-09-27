package com.clientpeertopeer;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Main {

    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int PORT = 21; // everything reminds me u

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the FTP server");
            System.out.print("Enter the desired file name: ");
            String desiredFile = userInput.readLine();

            outputWriter.println(desiredFile);

            String response = inputBuffer.readLine();
            if (!response.isEmpty()) {
                System.out.println("The file " + desiredFile + " belongs to " + response);
            } else {
                System.out.println("The file " + desiredFile + " was not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}