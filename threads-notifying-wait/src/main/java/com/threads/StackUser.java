package com.threads;

abstract class StackUser implements Runnable {       // (5) Stack user

  protected StackImpl stack;                         // (6)

  StackUser(String threadName, StackImpl stack) {
    this.stack = stack;
    Thread worker = new Thread(this, threadName);
    System.out.println(worker);
    worker.setDaemon(true);                          // (7) Daemon thread status
    worker.start();                                  // (8) Start the thread
  }
}