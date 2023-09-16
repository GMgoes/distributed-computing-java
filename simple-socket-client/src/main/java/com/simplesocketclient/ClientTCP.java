package com.simplesocketclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
    public static void main(String[] args) {
    try {
      String host = "127.0.0.1"; int port = 12345;
      Socket client = new Socket(host, port);
  
      Scanner input = new Scanner(System.in);
      InputStream inputStream = client.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
      BufferedReader reader = new BufferedReader(inputStreamReader);

      boolean flag = true;
      
      while (flag) {
        System.out.println("Type '0' to leave");
        System.out.print("Type the message you want to send: ");
        OutputStream outputStream = client.getOutputStream();

        String message = input.next();
        if (message.equals("0")) {
            flag = false;
        } else {
            byte[] data = message.getBytes("UTF-8");
            outputStream.write(data);
            outputStream.flush();

            String serverMessage = reader.readLine();
            if (serverMessage != null) {
                System.out.println("Server says: " + serverMessage);
            }
        }
    }
      input.close();
      client.close();
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}