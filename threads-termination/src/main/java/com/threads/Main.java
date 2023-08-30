package com.threads;

public class Main {
    public static void main(String[] args) {                      // (7)
        Worker worker = new Worker();                               // (8)
        worker.kickStart();                                         // (9)
        try {
          Thread.sleep(2);                                          // (10)
        } catch(InterruptedException ie) {
          ie.printStackTrace();
        }
        worker.terminate();                                         // (11)
      }
}