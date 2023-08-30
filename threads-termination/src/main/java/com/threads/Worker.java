package com.threads;

class Worker implements Runnable {                              // (1)
  private volatile Thread theThread;                            // (2)

  public void kickStart() {                                     // (3)
    if (theThread == null) {
      theThread = new Thread(this);
      theThread.start();
    }
  }

  public void terminate() {                                     // (4)
    theThread = null;
  }

  public void run() {                                           // (5)
    while (theThread == Thread.currentThread()) {               // (6)
      System.out.println("Going around in loops.");
    }
  }
}