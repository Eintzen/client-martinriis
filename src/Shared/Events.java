package Shared;

import java.util.ArrayList;
import java.util.Arrays;


public class Events {
    ArrayList<Event> events = new ArrayList<Event>();
    
    
    String currentUser;


    public void setEvents(ArrayList<Event> event) {
        this.events = event;
    }
    
    public ArrayList<Event> getEvents(){
    	return events;
    }
    
    // Converts array events to a String
    @Override
    public String toString() {
        return Arrays.toString(events.toArray());
    }
    
}