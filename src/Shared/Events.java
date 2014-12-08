package Shared;

import java.util.ArrayList;
import java.util.Arrays;


public class Events {
    ArrayList<EventInfo> events = new ArrayList<EventInfo>();
    
    
    String currentUser;


    public void setEvents(ArrayList<EventInfo> event) {
        this.events = event;
    }
    
    public ArrayList<EventInfo> getEvents(){
    	return events;
    }
    
    // Converts array events to a String
    @Override
    public String toString() {
        return Arrays.toString(events.toArray());
    }
    
}