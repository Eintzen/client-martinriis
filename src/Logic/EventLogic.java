package Logic;

import Shared.TCPClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EventLogic {
	
	String currentUser = ApplicationState.currentUser.usermail;
	Gson gson = new GsonBuilder().create();
	CalendarLogic ic = new CalendarLogic();
	TCPClient TcpClient = new TCPClient();
	private static String requestedCal;
	
	public void insertEvent(String dateOfEvent, String startTime, String endTime, String selectedCalendar, String eventText) throws Exception{
		
		requestedCal = ic.getCalendarID(selectedCalendar);
		System.out.println(dateOfEvent);
		System.out.println(startTime);
		System.out.println(endTime);
		System.out.println("calID: " + requestedCal);
		System.out.println(eventText);
		
		TcpClient.insertEvent(currentUser, dateOfEvent, startTime, endTime, requestedCal, eventText);
	}
	
	public void removeEvent(String eventID) throws Exception{
		TcpClient.removeEvent(eventID);
	}

}
