package printer;


import java.util.ArrayList;

import dto.Document;

public class PrinterSystem {
    private ArrayList<Document> queue = new ArrayList<>();
    private Object lock = new Object();

    public void addToQueue(Document document) {
        synchronized (lock) {
            // Se tem prioridade vai pro inicio da fila, senão vai pro final
            queue.add(document.priority ? 0 : queue.size(), document);
            System.out.println("Documento produzido: " + document);
            // Avisa as outras threads
            lock.notifyAll();
        }
    }

    public Document removeFromQueue() {
        synchronized (lock) {
            while (queue.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Document document = queue.remove(0);
            return document;
        }
    }
}