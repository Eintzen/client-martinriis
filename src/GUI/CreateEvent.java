package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import GUI.MainContainer;
import Logic.Quote;
import Logic.CalendarLogic;
import Logic.LoadCalendarData;
import Logic.EventLogic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.awt.FlowLayout;

public class CreateEvent extends JPanel {
	private JLabel logoImg;
	private MainContainer mainCon;
	private JLabel lblCalendarHeading;
	private JLabel lblQuote;
	private JLabel lblQuoteAuthor;
	static JLabel lblMonth, lblYear, lblPleaseSelectA;
    static JButton btnPrev, btnNext;
    static JTable tblCalendar;
    static JComboBox cmbYear, comboBox, comboBox_1;
    static DefaultTableModel mtblCalendar; //Table model
    static JScrollPane stblCalendar; //The scrollpane
    static JPanel pnlCalendar;
    static int realYear, realMonth, realDay, currentYear, currentMonth;
    LoadCalendarData lcd = LoadCalendarData.SingletonInstance();
    private static JTextField textField;
    private JLabel lblSelectCalendar;
    private JComboBox changeCalendar;
    private JButton btnbackButton;
    private JLabel lblYyyymmdd;
    private JLabel lblEventText;
    private static JTextField textField_event;
    private JLabel lblYourEventWas;
	
	public CreateEvent() { //constructor
		
		setSize(1200, 700);
		setLayout(null);
		
		//Create controls
        lblMonth = new JLabel ("January");
        lblYear = new JLabel ("Change year:");
        cmbYear = new JComboBox();
        btnPrev = new JButton ("<<");
        btnNext = new JButton (">>");
        mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        tblCalendar = new JTable(mtblCalendar);
        tblCalendar.setCellSelectionEnabled(true);
        stblCalendar = new JScrollPane(tblCalendar);
        pnlCalendar = new JPanel(null);
        
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
        
        tblCalendar.addMouseListener(new MouseAdapter() {
        	  public void mouseClicked(MouseEvent e) {
        	    if (e.getClickCount() == 1) {
        	      JTable target = (JTable)e.getSource();
        	      int row = target.getSelectedRow();
        	      int column = target.getSelectedColumn();
        	      String currentDayString = mtblCalendar.getValueAt(row, column).toString();
        	      int currentDay = Integer.parseInt(currentDayString);
        	      int daysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        	      //lcd.requestedDates(currentYear, currentMonth, currentDay, daysInMonth);
        	      String year = String.valueOf(currentYear);
        	      String month = String.valueOf(currentMonth);
        	      String day = String.valueOf(currentDay);
        	      textField.setText(year + "-" + month + "-" + day);
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
		lblCalendarHeading = new JLabel("<html><div style='text-align:center;'>Create Event</div></html>");
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
			
			JLabel lblDateOfEvent = new JLabel("Date of Event");
			lblDateOfEvent.setBounds(338, 105, 114, 16);
			add(lblDateOfEvent);
			
			textField = new JTextField();
			textField.setBackground(new Color(220, 220, 220));
			textField.setEditable(false);
			textField.setBounds(478, 99, 134, 28);
			add(textField);
			textField.setColumns(10);
			
			JLabel lblTimeOfEvent = new JLabel("Start time of Event");
			lblTimeOfEvent.setBounds(338, 161, 128, 16);
			add(lblTimeOfEvent);
			
			comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"8:00:00", "9:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00"}));
			comboBox.setBounds(478, 157, 134, 27);
			add(comboBox);
			
			JLabel lblEndTimeOf = new JLabel("End time of Event");
			lblEndTimeOf.setBounds(338, 207, 128, 16);
			add(lblEndTimeOf);
			
			comboBox_1 = new JComboBox();
			comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"8:00:00", "9:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00"}));
			comboBox_1.setBounds(478, 203, 134, 27);
			add(comboBox_1);
			
			lblSelectCalendar = new JLabel("Select Calendar");
			lblSelectCalendar.setBounds(338, 250, 128, 16);
			add(lblSelectCalendar);
			
			changeCalendar = new JComboBox();
			changeCalendar.setBounds(478, 242, 134, 27);
			add(changeCalendar);
			
			btnbackButton = new JButton("");
			btnbackButton.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
			        if (key == KeyEvent.VK_ENTER) {
			        	mainCon.switchPanel(mainCon.CALENDAR);
			        }
				
			        
				}
				}
			);
			btnbackButton.setIcon(new ImageIcon(CreateEvent.class.getResource("/left224.png")));
			btnbackButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainCon.switchPanel(mainCon.CALENDAR);
				}
			});
			btnbackButton.setBounds(1077, 481, 117, 103);
			btnbackButton.setBorderPainted(false);
			add(btnbackButton);
			
			lblYyyymmdd = new JLabel("yyyy-mm-dd");
			lblYyyymmdd.setBounds(485, 87, 114, 16);
			add(lblYyyymmdd);
			
			lblEventText = new JLabel("Event text");
			lblEventText.setBounds(338, 294, 128, 16);
			add(lblEventText);
			
			textField_event = new JTextField();
			textField_event.setBounds(478, 288, 258, 28);
			add(textField_event);
			textField_event.setColumns(10);
			
			JButton btnSaveEvent = new JButton("Save event");
			btnSaveEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String startDate = (String) comboBox.getSelectedItem();
					String endDate = (String) comboBox_1.getSelectedItem();
					String selectedCalendar = (String) changeCalendar.getSelectedItem();
					String eventDate = textField.getText();
					String eventText = textField_event.getText();
					EventLogic EL = new EventLogic();
					if(selectedCalendar.equals("- None -")){
						lblPleaseSelectA.setVisible(true);
						lblYourEventWas.setVisible(false);
					} else {
						lblPleaseSelectA.setVisible(false);
						lblYourEventWas.setVisible(true);
						try {
							EL.addEvent(eventDate, startDate, endDate, selectedCalendar, eventText);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
			btnSaveEvent.setBounds(338, 349, 183, 40);
			add(btnSaveEvent);
			
			lblPleaseSelectA = new JLabel("Please select a calendar or create a new one");
			lblPleaseSelectA.setVisible(false);
			lblPleaseSelectA.setForeground(Color.RED);
			lblPleaseSelectA.setBounds(561, 360, 287, 16);
			add(lblPleaseSelectA);
			
			lblYourEventWas = new JLabel("Your event was saved");
			lblYourEventWas.setForeground(Color.GREEN);
			lblYourEventWas.setBounds(561, 360, 258, 16);
			lblYourEventWas.setVisible(false);
			add(lblYourEventWas);
			
			
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				// load when component is shown
				// Import cbs calendar for current user
				CalendarLogic ic = new CalendarLogic();
				try {
					ic.getCalendars(changeCalendar);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				changeCalendar.addItem("- None -");
				// CBS Calendar should not be chosen
				changeCalendar.removeItemAt(0);
				
			}
		});
		
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
        System.out.println(mtblCalendar.getTableModelListeners());
        //Apply renderers
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
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