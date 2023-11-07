package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread{
    Socket socket;
    BufferedReader input;
    DataOutputStream output;
    Distributore distributore;
    private static Semaforo semaforo = new Semaforo();

    public ServerThread(Socket socket, Distributore distributore){
        try {
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.distributore = distributore;
        this.socket = socket;
    }

    @Override
    public void run(){

        String request = "";

        do{
            try {
                request = input.readLine();

                System.out.println("STRINGA RICEVUTA DA " + socket.getInetAddress() + " : " + request);

                switch (request) {
                    case "q":
                        output.writeBytes("uscita avvenuta con successo\n");
                        output.writeBytes("q\n");
                        socket.close();
                        break;
                    case "a":
                        semaforo.P();
                        boolean riuscito = distributore.compra();
                        semaforo.V();
                        if(riuscito){
                            output.writeBytes("Acquisto effettuato con successo!\n");
                        }
                        else{
                            output.writeBytes("Acquisto non riuscito! \n");
                        }
                        break;
                    case "d":
                        output.writeBytes("Sono disponibili " + distributore.getNumeroBiglietti() + " biglietti! \n");
                        break;
                
                    default:
                        output.writeBytes("scelta inserita non valida! \n");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }while(!request.equals("q"));
    }
    
}
