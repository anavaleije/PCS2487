package br.com.pcs2487;

import java.io.DataOutputStream;
import java.io.IOException;
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
		this.buffer = new byte[2048]; // 2kB
		this.socket = socket;
	}

	@Override
	public void run() {
		try (
			RandomAccessFile file = new RandomAccessFile(fileName, "r");
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		) {
			out.writeInt(offset);
			file.seek(offset);
			int minLen = Math.min(len, buffer.length);
			int totalReadBytes = 0;
			while (totalReadBytes < len) {
				int readBytes = file.read(buffer, 0, minLen);
				out.write(buffer, 0, readBytes);
				out.flush();
				totalReadBytes += readBytes;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
