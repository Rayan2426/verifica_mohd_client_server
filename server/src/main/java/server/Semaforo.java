package server;

public class Semaforo {
    private int V = 1;

    public Semaforo(){}

    public synchronized void P(){

        while (V == 0) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println("Error from " + Thread.currentThread() + ":" +e.getMessage());
            }
        }

        V--;
    }

    public synchronized void V(){
        V++;
        notify();
    }
}
