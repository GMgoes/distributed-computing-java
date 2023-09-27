package com.clientpeertopeer;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPClient;

public class Main {
    public static void main(String[] args) {
        String server = "localhost";
        int port = 21; // everything reminds me u
        String user = "gusmgoes";
        String password = "1234";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            
            ftpClient.login(user, password);

            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                System.out.println(file.getName());
            }

            ftpClient.logout();
            ftpClient.disconnect();
        } catch (Exception e) {
            System.err.println("Error connecting or listing files: " + e.getMessage());
        }
    }
}