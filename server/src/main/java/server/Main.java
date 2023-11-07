package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(3000);
            System.out.println("SERVER AVVIATO");
            Distributore dist = new Distributore();

            do{
                Socket socket = server.accept();
                System.out.println("NUOVO CLIENT CONNESSO");
                ServerThread thread = new ServerThread(socket);
                dist.aggiungiSocket(socket);
                thread.start();
                System.out.println("THREAD INIZIATO");
            }while(!server.isClosed());
            
            server.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}