package Logic;

import java.util.ArrayList;

import javax.swing.JComboBox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Shared.CreateCalendar;
import Shared.Note;
import Shared.TCPClient;

public class CalendarLogic {
	
	TCPClient TcpClient = new TCPClient();
	String currentUser = ApplicationState.currentUser.usermail;
	ArrayList<String> availableCalendars = new ArrayList<String>();
	
	public void importCbsCalendar() throws Exception{
		
		TcpClient.importNewCalendar(currentUser);
	}
	
	public void createNewCalendar(String calendarName) throws Exception{
		
		Gson gson = new GsonBuilder().create();
		CreateCalendar CC = new CreateCalendar();
		CC.setCalenderName(calendarName);
		CC.setCustomCalendar(1);
		CC.setPublicOrPrivate(1);
		CC.setUserName(currentUser);
		String gsonString = gson.toJson(CC);
		
		TcpClient.createNewCalendar(gsonString);
		
	}
	
	public void getCalendars(JComboBox calendars) throws Exception{
		calendars.removeAllItems();
		String inFromServer = TcpClient.getAvailableCalendars(currentUser);
		// get 'availableCalendars' ArrayList from server
		java.lang.reflect.Type listType = new TypeToken<ArrayList<String>>() {
		        }.getType();
		availableCalendars = new Gson().fromJson(inFromServer, listType);
		
		for (int i = 0; i < availableCalendars.size(); i++){
			String userCalendar = availableCalendars.get(i);
			calendars.addItem(userCalendar);
		}
	}
	
	public boolean calendarExists(String calendarName) throws Exception{
		String inFromServer = TcpClient.calendarExists(currentUser, calendarName);
		System.out.println("in from server:" + inFromServer);
		if(inFromServer.equals("exists")){
			return true;
		}
		return false;
	}
	
	public String getCalendarID(String requestedCalendar) throws Exception{

		String inFromServer = TcpClient.getCalendarID(currentUser, requestedCalendar);
		
		return inFromServer;
	}

}
