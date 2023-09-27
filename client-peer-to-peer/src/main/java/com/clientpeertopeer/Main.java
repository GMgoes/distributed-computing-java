package com.clientpeertopeer;

import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.net.ftp.FTPClient;

public class Main {
    public static void main(String[] args) {
        int port = 21; // everything reminds me u
        String server = "localhost";
        String user = "gusmgoes";
        String password = "1234";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            System.out.println("Hey, I successfully logged in");

            String remoteFilePath = "/teste.txt";

            InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath);
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
                inputStream.close();
            } else {
                System.err.println("Unable to locate this file, path: " + remoteFilePath);
            }

            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            System.err.println("Error in I/O operation: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}