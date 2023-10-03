package com.peertopeer;

import java.util.Map;
import java.util.UUID;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;

public class Main {

    private static final int SERVER_PORT = 21;  // everything reminds me u
    private static final String USERS_FILES_FILE = "src//main//general//users-info.txt";
    private static Map<String, String[]> userFilesMap = new HashMap<>();

    public static void main(String[] args) {
        initializeFTPServer();

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("FTP Server is running on port " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new FTPHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeFTPServer() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(USERS_FILES_FILE))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0].trim();
                String[] files = new String[parts.length - 1];
                for (int i = 1; i < parts.length; i++) {
                    files[i - 1] = parts[i].trim();
                }
                userFilesMap.put(username, files);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveUserFiles() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(USERS_FILES_FILE))) {
            for (Map.Entry<String, String[]> entry : userFilesMap.entrySet()) {
                String username = entry.getKey();
                String[] files = entry.getValue();
                StringBuilder line = new StringBuilder(username);
                for (String file : files) {
                    line.append(",").append(file);
                }
                fileWriter.write(line.toString());
                fileWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class FTPHandler extends Thread {
        private Socket clientSocket;

        public FTPHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String request = reader.readLine();

                if (request.startsWith("GET")) {
                    String desiredFile = request.substring(4).trim();
                    String owner = null;
                    String[] ownerPortAndIP = new String[2];
                    
                    for (Map.Entry<String, String[]> entry : userFilesMap.entrySet()) {
                        String key = entry.getKey();
                        String[] values = entry.getValue();

                        for (String value : values) {
                            if(value.equals(desiredFile)){
                                owner = key;
                                ownerPortAndIP = values;
                            }
                        }
                    }

                    if (owner != null) {
                        StringBuilder response = new StringBuilder("OWNER " + owner + " " + ownerPortAndIP[0] + " " + ownerPortAndIP[1]);
                        writer.println(response.toString());
                    } else {
                        writer.println("File not found or user not found");
                    }
                } else if (request.startsWith("REGISTER")) {
                    String informationUser = request.substring(9).trim();

                    UUID userId = UUID.randomUUID();
                    String username = "user_" + userId.toString();
                    String[] files = userFilesMap.get(username);
                    if (files != null) {
                        String[] newFiles = new String[files.length + 1];
                        System.arraycopy(files, 0, newFiles, 0, files.length);
                        newFiles[files.length] = informationUser;
                        userFilesMap.put(username, newFiles);
                    } else {
                        userFilesMap.put(username, new String[]{informationUser});
                    }

                    saveUserFiles();

                    writer.println("File '" + informationUser + "' registered successfully.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}