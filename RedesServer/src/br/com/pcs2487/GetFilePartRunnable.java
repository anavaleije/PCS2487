package br.com.pcs2487;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class GetFilePartRunnable implements Runnable {
	
	private String fileName;
	private int offset;
	private int len;
	private byte[] buffer;
	private Socket socket;

	public GetFilePartRunnable(String fileName, int offset, int len, Socket socket) {
		this.fileName = fileName;
		this.offset = offset;
		this.len = len;
		this.buffer = new byte[len];
		this.socket = socket;
	}

	@Override
	public void run() {
		try (
			DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		){
	        in.read(buffer, 0, len);
	        file.seek(offset);
	        file.write(buffer);
	        
	        socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
