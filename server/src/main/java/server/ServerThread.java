package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread{
    Socket socket;
    BufferedReader input;
    DataOutputStream output;

    public ServerThread(Socket socket){
        try {
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.socket = socket;
    }

    @Override
    public void run(){

        String request = "";

        do{
            try {
                request = input.readLine();
                request = request.toLowerCase();

                switch (request) {
                    case "q":
                        output.writeBytes("uscita avvenuta con successo\n");
                        output.writeBytes("q");
                        socket.close();
                        break;
                    case "a":
                        if(Distributore.compra()){
                            output.writeBytes("Acquisto effettuato con successo!\n");
                        }
                        break;
                    case "d":
                        output.writeBytes("Sono disponibili " + Distributore.getNumeroBiglietti() + " biglietti! \n");
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
