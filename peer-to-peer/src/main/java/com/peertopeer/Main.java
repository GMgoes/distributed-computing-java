package com.peertopeer;
import java.io.File;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

public class Main {
    public static void main(String[] args) {

        //https://mina.apache.org/ftpserver-project/embedding_ftpserver.html
        int port = 21; // everything reminds me u

        FtpServerFactory serverFactory = new FtpServerFactory(); // Instantiate the FTP server factory
        ListenerFactory factory = new ListenerFactory(); // Instantiate the listener factory
             
        factory.setPort(port); // Set the port 21 to the factory listener

        serverFactory.addListener("default", factory.createListener()); // Replaces the default listener with the one we defined

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory(); // Instantiate the Properties of User factory

        userManagerFactory.setFile(new File("src//main//users.properties")); // Defines the file for us to configure
        serverFactory.setUserManager(userManagerFactory.createUserManager()); // Set the userManagerFactory into the server FTP

        FtpServer server = serverFactory.createServer(); 

        try {     
            server.start();  // really start the server
            System.out.println("FTP server started on port " + port);
        } catch (Exception e) {
            System.err.println("Error starting FTP server: " + e.getMessage());
        }
    }
}