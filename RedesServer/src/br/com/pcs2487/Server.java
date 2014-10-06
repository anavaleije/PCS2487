package br.com.pcs2487;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

public static void main(String[] args) throws IOException {

        int portNumber = 25565;
        
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader( 
            		new InputStreamReader(clientSocket.getInputStream()));
        ) {
        	//GetFilePartRunnable getFilePartRunnable = new GetFilePartRunnable(1);
        	//new Thread(getFilePartRunnable).start();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
