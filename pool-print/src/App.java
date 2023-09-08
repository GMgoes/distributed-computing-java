import printer.PrinterSystem;
import threads.Consumer;
import threads.Producer;

public class App {
    public static void main(String[] args) {
        PrinterSystem printer = new PrinterSystem();

        Thread[] producers = new Thread[4];
        Thread[] consumers = new Thread[2];

        for (int i = 0; i < 4; i++) {
            producers[i] = new Thread(new Producer(printer));
            producers[i].start();
        }

        for (int i = 0; i < 2; i++) {
            consumers[i] = new Thread(new Consumer(printer));
            consumers[i].start();
        }
/* 
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } */

        for (Thread producer : producers) {
            producer.interrupt();
        }
        for (Thread consumer : consumers) {
            consumer.interrupt();
        }
    }
}