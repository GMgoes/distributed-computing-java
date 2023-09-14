package com.simplesocketclient;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
    public static void main(String[] args) {
    try {
      String host = "127.0.0.1"; int port = 12345;
      Socket client = new Socket(host, port);
  
      Scanner input = new Scanner(System.in);
      boolean flag = true;
      while(flag == true){
        System.out.println("Type it 0 to leave");
        System.out.print("Type the message you want to send:");
        OutputStream outputStream = client.getOutputStream();
        String message = input.next();
        if(message.length() != 1 && message != "0"){
          byte[] data = message.getBytes("UTF-8");
          outputStream.write(data);          
        }else{
          flag = false;
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