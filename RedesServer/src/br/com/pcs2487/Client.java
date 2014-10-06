package br.com.pcs2487;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	 public static void main(String[] args) throws IOException {

	        String hostName = "localhost";
	        int portNumber = 25565;
	        int nThreads = 1; //Integer.parseInt(args[0]);
	        
	        for (int i = 0; i < nThreads; i++) {
	        	
		        try (
		        	Socket echoSocket = new Socket(hostName, portNumber)
		        ) {
		            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
		            BufferedReader in = new BufferedReader(
		            					new InputStreamReader(echoSocket.getInputStream()));
		            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		            String userInput;
		            System.out.print("Digite algo: ");
		            while ((userInput = stdIn.readLine()) != null) {
		                out.println(userInput);
		                System.out.println("echo: " + in.readLine());
		                System.out.print("Digite algo: ");
		            }
		        } catch (UnknownHostException e) {
		            System.err.println("Don't know about host " + hostName);
		            System.exit(1);
		        } catch (IOException e) {
		            System.err.println("Couldn't get I/O for the connection to " +
		                hostName);
		            System.exit(1);
		        }
	        }
	    }
}
