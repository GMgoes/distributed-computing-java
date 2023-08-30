package com.threads;

class Counter implements Runnable {
  private int currentValue;
  public Counter() { currentValue = 0; }
  public int getValue() { return currentValue; }

  public void run() {                            // (1) Thread entry point
    try {
      while (currentValue < 5) {
        System.out.println(
            Thread.currentThread().getName()     // (2) Print thread name.
            + ": " + (currentValue++)
        );
        Thread.sleep(250);                       // (3) Current thread sleeps.
      }
    } catch (InterruptedException e) {
      System.out.println(Thread.currentThread().getName() + " interrupted.");
    }
    System.out.println("Exit from thread: " + Thread.currentThread().getName());
  }
}