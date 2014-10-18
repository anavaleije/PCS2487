package br.com.pcs2487;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;

public class Client extends Thread {

	 public static void main(String[] args) throws IOException {

	        String hostName = args[0];
	        int portNumber = Integer.parseInt(args[1]);
	        int nThreads = Integer.parseInt(args[2]);
	        String fileName = args[3];
	        long fileLength;
	        
	        try ( // try-with-resources: fecha os sockets e os streams sozinho
        		Socket socket = new Socket(hostName, portNumber);
        		DataInputStream in = new DataInputStream(socket.getInputStream());
	        	DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	        	RandomAccessFile file = new RandomAccessFile(fileName, "rw");
	        ) {
	        	// Envio pedido de baixar arquivo fileName, usando nThreads
	        	out.writeInt(nThreads);
	        	out.write(fileName.getBytes());
	        	
	        	// Recebo informação do tamanho total do arquivo
	        	fileLength = in.readLong();
	        	file.setLength(fileLength);
	        	
	        	int len = (int) (fileLength/nThreads);
	        	
	        	for (int i = 0; i < nThreads; i++) {
	        		
	        		int offset = i*len;
					
					if (i == nThreads - 1) {
						len += (int) (fileLength%nThreads);
					}
					
	        		Socket socketPart = new Socket(hostName, portNumber);
	        		new GetFilePartRunnable(fileName, offset, len, socketPart).run();
				}
	        	
	        } catch (Exception e) {
				e.printStackTrace();
			}
	    }
}
