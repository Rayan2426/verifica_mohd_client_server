package server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Distributore{
    private ArrayList<Socket> sockets;
    private  int biglietti = 5;

    public Distributore(){
        sockets = new ArrayList<Socket>();
    }

    public void aggiungiSocket(Socket s){
        sockets.add(s);
    }

    public  boolean compra(){
        if(disponibile()){
            biglietti--;
            if(biglietti == 0){
                System.out.println("BIGLIETTI FINITI");
                broadcast();
            }
            return true;
        }
        return false;
    }

    public  boolean disponibile(){
        return biglietti > 0;
    }

    public  int getNumeroBiglietti(){
        return biglietti;
    }

    public  void broadcast(){
        for(Socket s : sockets){
            try {
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                out.writeBytes("I BIGLIETTI SONO ESAURITI \n");
                System.out.println("INVIATO MESSAGGIO BROADCAST DI BIGLIETTI ESAURITI A " + s.getInetAddress());
            } catch (Exception e) {
                System.out.println("ERRORE BROADCAST: " + e.getMessage());
            }
        }

        System.out.println("INVIO MESSAGGIO DI BROADCAST TERMINATO");
    }
    
}
