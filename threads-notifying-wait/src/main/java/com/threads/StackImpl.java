package com.threads;

class StackImpl {
  private Object[] stackArray;
  private volatile int topOfStack;

  StackImpl (int capacity) {
    stackArray = new Object[capacity];
    topOfStack = -1;
  }

  public synchronized Object pop() {
    System.out.println(Thread.currentThread() + ": popping");
    while (isEmpty())
      try {
        System.out.println(Thread.currentThread() + ": waiting to pop");
        wait();                                      // (1)
      } catch (InterruptedException ie) {
        System.out.println(Thread.currentThread() + " interrupted.");
      }
    Object element = stackArray[topOfStack];
    stackArray[topOfStack--] = null;
    System.out.println(Thread.currentThread() +
                       ": notifying after popping");
    notify();                                        // (2)
    return element;
  }

  public synchronized void push(Object element) {
    System.out.println(Thread.currentThread() + ": pushing");
    while (isFull())
      try {
        System.out.println(Thread.currentThread() + ": waiting to push");
        wait();                                      // (3)
      } catch (InterruptedException ie) {
        System.out.println(Thread.currentThread() + " interrupted.");
      }
    stackArray[++topOfStack] = element;
    System.out.println(Thread.currentThread() +
                       ": notifying after pushing");
    notify();                                        // (4)
  }

  public boolean isFull() { return topOfStack >= stackArray.length -1; }
  public boolean isEmpty() { return topOfStack < 0; }
}
