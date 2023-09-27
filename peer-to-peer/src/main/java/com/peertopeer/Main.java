package com.peertopeer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

public class Main {
    public static void main(String[] args) {
        //https://mina.apache.org/ftpserver-project/embedding_ftpserver.html
        //https://stackoverflow.com/questions/8969097/writing-a-java-ftp-server
        int port = 21; // everything reminds me u

        FtpServerFactory serverFactory = new FtpServerFactory(); // instantiate the FTP server factory
        ListenerFactory factory = new ListenerFactory(); // instantiate the listener factory
             
        factory.setPort(port); // set the port 21 to the server listen

        serverFactory.addListener("default", factory.createListener()); // replaces the default listener with the one we defined

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory(); // instantiate the Properties of User factory

        userManagerFactory.setFile(new File("src//main//users.properties")); // defines the file for us to configure
        UserManager userManager = userManagerFactory.createUserManager();

        BaseUser user = new BaseUser(); // create a user with username = gusmgoes and password = 1234, this credentials is used in login the user in client ftp
        user.setName("gusmgoes");
        user.setPassword("1234");
        user.setHomeDirectory("src//main//general"); // gives him access to the general folder and the files inside him

        List<Authority> roles = new ArrayList<Authority>();
        roles.add(new WritePermission()); // have permission to write in the files
        user.setAuthorities(roles);

        try {   
            userManager.save(user);
            System.out.println("Success in create a user");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        serverFactory.setUserManager(userManager);

        FtpServer server = serverFactory.createServer();

        try {   
            server.start();  // really start the server
            System.out.println("FTP server started on port " + port);
        } catch (Exception e) {
            System.err.println("Error starting FTP server: " + e.getMessage());
        }
    }
}