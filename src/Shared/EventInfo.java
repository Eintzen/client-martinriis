package Shared;

import java.util.ArrayList;

public class EventInfo {
    private String activityid;
    private String eventid;
    private String type;
    private String title;
    private String description;
    private String location;
    private String createdby;
    private ArrayList<String> start;
    private ArrayList<String> end;

    // Setters and getters for the event object 
    public void setActivityid(String activityid){
        this.activityid = activityid;
    }
    public String getActivityid(){
        return activityid;
    }

    public void setEventid(String eventid){
        this.eventid = eventid;
    }
    public String getEventid(){
        return eventid;
    }

    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return location;
    }

    public void setCreatedby(String createdby){
        this.createdby = createdby;
    }
    public String getCreatedby(){
        return createdby;
    }
    
    public void setStart(ArrayList<String> start){
        this.start = start;
    }
    public ArrayList<String> getStart(){
        return start;
    }

    public void setEnd(ArrayList<String> end){
        this.end = end;
    }
    public ArrayList<String> getEnd(){
        return end;
    }
	public EventInfo(String activityid, String eventid, String type, String title,
			String description, String location, String createdby, ArrayList<String> start,
			ArrayList<String> end) {
		super();
		this.activityid = activityid;
		this.eventid = eventid;
		this.type = type;
		this.title = title;
		this.description = description;
		this.location = location;
		this.createdby = createdby;
		this.start = start;
		this.end = end;
	}
    
    

}
