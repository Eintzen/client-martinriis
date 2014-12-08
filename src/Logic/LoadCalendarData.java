package Logic;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Shared.Event;
import Shared.TCPClient;
import Shared.Events;

public class LoadCalendarData {
	
	Gson gson = new GsonBuilder().create();
	ArrayList<String> requestedDates = new ArrayList<String>();
	ArrayList<String> labelDates = new ArrayList<String>();
	ArrayList<String> matchedEvents = new ArrayList<String>();
	
	private static LoadCalendarData _instance;
	
	TCPClient TcpClient = new TCPClient();
	
	private LoadCalendarData(){
		//private contructor to avoid multiple instances
	}
	
	public static LoadCalendarData SingletonInstance(){
 	   if(_instance == null)
 		   _instance = new LoadCalendarData();
 	   return _instance;
    }
	
	public void LoadCalendarFromServer(String calendarToDisplay) throws Exception{
		
		String currentUser = ApplicationState.currentUser.usermail;
		
		String jsonFromServer = TcpClient.LoadCalendarData(currentUser, calendarToDisplay);
		
		// remove results from matchedEvents & labelDates, if any
		matchedEvents.clear();
		
		//converting gson from server to Event-objects
		Events ev = (Events)gson.fromJson(jsonFromServer, Events.class);
		System.out.println("ev:" + ev);
		
		//Loop over requested dates
		for(int i = 0; i < getRequestedDates().size(); i++){
			String requestedDate = getRequestedDates().get(i);
			System.out.println("requested from 'loadcalendar': " + requestedDate);
			
			 String[] requested_parts = requestedDate.split("-");
		     String requested_year = requested_parts[0];
		     String requested_month = requested_parts[1];
		     String daySplit = requested_parts[2];
		     String[] requested_parts_2 = daySplit.split(" ");
		     String requested_day = requested_parts_2[0];
		     
		     //Loop through events to find a matching date
		     for (int j = 0; j < ev.getEvents().size(); j++){
				  String event_startDate = ev.getEvents().get(j).getStart().get(0);
							
				 String[] parts = event_startDate.split("-");
			     String event_year = parts[0];
			     String event_month = parts[1];
			     String day = parts[2];
			     String[] parts_2 = day.split(" ");
			     String event_day = parts_2[0];
			     String[] time = event_startDate.split(":");
			     String hour = time[0];
			     String[] time_2 = hour.split(" ");
			     String event_hour = time_2[1];
			     String event_minute = time[1];
			     String event_second = time[2];
			     
			     // if requested date matches event date
			     if(event_year.equals(requested_year) && event_month.equals(requested_month) && event_day.equals(requested_day)){
			    	 String uniqueEvID = ev.getEvents().get(j).getEventid();
			    	 String eventEnd = ev.getEvents().get(j).getEnd().get(0);
			    	 String eventType = ev.getEvents().get(j).getType();
			    	 String eventTitle = ev.getEvents().get(j).getTitle();
			    	 String eventTitleClean = eventTitle.replace(" - ", " ");
			    	 String eventTime = event_year + "-" + event_month + "-" + event_day + " " + event_hour + ":" + event_minute + ":" + event_second;
			    	 String matchedEvent = uniqueEvID + " . " + eventTime + " . " + eventEnd + " . " + eventType + " . " + eventTitleClean + " . " + i;
			    	 matchedEvents.add(matchedEvent);
			     }
			}
		}
	}
	
	public void requestedDates(int year, int month, int day, int daysInMonth) {
		
		//Remove existing values, if any
		requestedDates.clear();
		
		int currentMonth = month;
		
		boolean monthChanged = false;
		
		for (int i = 0; i < 7; i++){
			
			int dayInt = day + i;
			
			if(daysInMonth < dayInt || month != currentMonth){
				dayInt = dayInt - daysInMonth;
				if (!monthChanged){
					// only change month once
					month = month + 1;
					dayInt = 1;
				}
				monthChanged = true;
			}
			
			String yearString = String.valueOf(year);
			String monthString = String.valueOf(month);
			String dayString = String.valueOf(dayInt);
			
			String requestedDate = yearString + "-" + monthString + "-" + dayString;
			System.out.println(requestedDate);
			
			requestedDates.add(requestedDate);
		}
        
    }
	
	public void currentDates(int year, int month, int day, int daysInMonth) {

		int currentMonth = month;
		
		boolean monthChanged = false;
		
		for (int i = 0; i < 7; i++){
			
			int dayInt = day + i;
			
			if(daysInMonth < dayInt || month != currentMonth){
				dayInt = dayInt - daysInMonth;
				if (!monthChanged){
					// only change month once
					month = month + 1;
					dayInt = 1;
				}
				monthChanged = true;
			}
			
			String yearString = String.valueOf(year);
			String monthString = String.valueOf(month);
			String dayString = String.valueOf(dayInt);
			
			String requestedDate = yearString + "-" + monthString + "-" + dayString;
			System.out.println(requestedDate);
			
			requestedDates.add(requestedDate);
		}
	}
	
	public ArrayList<String> getRequestedDates(){
    	return requestedDates;
    }
	
	public ArrayList<String> getLabelDates(){
		//display month correctly for labels
		//remove existing data from array, if any
		labelDates.clear();
		for(int i = 0; i < getRequestedDates().size(); i++){
			String requestedDate = getRequestedDates().get(i);
			String[] requested_parts = requestedDate.split("-");
		    String requested_year = requested_parts[0];
		    String requested_month = requested_parts[1];
		    int month_modified = Integer.parseInt(requested_month) + 1;
		    String requested_day = requested_parts[2];
		    String labelDate = requested_year + "-" + month_modified + "-" + requested_day;
		    labelDates.add(labelDate);
		}
    	return labelDates;
    }
	
	public ArrayList<String> getMatchedEvents(){
    	return matchedEvents;
    }

}
