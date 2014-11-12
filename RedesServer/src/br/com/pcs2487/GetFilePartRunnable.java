package br.com.pcs2487;

import java.io.DataInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class GetFilePartRunnable implements Runnable {
	
	private String fileName;
	private byte[] buffer;
	private String hostName;
	private int portNumber;

	public GetFilePartRunnable(String fileName, String hostName, int portNumber) {
		this.fileName = fileName;
		this.buffer = new byte[1024]; // 1kB
		this.hostName = hostName;
		this.portNumber = portNumber;
	}

	@Override
	public void run() {
		
		try (
			Socket socket = new Socket(hostName, portNumber);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		) {
			int offset = in.readInt();
			int readBytes = in.read(buffer, 0, buffer.length);
			while (readBytes != -1) {
				file.seek(offset);
	        	file.write(buffer, 0, readBytes);
	        	offset += readBytes;
	        	readBytes = in.read(buffer, 0, buffer.length);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
