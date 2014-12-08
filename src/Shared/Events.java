package Shared;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by jesperbruun on 10/10/14.
 * Den laver selve arrayet af alle generede Event
 */
public class Events {
    ArrayList<Event> events = new ArrayList<Event>();
    
    
    String currentUser;


    public void setEvents(ArrayList<Event> event) {
        this.events = event;
    }
    
    public ArrayList<Event> getEvents(){
    	return events;
    }
    
    // Konverterer array events til en tekst streng
    @Override
    public String toString() {
        return Arrays.toString(events.toArray());
    }
    
}