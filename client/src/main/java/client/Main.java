package client;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String str = "";
        Socket mySocket;
        try {
            mySocket = new Socket("localhost",3000);

            DataOutputStream out = new DataOutputStream(mySocket.getOutputStream());


            ClientThread thread = new ClientThread(mySocket);

            thread.start();
            do{
                str = input.nextLine();
                out.writeBytes(str+"\n");
                
            }while(!str.equals("q"));

            mySocket.close();
            
        } catch (Exception e) {
        }

        input.close();
    }
}