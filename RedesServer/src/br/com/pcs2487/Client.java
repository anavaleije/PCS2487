package br.com.pcs2487;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	 public static void main(String[] args) throws IOException {

	        String hostName = "localhost";
	        int portNumber = 25565;
	        int nThreads = 1; //Integer.parseInt(args[0]);
	        
	        byte[] buffer = new byte[1024];
	        byte[] buffer1 = new byte[1024];
	        
	        for (int i = 0; i < nThreads; i++) {
	        	
		        try (
		        	Socket echoSocket = new Socket(hostName, portNumber)
		        ) {
		        	DataOutputStream out = new DataOutputStream(new BufferedOutputStream(echoSocket.getOutputStream()));
		            DataInputStream in = new DataInputStream(new BufferedInputStream(echoSocket	.getInputStream()));
//		            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
//		            ByteArrayOutputStream teste = new ByteArrayOutputStream();
//		            BufferedReader in = new BufferedReader(
//		            					new InputStreamReader(echoSocket.getInputStream()));
//		            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//		            String userInput;
//		            System.out.print("Digite algo: ");
//		            while ((userInput = stdIn.readLine()) != null) {
//		                out.println(userInput);
//		                System.out.println("echo: " + in.readLine());
//		                System.out.print("Digite algo: ");
//		            }
		            FileOutputStream fos = new FileOutputStream("testeImagemChegou.bmp");
		            in.read(buffer, 0, 4);
		            fos.write(buffer, 0, 4);
		            in.read(buffer, 4, 1020);
		            fos.write(buffer, 4, 1020); //FIXME Aqui ele completa com vários \00, tem que arrumar 
		            fos.flush();
		            fos.close();
		            
		        } catch (UnknownHostException e) {
		            System.err.println("Don't know about host " + hostName);
		            System.exit(1);
		        } catch (IOException e) {
		            System.err.println("Couldn't get I/O for the connection to " +
		                hostName);
		            System.exit(1);
		        }
		        System.out.println("Fim cliente");
	        }
	    }
}
