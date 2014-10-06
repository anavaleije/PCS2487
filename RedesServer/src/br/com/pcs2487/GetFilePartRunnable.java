package br.com.pcs2487;

public class GetFilePartRunnable implements Runnable {

	private String filePart;
	
	public String getFilePart() {
		return filePart;
	}
	
	public void setFilePart(String filePart) {
		this.filePart = filePart;
	}

	@Override
	public void run() {
		System.out.println("Getting part " + filePart);
	}

}
