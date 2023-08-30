package com.threads;

public class Main {
    public static void main(String[] args) {
        Counter counterA = new Counter();                 // (4) Create a counter.
        Thread worker = new Thread(counterA, "Counter A");// (5) Create a new thread.
        System.out.println(worker);
        worker.start();                                   // (6) Start the thread.
    
        try {
          int val;
          do {
            val = counterA.getValue();               // (7) Access the counter value.
            System.out.println(
                "Counter value read by " +
                Thread.currentThread().getName() +   // (8) Print thread name.
                ": " + val
            );
            Thread.sleep(1000);                      // (9) Current thread sleeps.
          } while (val < 5);
        } catch (InterruptedException e) {
          System.out.println("The main thread is interrupted.");
        }
    
        System.out.println("Exit from main() method.");
      }
}