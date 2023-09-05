import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        List<String> list_message = new ArrayList<>();
        Object lock = new Object();

        // threads that produce the data
        Thread thread_one_p = new Thread(new Producer(list_message, lock));
        Thread thread_two_p = new Thread(new Producer(list_message, lock));
        Thread thread_three_p = new Thread(new Producer(list_message, lock));
        Thread thread_four_p = new Thread(new Producer(list_message, lock));

        // threads that consume the data
        Thread thread_one_c = new Thread(new Consumer(list_message, lock));
        Thread thread_two_c = new Thread(new Consumer(list_message, lock));

        thread_one_c.start();
        thread_two_c.start();
        thread_one_p.start();
        thread_two_p.start();
        thread_three_p.start();
        thread_four_p.start();

        try {
            Thread.sleep(5000);

            thread_one_p.interrupt();
            thread_two_p.interrupt();
            thread_three_p.interrupt();
            thread_four_p.interrupt();

            thread_one_p.join();
            thread_two_p.join();
            thread_three_p.join();
            thread_four_p.join();
            thread_one_c.join();
            thread_two_c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("List of messages: ");
        for (String element : list_message) {
            System.out.println(element);
        }
    }
}

class Consumer implements Runnable {
    private List<String> list;
    private Object lock;

    public Consumer(List<String> list, Object lock) {
        this.list = list;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            String data;
            synchronized (lock) {
                while (list.isEmpty()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                data = list.remove(0);
            }

            System.out.println(Thread.currentThread().getName() + " printed: " + data);
        }
    }
}

class Producer implements Runnable {
    private List<String> list;
    private Object lock;

    public Producer(List<String> list, Object lock) {
        this.list = list;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            boolean insertionAuthorized = tryAdd();

            if (insertionAuthorized) {
                Random generator = new Random();
                String message = "file_" + generator.nextInt(1000) + ".docx";

                synchronized (lock) {
                    list.add(message);
                    lock.notifyAll();
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    private boolean tryAdd() {
        int R = 70;
        Random generator = new Random();
        int random = generator.nextInt(100);
        return random <= R;
    }
}