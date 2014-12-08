package Logic;

import Shared.Note;

import java.awt.Window.Type;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Shared.TCPClient;

public class NoteLogic {
	
	String currentUser = ApplicationState.currentUser.usermail;
	Gson gson = new GsonBuilder().create();
	ArrayList<Note> notes = new ArrayList<Note>();
	
	public void insertNote(String eventID, String noteText) throws Exception{
		
		TCPClient TcpClient = new TCPClient();
		
		TcpClient.insertNewNote(currentUser, eventID, noteText);
		
	}
	
	public void deleteNote(String noteID) throws Exception{
		
		TCPClient TcpClient = new TCPClient();
		
		TcpClient.removeNote(currentUser, noteID);
		
	}
	
	public void updateNotes(DefaultTableModel tableModel, String eventID) throws Exception{
		
		TCPClient TcpClient = new TCPClient();
		
		String inFromServer = TcpClient.refreshNotes(eventID);
		
		tableModel.setRowCount(0);
		
		// get 'notes' ArrayList from server
		java.lang.reflect.Type listType = new TypeToken<ArrayList<Note>>() {
		        }.getType();
		notes = new Gson().fromJson(inFromServer, listType);
		
		for (int i = 0; i < notes.size(); i++){
			String created = notes.get(i).getDateTime();
			String note = notes.get(i).getText();
			int ID = notes.get(i).getNoteID();
			String createdBy = notes.get(i).getCreatedBy();
			Object[] objs = {note, createdBy, created, ID};
            tableModel.addRow(objs);
		}
		
	}

}
