package com.threads;

public class Main {
    public static void main(String[] args)
    throws InterruptedException {   // (11)

    StackImpl stack = new StackImpl(5);              // Stack of capacity 5.

    new StackPusher("A", stack);
    new StackPusher("B", stack);
    new StackPopper("C", stack);
    System.out.println("Main Thread sleeping.");
    Thread.sleep(10);
    System.out.println("Exit from Main Thread.");
    }
}