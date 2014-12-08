package GUI;

import javax.swing.JPanel;

import java.awt.CardLayout;

public class MainContainer extends JPanel {
	
	// constants for all panels
	public static final String LOGIN = "LOGIN";
	public static final String CALENDAR = "CALENDAR";
	public static final String CREATEEVENT = "CREATEEVENT";
	private static MainContainer mainCon;
	
	public MainContainer() {
				
		mainCon = this;
		setLayout(new CardLayout()); // use cardlayout
		
		LoginPanel login = new LoginPanel();
		add(login, LOGIN);
		
		CalendarPanel calendar = new CalendarPanel();
		add(calendar, CALENDAR);
		
		CreateEvent createEvent = new CreateEvent();
		add(createEvent, CREATEEVENT);
		
		switchPanel(LOGIN); // set login-panel as default
		
	}
	
	// method for switching between panels
	public static void switchPanel(String panel){
		CardLayout layout = (CardLayout) mainCon.getLayout();
		layout.show(mainCon, panel); // show panel that was passed to method
	}
	
}
