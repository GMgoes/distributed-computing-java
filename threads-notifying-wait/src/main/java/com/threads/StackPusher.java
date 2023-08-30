package com.threads;

class StackPusher extends StackUser {                // (10) Pusher
  StackPusher(String threadName, StackImpl stack) {
    super(threadName, stack);
  }
  public void run() { while (true) stack.push(2008); }
}
