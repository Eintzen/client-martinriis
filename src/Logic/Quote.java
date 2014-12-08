package Logic;

import Shared.TCPClient;

public class Quote {
	
	public String getQuote() throws Exception{
		
		TCPClient TcpClient = new TCPClient();
		
		return TcpClient.getQOTD();
		
	}
	
	public String getAuthor() throws Exception{
		
		TCPClient TcpClient = new TCPClient();
		
		return TcpClient.getQOTDAuthor();
		
	}

}
