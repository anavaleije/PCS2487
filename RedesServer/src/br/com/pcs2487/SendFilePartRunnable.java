package br.com.pcs2487;

public class SendFilePartRunnable implements Runnable {

	private String filePart;
	
	public SendFilePartRunnable(String filePart) {
		this.filePart = filePart;
	}
	
	public String getFilePart() {
		return filePart;
	}

	public void setFilePart(String filePart) {
		this.filePart = filePart;
	}
	
	@Override
	public void run() {
		
	}

}
