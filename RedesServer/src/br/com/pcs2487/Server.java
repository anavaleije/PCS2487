package br.com.pcs2487;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

public static void main(String[] args) throws IOException {

        int portNumber = 25565;
        
        File arquivoOriginal = new File("teste.txt");
//        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        InputStream ios = new FileInputStream(arquivoOriginal);
        byte[] buffer = new byte[1024];
        
        ios.read(buffer);
        
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
        ) {
        	Socket clientSocket = serverSocket.accept();
        	
        	DataOutputStream out = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            DataInputStream in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        	
//            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);                   
//            BufferedReader in = new BufferedReader( 
//            		new InputStreamReader(clientSocket.getInputStream()));
        	//GetFilePartRunnable getFilePartRunnable = new GetFilePartRunnable(1);
        	//new Thread(getFilePartRunnable).start();
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                out.println(inputLine);
//            }
            out.write(buffer);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
