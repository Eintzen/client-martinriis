package GUI;

import java.awt.Dimension;
import java.util.Locale;

import javax.swing.*;

public class CalendarFrame extends JFrame {
	
	// set size of application
		public final static Dimension SIZE = new Dimension(1200, 700);
		Locale userlocale = new Locale("en_US");
		
		public CalendarFrame(){ // Constructor
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			Locale.setDefault(userlocale);
			setResizable(false); //window should not be resizable
			
			MainContainer content = new MainContainer();
			add(content); // add maincontainer to JFrame
			
			setVisible(true);
			setTitle("CBS Calendar 1.0");
			setSize(SIZE);
		}

}
