package threads;

import java.util.List;
import java.util.Random;

public class Producer implements Runnable {
    private List<String> list;

    public Producer(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        boolean insertion_authorized = tryAdd();

        if(insertion_authorized){
            Random generator = new Random();
            list.add("file_"+ generator.nextInt(1000)+ ".docx");
        }else {
            try{
                Thread.sleep(100);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized boolean tryAdd(){
        int R = 70;
        Random generator = new Random();
        /* Defined that the probability of this thread being able to insert in the queue is 70%
        that is, if the drawn number is >= 0 and <= 70, it will be able to add to the queue
        */
        int random = generator.nextInt(100);
        /* System.out.println(random); */
        return random <= R;
    }
}
