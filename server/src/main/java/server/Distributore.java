package server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Distributore extends Thread{
    private ArrayList<Socket> sockets;
    private static Semaforo semaforo = new Semaforo();
    private static int biglietti = 5;

    public Distributore(){
        sockets = new ArrayList<Socket>();
    }

    public void aggiungiSocket(Socket s){
        semaforo.P();
        sockets.add(s);
        semaforo.V();
    }

    public static boolean compra(){
        semaforo.P();
        if(disponibile()){
            biglietti--;
            return true;
        }
        semaforo.V();
        return false;
    }

    public static boolean disponibile(){
        return biglietti > 0;
    }

    public static int getNumeroBiglietti(){
        return biglietti;
    }

    @Override
    public void run(){
        while (biglietti > 0) {
            if(biglietti == 0){
                for(Socket s : sockets){
                    try {
                        DataOutputStream out = new DataOutputStream(s.getOutputStream());
                        out.writeBytes("I BIGLIETTI SONO ESAURITI \n");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("INVIO MESSAGGIO DI BROADCAST TERMINATO");
            }
        }
    }
    
}
