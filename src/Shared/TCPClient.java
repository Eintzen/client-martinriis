package Shared;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Logic.ApplicationState;
import Logic.ValidateUser;

public class TCPClient {
	
	private static String OutToServer(String gsonString) throws Exception{
		// String to be returned
		String StrInFromServer;
		
		// Establish TCP connection
		Socket clientSocket = new Socket("localhost", 8888);
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());
		byte[] input = gsonString.getBytes();
		
		// Encrypt input
		byte key = (byte) 3.1470;
		byte[] encrypted = input;
		for (int i = 0; i < encrypted.length; i++)
			encrypted[i] = (byte) (encrypted[i] ^ key);
		
		// Send encrypted string to server
		outToServer.write(encrypted);
		outToServer.flush();
		
		// Read answer from server
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		StrInFromServer = inFromServer.readLine();
		
		System.out.println("Answer from server: " + StrInFromServer);
		// Close TCP connection and return server answer
		clientSocket.close();
		return StrInFromServer;
	}
	
	public String getQOTD() throws Exception{
		//Ask server for quote
		return OutToServer("getQuote");
	}
	
	public String getQOTDAuthor() throws Exception{
		//Ask server for quote author
		return OutToServer("getAuthor");
	}
	
	public void createNewCalendar(String gsonString) throws Exception{
		//Tell server to import calendar for current user
		OutToServer(gsonString);
	}

	public void importNewCalendar(String currentUser) throws Exception{
		//Tell server to import calendar for current user
		OutToServer("{\"overallID\":\"importCalendar\",\"email\":\"" + currentUser + "\"}");
	}
	
	public String getAvailableCalendars(String currentUser) throws Exception{
		//Tell server to import calendar for current user
		return OutToServer("{\"overallID\":\"getAvailableCalendars\",\"email\":\"" + currentUser + "\"}");
	}
	
	public String getCalendarID(String currentUser, String requestedID) throws Exception{
		//Tell server to import calendar for current user
		return OutToServer("{\"overallID\":\"getCalendarID\",\"email\":\"" + currentUser + "\" ,\"calendarToDisplay\":\"" + requestedID + "\"}");
	}
	
	public String LoadCalendarData(String currentUser, String calendarToDisplay) throws Exception{
		//Tell server to retrieve calendar data for current user
		return OutToServer("{\"overallID\":\"retrieveCalendar\",\"email\":\"" + currentUser + "\" ,\"calendarToDisplay\":\"" + calendarToDisplay + "\"}");
	}
	
	public String calendarExists(String currentUser, String calendarName) throws Exception{
		//Tell server to check if calendar exists
		return OutToServer("{\"overallID\":\"calendarExists\",\"email\":\"" + currentUser + "\" ,\"calendarName\":\"" + calendarName + "\"}");
	}
	
	public void insertNewNote(String currentUser, String eventID, String noteText) throws Exception{
		//Tell server to add a new note to event
		OutToServer("{\"overallID\":\"insertNewNote\",\"email\":\"" + currentUser + "\" ,\"eventid\":\"" + eventID + "\" ,\"text\":\"" + noteText + "\"}");
	}
	
	public void removeNote(String currentUser, String noteID) throws Exception{
		//Tell server to add a new note to event
		OutToServer("{\"overallID\":\"removeNote\",\"email\":\"" + currentUser + "\" ,\"noteid\":\"" + noteID + "\"}");
	}

	public String refreshNotes(String eventID) throws Exception{
		//Tell server to add a new note to event
		return OutToServer("{\"overallID\":\"getNotes\",\"eventid\":\"" + eventID + "\"}");
	}
	
	public void insertEvent(String currentUser, String dateOfEvent, String startTime, String endTime, String selectedCalendarID, String eventText) throws Exception{
		//Tell server to import calendar for current user
		OutToServer("{\"overallID\":\"insertEvent\",\"email\":\"" + currentUser + "\" ,\"dateOfEvent\":\"" + dateOfEvent + 
				"\" ,\"startTime\":\"" + startTime + "\" ,\"endTime\":\"" + endTime + "\" ,\"calendarID\":\"" + selectedCalendarID + "\" ,\"eventText\":\"" 
				+ eventText + "\"}");
	}
	
	public void removeEvent(String eventID) throws Exception{
		//Tell server to add a new note to event
		OutToServer("{\"overallID\":\"removeEvent\",\"eventid\":\"" + eventID + "\"}");
	}
	
	public String getWeatherData() throws Exception{
		//Tell server to add a new note to event
		return OutToServer("getWeatherData");
	}
	
	public boolean validateLogin(String gsonUser) throws Exception{
				
		// Send to server
		String inFromServer = OutToServer(gsonUser);
		
		double inFromServerDouble = Double.parseDouble(inFromServer);
		
		// Return true if user is valid
		if(inFromServerDouble == 0){
			return true;
		}
		
		return false;
		
	}

}
