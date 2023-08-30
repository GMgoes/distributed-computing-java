package com.threads;

public class Main {
    public static void main(String[] args) {

    System.out.println("Method main() runs in thread " +
        Thread.currentThread().getName());       // (5) Current thread

    Counter counterA = new Counter("Counter A"); // (6) Create a thread.
    Counter counterB = new Counter("Counter B"); // (7) Create a thread.

    System.out.println("Exit from main() method.");
  }
}