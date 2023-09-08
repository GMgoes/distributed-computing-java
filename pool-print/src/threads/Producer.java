package threads;

import java.util.Random;

import dto.Document;
import printer.PrinterSystem;

public class Producer implements Runnable {
    private PrinterSystem printer;

    public Producer(PrinterSystem printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            /* int number_random = random.nextInt(100); */
            // Lógica para chance de produzir o documento e adicioná-lo à fila 70%
            if(random.nextInt(100) < 70){
                /* System.out.println("O número gerado aleatoriamente pela Thread "+ Thread.currentThread().getName()+ " foi o número: "+ number_random); */
                Document document = new Document("documento" + System.currentTimeMillis() + ".docx", random.nextBoolean());
                printer.addToQueue(document);
            }else {
                // Lógica para chance de não produzir o documento 30%
                System.out.println("A thread " + Thread.currentThread().getName()+ " bateu o dedo na quina e perdeu o documento que estava produzindo");
            }

            try {
                // Após produzir e adicionar à fila, ou perder o documento, dormirá por um tempo aleatório entre 2 e 3 segundos
                Thread.sleep(random.nextInt(2000,3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
