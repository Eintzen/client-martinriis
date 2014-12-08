package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import GUI.MainContainer;
import Logic.EventLogic;
import Logic.Quote;
import Logic.NoteLogic;
import Logic.CalendarLogic;
import Logic.LoadCalendarData;
import Logic.WeatherData;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.annotation.Target;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.awt.FlowLayout;

import javax.swing.table.TableModel;

public class CalendarPanel extends JPanel {
	private JLabel logoImg;
	private static JLabel notesLabel;
	private static boolean weekView;
	private static String requestedCal;
	private MainContainer mainCon;
	private JLabel lblCalendarHeading;
	private JLabel lblQuote;
	private JLabel lblQuoteAuthor;
	private static JLabel lblFirstDate, lblCalendarExists, lblSecondDate, lblThirdDate, lblFourthDate, lblFifthDate, lblSixthDate, lblSeventhDate, lblPleaseWait;
	static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext, event, btnAddNote;
    static JTable tblCalendar;
    static JComboBox cmbYear, changeCalendar;
    static DefaultTableModel mtblCalendar, notesTableModel; //Table model
    static JScrollPane stblCalendar; //The scrollpane
    static JPanel pnlCalendar;
    static int realYear, realMonth, realDay, currentYear, currentMonth;
    private static JPanel firstDate;
    private static JPanel secondDate;
    private static JPanel thirdDate;
    private static JPanel fourthDate;
    private static JPanel fifthDate;
    private static JPanel sixthDate;
    private static JPanel seventhDate;
    LoadCalendarData lcd = LoadCalendarData.SingletonInstance();
    private static JScrollPane scrollPane;
    private static JButton btnCreateEvent;
    private static JScrollPane scrollPane_7;
    private static JScrollPane notesScrollPane;
    private static JScrollPane scrollPane_8;
    private static JScrollPane scrollPane_9;
    private static JScrollPane scrollPane_10;
    private static JScrollPane scrollPane_11;
    private static JScrollPane scrollPane_12;
    private JButton BackButton;
    private JLabel lblNewCalendar;
    private JLabel label;
    private static JLabel lblDatetime;
    private JTable notesTable;
    private static String currentEventID;
    private static JButton btnDeleteNote;
    private static JButton btnDeleteEvent;
    private JLabel lblTodaysWeather;
    private JLabel lblTemperature;
    private JLabel lblTemperatureSummary;
    private JLabel tempLabel;
	
	public CalendarPanel() {
		setBackground(new Color(250, 235, 215)); //constructor
		
		setSize(1200, 700);
		setLayout(null);
		
		//Create controls
        lblMonth = new JLabel ("January");
        lblYear = new JLabel ("Choose year:");
        cmbYear = new JComboBox();
        btnPrev = new JButton ("<<");
        btnNext = new JButton (">>");
        mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        tblCalendar = new JTable(mtblCalendar);
        tblCalendar.setCellSelectionEnabled(true);
        stblCalendar = new JScrollPane(tblCalendar);
        pnlCalendar = new JPanel(null);
        
        String[] columnNames = {"Note", "Author", "Date", "ID"};
        
      //Register action listeners
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        cmbYear.addActionListener(new cmbYear_Action());
        
        //Add controls to pane
        add(pnlCalendar);
        pnlCalendar.add(lblMonth);
        pnlCalendar.add(lblYear);
        pnlCalendar.add(cmbYear);
        pnlCalendar.add(btnPrev);
        pnlCalendar.add(btnNext);
        pnlCalendar.add(stblCalendar);
        
      //Set bounds
        pnlCalendar.setBounds(6, 6, 320, 335);
        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 100, 25);
        lblYear.setBounds(10, 305, 99, 20);
        cmbYear.setBounds(211, 305, 99, 20);
        btnPrev.setBounds(10, 25, 50, 25);
        btnNext.setBounds(260, 25, 50, 25);
        stblCalendar.setBounds(10, 50, 300, 250);
        
        
        //Get real month/year
        final GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;
                
