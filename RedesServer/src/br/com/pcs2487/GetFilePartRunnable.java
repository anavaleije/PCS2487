package br.com.pcs2487;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class GetFilePartRunnable implements Runnable {
	
	private String fileName;
	private int offset;
	private byte[] buffer;
	private Socket socket;

	public GetFilePartRunnable(String fileName, int offset, Socket socket) {
		this.fileName = fileName;
		this.offset = offset;
		this.buffer = new byte[255];
		this.socket = socket;
	}

	@Override
	public void run() {
		try (
			DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		){
			int readBytes = in.read(buffer, 0, 255);
			
			while (readBytes != -1) {
				file.seek(offset);
	        	file.write(buffer, 0, readBytes);
	        	offset += readBytes;
	        	readBytes = in.read(buffer, 0, 255);
			}
	        
	        socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
