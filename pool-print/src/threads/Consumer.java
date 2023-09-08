package threads;

import java.util.Random;

import dto.Document;
import printer.PrinterSystem;

public class Consumer implements Runnable {
    private PrinterSystem printer;

    public Consumer(PrinterSystem printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            // Lógica para chance de imprimir o documento 70%
            if(random.nextInt(100) < 70){
                Document document = printer.removeFromQueue();
                System.out.println("Documento impresso: " + document.toString());
            } else {
                // Lógica para chance de perder o documento que está sendo impresso e não removê-lo da fila 30%
                System.out.println("A thread " + Thread.currentThread().getName()+ " caiu e perdeu o documento que estava sendo impresso");
            }
            try {
                // Após consumir e tirar da fila, ou perder o documento que estava lendo, dormirá por um tempo aleatório entre 2 e 3 segundos
                Thread.sleep(random.nextInt(2000,3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}