        //Add headers
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            mtblCalendar.addColumn(headers[i]);
        }
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(338, 89, 120, 386);
		add(scrollPane);
		
		firstDate = new JPanel();
		scrollPane.setViewportView(firstDate);
		firstDate.setBackground(Color.WHITE);
		firstDate.setLayout(new GridLayout(0,1));
		
		scrollPane_8 = new JScrollPane();
		scrollPane_8.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_8.setBounds(459, 89, 120, 386);
		add(scrollPane_8);
		
		secondDate = new JPanel();
		scrollPane_8.setViewportView(secondDate);
		secondDate.setBackground(Color.WHITE);
		secondDate.setLayout(new GridLayout(0,1));
		secondDate.setVisible(true);
		
		scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(580, 89, 120, 386);
		add(scrollPane_7);

		thirdDate = new JPanel();
		scrollPane_7.setViewportView(thirdDate);
		thirdDate.setBackground(Color.WHITE);
		thirdDate.setVisible(true);
		thirdDate.setLayout(new GridLayout(0,1));
		
		
		scrollPane_9 = new JScrollPane();
		scrollPane_9.setBounds(701, 89, 120, 386);
		add(scrollPane_9);
		
		fourthDate = new JPanel();
		scrollPane_9.setViewportView(fourthDate);
		fourthDate.setBackground(Color.WHITE);
		fourthDate.setVisible(true);
		fourthDate.setLayout(new GridLayout(0,1));
		
		scrollPane_10 = new JScrollPane();
		scrollPane_10.setBounds(822, 89, 120, 386);
		add(scrollPane_10);
		
		fifthDate = new JPanel();
		scrollPane_10.setViewportView(fifthDate);
		fifthDate.setBackground(Color.WHITE);
		fifthDate.setVisible(true);
		fifthDate.setLayout(new GridLayout(0,1));	

		scrollPane_11 = new JScrollPane();
		scrollPane_11.setBounds(943, 89, 120, 386);
		add(scrollPane_11);
		
		sixthDate = new JPanel();
		scrollPane_11.setViewportView(sixthDate);
		sixthDate.setBackground(Color.WHITE);
		sixthDate.setVisible(true);
		sixthDate.setLayout(new GridLayout(0,1));		
		
		scrollPane_12 = new JScrollPane();
		scrollPane_12.setBounds(1064, 89, 120, 386);
		add(scrollPane_12);
		
		seventhDate = new JPanel();
		scrollPane_12.setViewportView(seventhDate);
		seventhDate.setBackground(Color.WHITE);
		seventhDate.setVisible(true);
		seventhDate.setLayout(new GridLayout(0,1));	
		
		// hide until loaded
		scrollPane.setVisible(false);
		scrollPane_8.setVisible(false);
		scrollPane_7.setVisible(false);
		scrollPane_9.setVisible(false);
		scrollPane_10.setVisible(false);
		scrollPane_11.setVisible(false);
		scrollPane_12.setVisible(false);
		
		lblFirstDate = new JLabel("");
		lblFirstDate.setBounds(348, 71, 110, 16);
		add(lblFirstDate);
		
		lblSecondDate = new JLabel("");
		lblSecondDate.setBounds(470, 71, 110, 16);
		add(lblSecondDate);
		
		lblThirdDate = new JLabel("");
		lblThirdDate.setBounds(593, 71, 110, 16);
		add(lblThirdDate);
		
		lblFourthDate = new JLabel("");
		lblFourthDate.setBounds(711, 71, 110, 16);
		add(lblFourthDate);
		
		lblFifthDate = new JLabel("");
		lblFifthDate.setBounds(833, 71, 110, 16);
		add(lblFifthDate);
		
		lblSixthDate = new JLabel("");
		lblSixthDate.setBounds(955, 71, 110, 16);
		add(lblSixthDate);
		
		lblSeventhDate = new JLabel("");
		lblSeventhDate.setBounds(1074, 71, 110, 16);
		add(lblSeventhDate);
        
        tblCalendar.addMouseListener(new MouseAdapter() {
        	  public void mouseClicked(MouseEvent e) {
        	    if (e.getClickCount() == 1) {
        	      JTable target = (JTable)e.getSource();
        	      int row = target.getSelectedRow();
        	      int column = target.getSelectedColumn();
        	      String currentDayString = mtblCalendar.getValueAt(row, column).toString();
        	      int currentDay = Integer.parseInt(currentDayString);
        	      int daysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        	      lcd.requestDates(currentYear, currentMonth, currentDay, daysInMonth);
        	      lblFirstDate.setText(lcd.getLabelDates().get(0));
        	      lblSecondDate.setText(lcd.getLabelDates().get(1));
        	      lblThirdDate.setText(lcd.getLabelDates().get(2));
        	      lblFourthDate.setText(lcd.getLabelDates().get(3));
        	      lblFifthDate.setText(lcd.getLabelDates().get(4));
        	      lblSixthDate.setText(lcd.getLabelDates().get(5));
        	      lblSeventhDate.setText(lcd.getLabelDates().get(6));
        	      try {
					lcd.LoadCalendarFromServer(requestedCal);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
        	      try {
					populateTables();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	    }
        	  }
        	});
          
          tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background
          
          //No resize/reorder
          tblCalendar.getTableHeader().setResizingAllowed(false);
          tblCalendar.getTableHeader().setReorderingAllowed(false);
          
          //Single cell selection
          tblCalendar.setColumnSelectionAllowed(true);
          tblCalendar.setRowSelectionAllowed(true);
          tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
          
          //Set row/column count
          tblCalendar.setRowHeight(38);
          mtblCalendar.setColumnCount(7);
          mtblCalendar.setRowCount(6);
          
          //Populate table
          for (int i=realYear-100; i<=realYear+100; i++){
              cmbYear.addItem(String.valueOf(i));
          }
          
          //Refresh calendar
          refreshCalendar (realMonth, realYear); //Refresh calendar
		
		// Header message
		lblCalendarHeading = new JLabel("<html><div style='text-align:center;'>Your calendar</div></html>");
		lblCalendarHeading.setBounds(338, 16, 210, 59);
		lblCalendarHeading.setFont(new Font("Helvetica", Font.BOLD, 20));
		add(lblCalendarHeading);
		
		// Quote of the day
		lblQuote = new JLabel("Quote");
		lblQuote.setBounds(6, 494, 300, 155);
		lblQuote.setVerticalAlignment(JLabel.BOTTOM);
		add(lblQuote);
		
		// Quote of the day author
		lblQuoteAuthor = new JLabel("Quote Author");
		lblQuoteAuthor.setBounds(6, 639, 229, 40);
		add(lblQuoteAuthor);
	
		// Load quote of the day
		Quote quote = new Quote();
		try {
			lblQuote.setText("<html>" + quote.getQuote() + "</html>");
			lblQuoteAuthor.setText("<html><strong> - " + quote.getAuthor() + "</strong></html>");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
			btnCreateEvent = new JButton("");
			btnCreateEvent.setIcon(new ImageIcon(CalendarPanel.class.getResource("/add98.png")));
			btnCreateEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCon.switchPanel(mainCon.CREATEEVENT);
				}
			});
			btnCreateEvent.setBounds(1007, 487, 69, 69);
			btnCreateEvent.setBorderPainted(false);
			add(btnCreateEvent);
			
			BackButton = new JButton("");
			BackButton.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
			        if (key == KeyEvent.VK_ENTER) {
			        	mainCon.switchPanel(mainCon.LOGIN);
			        }
				}
				}
			);
			BackButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					mainCon.switchPanel(mainCon.LOGIN);
				}
			});
			BackButton.setIcon(new ImageIcon(CalendarPanel.class.getResource("/door1364PX.png")));
			BackButton.setBounds(1105, 593, 79, 86);
			BackButton.setBorderPainted(false);
			add(BackButton);
			
			lblCalendarExists = new JLabel("<html>Similar calendar is already<br />existing</html>");
			lblCalendarExists.setForeground(Color.RED);
			lblCalendarExists.setBounds(992, 601, 110, 63);
			lblCalendarExists.setVisible(false);
			add(lblCalendarExists);
			
			JButton lblCreateNewCalendar = new JButton("");
			lblCreateNewCalendar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					 CalendarLogic ic = new CalendarLogic();
					 String calendarName = JOptionPane.showInputDialog(null, "Calendar Name", null);
					 try {
						System.out.println(ic.calendarExists(calendarName));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 try {
						// Create calendar if user hasn't already created one by the same name
						if(!ic.calendarExists(calendarName)){
							ic.createNewCalendar(calendarName);
							ic.getCalendars(changeCalendar);
							lblCalendarExists.setVisible(false);
						 } else {
							 lblCalendarExists.setVisible(true);
						 }
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			});
			lblCreateNewCalendar.setIcon(new ImageIcon(CalendarPanel.class.getResource("/calendar146.png")));
			lblCreateNewCalendar.setBounds(1098, 490, 64, 59);
			add(lblCreateNewCalendar);
			
			lblNewCalendar = new JLabel("<html>" + "Create New" + "<br />"  + "Calendar" + "</html>");
			lblNewCalendar.setBounds(1098, 549, 79, 40);
			add(lblNewCalendar);
			
			label = new JLabel("<html>Create New<br />Event</html>");
			label.setBounds(1007, 549, 79, 40);
			add(label);
			
			notesLabel = new JLabel("Event notes");
			notesLabel.setVisible(false);
			notesLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
			notesLabel.setBounds(338, 474, 525, 59);
			add(notesLabel);
			
			lblDatetime = new JLabel("Datetime");
			lblDatetime.setBounds(338, 517, 659, 16);
			lblDatetime.setVisible(false);
			add(lblDatetime);
			
			notesTableModel = new DefaultTableModel(columnNames, 0);
			
			notesTable = new JTable(notesTableModel){
				@Override
		        public boolean isCellEditable(int row, int col)  
		        {  
		            return false; 
		        }
		        
			};
			
			notesTable.setRowSelectionAllowed(true);
			notesTable.getColumnModel().getColumn(3).setPreferredWidth(0);
			notesTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
			notesTable.setFillsViewportHeight(true);
			notesTable.setBounds(338, 549, 643, 110);
			
			notesScrollPane = new JScrollPane();
			notesScrollPane.setVisible(false);
			notesScrollPane.setViewportView(notesTable);
			notesScrollPane.setBounds(340, 540, 640, 90);
			add(notesScrollPane);
			
			btnAddNote = new JButton("Add note");
			btnAddNote.setBounds(335, 635, 117, 29);
			btnAddNote.setVisible(false);
			add(btnAddNote);
			
			btnAddNote.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String noteText = JOptionPane.showInputDialog(null, "Indtast note:", null);
					NoteLogic note = new NoteLogic();
					try {
						note.insertNote(currentEventID, noteText);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						note.refreshNotes(notesTableModel, currentEventID);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			lblPleaseWait = new JLabel("<html>" + "Loading..."+ "<br />" + "Please wait" + "</html>");
			lblPleaseWait.setFont(new Font("Helvetica", Font.BOLD, 18));
			lblPleaseWait.setBounds(701, 150, 130, 59);
			lblPleaseWait.setVisible(true);
			add(lblPleaseWait);
			
			btnDeleteNote = new JButton("Delete note");
			btnDeleteNote.setVisible(false);
			btnDeleteNote.setBounds(450, 635, 117, 29);
			add(btnDeleteNote);
			
			JButton btnWeekView = new JButton("Weekly view");
			btnWeekView.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					weekView = true;
					changeView();
				}
			});
			btnWeekView.setBounds(1084, 30, 103, 29);
			add(btnWeekView);
			
			JButton btnDayView = new JButton("Daily view");
			btnDayView.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					weekView = false;
					changeView();
				}
			});
			btnDayView.setBounds(985, 30, 103, 29);
			add(btnDayView);
			
			JLabel lblChangeCalendar = new JLabel("Choose calendar:");
			lblChangeCalendar.setBounds(15, 345, 120, 16);
			add(lblChangeCalendar);
			
			changeCalendar = new JComboBox();
			changeCalendar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CalendarLogic ic = new CalendarLogic();
					String selectedCal = "";
					selectedCal = (String) changeCalendar.getSelectedItem();
					try {
						requestedCal = ic.getCalendarID(selectedCal);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						lcd.LoadCalendarFromServer(requestedCal);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						populateTables();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//Hide notes when calendar changed
					getNotesLabel().setVisible(false);
					getLblDatetime().setVisible(false);
					getNotesScrollPane().setVisible(false);
					getBtnAddNote().setVisible(false);
					getBtnDeleteNote().setVisible(false);
					getBtnDeleteEvent().setVisible(false);
				}
			});
			changeCalendar.setBounds(216, 345, 99, 20);
			add(changeCalendar);
			
			btnDeleteEvent = new JButton("Delete event");
			btnDeleteEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					EventLogic EL = new EventLogic();
					// remove custom event
					try {
						EL.removeEvent(currentEventID);
					} catch (Exception e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					// load events from server after event is removed
					try {
						lcd.LoadCalendarFromServer(requestedCal);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					// populate tables after event is removed
	        	      try {
						populateTables();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnDeleteEvent.setBounds(868, 635, 117, 29);
			btnDeleteEvent.setVisible(false);
			add(btnDeleteEvent);
			
			lblTodaysWeather = new JLabel("<html><strong>Today's weather</strong></html>");
			lblTodaysWeather.setBounds(15, 403, 120, 16);
			add(lblTodaysWeather);
			
			lblTemperature = new JLabel("Temperature:");
			lblTemperature.setBounds(15, 431, 246, 16);
			add(lblTemperature);
			
			lblTemperatureSummary = new JLabel("Description: ");
			lblTemperatureSummary.setBounds(15, 459, 300, 16);
			add(lblTemperatureSummary);
			
			tempLabel = new JLabel("");
			tempLabel.setIcon(new ImageIcon(CalendarPanel.class.getResource("/barometer.png")));
			tempLabel.setBounds(136, 403, 61, 72);
			add(tempLabel);
			
			btnDeleteNote.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NoteLogic note = new NoteLogic();
					int row = notesTable.getSelectedRow();
	        		int noteID = (int) notesTable.getValueAt(row, 3);
	        		String noteIDstring = String.valueOf(noteID);
	        		try {
						note.removeNote(noteIDstring);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						note.refreshNotes(notesTableModel, currentEventID);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				// load when component is shown
      	        int daysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
      	        lcd.currentDate(currentYear, currentMonth, realDay, daysInMonth);
      	        // Update date-labels
      	        lblFirstDate.setText(lcd.getLabelDates().get(0));
    	        lblSecondDate.setText(lcd.getLabelDates().get(1));
    	        lblThirdDate.setText(lcd.getLabelDates().get(2));
    	        lblFourthDate.setText(lcd.getLabelDates().get(3));
    	        lblFifthDate.setText(lcd.getLabelDates().get(4));
    	        lblSixthDate.setText(lcd.getLabelDates().get(5));
    	        lblSeventhDate.setText(lcd.getLabelDates().get(6));
    	        
    	        //Request weather data from server
    	        WeatherData WD = new WeatherData();
    	        try {
					WD.getWeatherData();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
    	        //Display today's weather
    	        lblTemperature.setText("Temperature: " + WD.getTemperature(0));
    	        lblTemperatureSummary.setText("Description: " + WD.getDescription(0));
    	        
				// Import cbs calendar for current user
				CalendarLogic ic = new CalendarLogic();
				try {
					ic.importCbsCalendar();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					ic.getCalendars(changeCalendar);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					//Show CBS Calendar upon load
					lcd.LoadCalendarFromServer(requestedCal);
					System.out.println("calendarID returned from server: " + ic.getCalendarID("my test cal"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					populateTables();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// show when loaded
				scrollPane.setVisible(true);
				scrollPane_8.setVisible(true);
				scrollPane_7.setVisible(true);
				scrollPane_9.setVisible(true);
				scrollPane_10.setVisible(true);
				scrollPane_11.setVisible(true);
				scrollPane_12.setVisible(true);
				lblPleaseWait.setVisible(false);
			}
		});
		
	}
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	public static JButton getBtnCreateEvent() {
		return btnCreateEvent;
	}
	public void setBtnCreateEvent(JButton btnCreateEvent) {
		this.btnCreateEvent = btnCreateEvent;
	}
	public static JButton getBtnDeleteEvent() {
		return btnDeleteEvent;
	}
	public void setBtnDeleteEvent(JButton btnDeleteEvent) {
		this.btnDeleteEvent = btnDeleteEvent;
	}
	public static JButton getBtnDeleteNote() {
		return btnDeleteNote;
	}
	public void setBtnDeleteNote(JButton btnDeleteNote) {
		this.btnDeleteNote = btnDeleteNote;
	}
	public static JButton getBtnAddNote() {
		return btnAddNote;
	}
	public static void setBtnAddNote(JButton btnAddNote) {
		CalendarPanel.btnAddNote = btnAddNote;
	}
	public static JScrollPane getNotesScrollPane() {
		return notesScrollPane;
	}
	public void setNotesScrollPane(JScrollPane notesScrollPane) {
		this.notesScrollPane = notesScrollPane;
	}
	public static JLabel getLblDatetime() {
		return lblDatetime;
	}
	public void setLblDatetime(JLabel lblDatetime) {
		this.lblDatetime = lblDatetime;
	}
	public static JLabel getNotesLabel() {
		return notesLabel;
	}
	public void setNotesLabel(JLabel notesLabel) {
		this.notesLabel = notesLabel;
	}
	public static void refreshCalendar(int month, int year){
        //Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month
        
        //Allow/disallow buttons
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
        if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        //Clear table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                mtblCalendar.setValueAt(null, i, j);
            }
        }
        
        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        //Draw calendar
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            mtblCalendar.setValueAt(i, row, column);
        }
        //Apply renderers
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
    }
	
	public static void changeView(){
		if(weekView){
			scrollPane.setBounds(338, 89, 120, 386);
			scrollPane.setVisible(true);
			scrollPane_8.setVisible(true);
			scrollPane_7.setVisible(true);
			scrollPane_9.setVisible(true);
			scrollPane_10.setVisible(true);
			scrollPane_11.setVisible(true);
			scrollPane_12.setVisible(true);
			lblFirstDate.setBounds(348, 71, 110, 16);
			lblFirstDate.setVisible(true); 
			lblSecondDate.setVisible(true);
			lblThirdDate.setVisible(true); 
			lblFourthDate.setVisible(true);
			lblFifthDate.setVisible(true);
			lblSixthDate.setVisible(true);
			lblSeventhDate.setVisible(true);
		} else {
			scrollPane.setBounds(338, 89, 845, 386);
			scrollPane.setVisible(true);
			scrollPane_8.setVisible(false);
			scrollPane_7.setVisible(false);
			scrollPane_9.setVisible(false);
			scrollPane_10.setVisible(false);
			scrollPane_11.setVisible(false);
			scrollPane_12.setVisible(false);
			lblFirstDate.setBounds(705, 71, 110, 16);
			lblFirstDate.setVisible(true); 
			lblSecondDate.setVisible(false);
			lblThirdDate.setVisible(false); 
			lblFourthDate.setVisible(false);
			lblFifthDate.setVisible(false);
			lblSixthDate.setVisible(false);
			lblSeventhDate.setVisible(false);
		}
	}
	
	public static void populateTables() throws Exception{
		LoadCalendarData lcd = LoadCalendarData.SingletonInstance();
		
		// Clear all existing data in calendar tables, if any
		firstDate.removeAll();
		firstDate.revalidate();
		firstDate.repaint();
		secondDate.removeAll();
		secondDate.revalidate();
		secondDate.repaint();
		thirdDate.removeAll();
		thirdDate.revalidate();
		thirdDate.repaint();
		fourthDate.removeAll();
		fourthDate.revalidate();
		fourthDate.repaint();
		fifthDate.removeAll();
		fifthDate.revalidate();
		fifthDate.repaint();
		sixthDate.removeAll();
		sixthDate.revalidate();
		sixthDate.repaint();
		seventhDate.removeAll();
		seventhDate.revalidate();
		seventhDate.repaint();
		
		System.out.println("matched events size:" + lcd.getMatchedEvents().size());
		
		for(int i = 0; i < lcd.getMatchedEvents().size(); i++){
			// full string of all matched events
			String matchedEvents = lcd.getMatchedEvents().get(i);
			// splits string into individual events
			String[] eventAttributes = matchedEvents.split(" . ");
			final String eventUniqueID = eventAttributes[0];
			final String eventStart = eventAttributes[1];
			final String eventEnd = eventAttributes[2];
			final String eventType = eventAttributes[3];
			final String eventTitle = eventAttributes[4];
			String insertInto = eventAttributes[5];
			System.out.println("matched parts: Unique ID: " + eventUniqueID + " event starts: " + eventStart + " eventEnd: " + eventEnd + " eventType: " + eventType
					+ " eventTitle: " + eventTitle + " insert into: " + insertInto);
			event = new JButton("<html>" + eventTitle + "<br />" + eventStart + "</html>");
			event.setFont(new Font("Helvetica", Font.BOLD, 11));
			event.setPreferredSize(new Dimension(110, 75));
			event.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NoteLogic note = new NoteLogic();
					currentEventID = eventUniqueID;
					// show notes
					getNotesLabel().setVisible(false);
					getNotesLabel().setText("Noter for " + eventTitle);
					getNotesLabel().setVisible(true);
					getLblDatetime().setVisible(false);
					getLblDatetime().setText(eventStart + " | " + eventEnd + " (" + eventType + ")");
					getLblDatetime().setVisible(true);
					getNotesScrollPane().setVisible(true);
					getBtnAddNote().setVisible(true);
					getBtnDeleteNote().setVisible(true);
					try {
						note.refreshNotes(notesTableModel, eventUniqueID);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			// determine in which column to insert events
			if(insertInto.equals("0")){
				firstDate.add(event);
			}
			if(insertInto.equals("1")){
				secondDate.add(event);
			}
			if(insertInto.equals("2")){
				thirdDate.add(event);
			}
			if(insertInto.equals("3")){
				fourthDate.add(event);
			}
			if(insertInto.equals("4")){
				fifthDate.add(event);
			}
			if(insertInto.equals("5")){
				sixthDate.add(event);
			}
			if(insertInto.equals("6")){
				seventhDate.add(event);
			}
		}
			
	}
	
	private void refreshUserList(DefaultTableModel tableModel){
    	// retrieve and display user list
		tableModel.setRowCount(0);
    }
    
    static class tblCalendarRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6){ //Week-end
                setBackground(new Color(255, 220, 220));
            }
            else{ //Week
                setBackground(new Color(255, 255, 255));
            }
            if (value != null){
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
                    setBackground(new Color(220, 220, 255));
                }
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }
    
    static class btnPrev_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 0){ //Back one year
                currentMonth = 11;
                currentYear -= 1;
            }
            else{ //Back one month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class btnNext_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 11){ //Foward one year
                currentMonth = 0;
                currentYear += 1;
            }
            else{ //Foward one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class cmbYear_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (cmbYear.getSelectedItem() != null){
                String b = cmbYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }
}