package threads;

import java.util.List;

public class Consumer implements Runnable {
    private List<String> list;

    public Consumer(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            String data;
            synchronized (list) {
                if (!list.isEmpty()) {
                    data = list.remove(0); // Remova o primeiro elemento da lista
                } else {
                    data = null;
                }
            }

            if (data != null) {
                System.out.println(Thread.currentThread().getName() + " printed: " + data);
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
