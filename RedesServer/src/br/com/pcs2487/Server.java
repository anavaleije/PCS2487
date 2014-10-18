package br.com.pcs2487;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

@SuppressWarnings("resource")
public static void main(String[] args) {

        final int portNumber = 25565;
        int nThreads;
        String fileName;
        byte[] fileNameBuffer;
        ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
        
        while (true) {
			try ( // try-with-resources: fecha os sockets e os streams sozinho
				Socket clientSocket = serverSocket.accept();
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			) {
				
				// Pegando pedido do cliente: qual arquivo e quantas threads
				nThreads = in.readInt();
				fileNameBuffer = new byte[256];
				in.read(fileNameBuffer);
				fileName = "serverFiles" + File.separator + new String(fileNameBuffer).trim();
				
				if (fileName.contains("..")) {
					System.out.println("Tentativa de acesso a pastas mais altas na árvore de arquivos.");
					continue;
				}
				
				File file = new File(fileName);
				int len = (int) (file.length()/nThreads);
				
				// Enviando o tamanho do arquivo para o cliente
				out.writeLong(file.length());
				
				for (int i = 0; i < nThreads; i++) {
					int offset = i*len;
					
					if (i == nThreads - 1) {
						len += (int) (file.length()%nThreads);
					}
					
					Socket socket = serverSocket.accept();
					new Thread(new SendFilePartRunnable(fileName, offset, len, socket)).start();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
}
