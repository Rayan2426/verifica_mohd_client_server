package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket;

    public ClientThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input = "";
            while (!this.socket.isClosed()) {
                input = in.readLine();

                if(!input.equals("q"))
                    System.out.println(input);
            }
            System.out.println("RICEVUTA DI MESSAGGIO DI CHIUSURA AVVENUTA CON SUCCESSO");
            socket.close();
        } catch (Exception e) {
            
        }
    }
}
