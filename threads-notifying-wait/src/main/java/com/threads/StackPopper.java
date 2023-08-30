package com.threads;

class StackPopper extends StackUser {                // (9) Popper
  StackPopper(String threadName, StackImpl stack) {
    super(threadName, stack);
  }
  public void run() { while (true) stack.pop(); }
}
