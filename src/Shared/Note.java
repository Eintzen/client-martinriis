package Shared;

public class Note {
	
	private int noteID;
	private String text;
	private String dateTime;
	private String createdBy;
	private int isActive;
	private String eventID;
	
	public Note(int noteID, String text, String dateTime, String createdBy, int isActive, String eventID) {
		super();
		this.noteID = noteID;
		this.text = text;
		this.dateTime = dateTime;
		this.createdBy = createdBy;
		this.isActive = isActive;
		this.eventID = eventID;
		
		
	}
	
	public String getEventID() {
		return eventID;
	}


	public void setEventID(String eventID) {
		this.eventID = eventID;
	}


	public int isActive() {
		return isActive;
	}


	public void setActive(int isActive) {
		this.isActive = isActive;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public int getNoteID() {
		return noteID;
	}

	public void setNoteID(int noteID) {
		this.noteID = noteID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
		
}