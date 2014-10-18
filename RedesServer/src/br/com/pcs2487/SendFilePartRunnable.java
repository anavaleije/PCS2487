package br.com.pcs2487;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class SendFilePartRunnable implements Runnable {

	private String fileName;
	private int offset;
	private int len;
	private byte[] buffer;
	private Socket socket;
	
	public SendFilePartRunnable(String fileName, int offset, int len, Socket socket) {
		this.fileName = fileName;
		this.offset = offset;
		this.len = len;
		this.buffer = new byte[len];
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			RandomAccessFile file = new RandomAccessFile(fileName, "r");
			file.seek(offset);
			file.read(buffer, 0, len);
			
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			out.write(buffer);
			out.flush();
			
			file.close();
			out.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
