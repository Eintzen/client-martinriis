package GUI;
import javax.swing.*;

import GUI.MainContainer;
import Logic.ApplicationState;
import Logic.User;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {
	private JLabel userMailLbl;
	private JTextField userMail;
	private JLabel userPassLbl;
	private JPasswordField userPass;
	private JLabel logoImg;
	private MainContainer mainCon;
	private JLabel lblInvalidUserPass;
	private JLabel lblWelcomeCbsCalendar;
	private JLabel lblNewLabel;
	private JLabel lblLogIn_1;
	
	public LoginPanel() { //constructor
		
		setSize(1200, 700);
		setLayout(null);
		
		userMailLbl = new JLabel("E-mail:");
		userMailLbl.setBounds(462, 300, 74, 16);
		userMail = new JTextField();
		userMail.setBounds(542,297, 242, 22);
		userPassLbl = new JLabel("Password:");
		userPassLbl.setBounds(462, 346, 63, 16);
		userPass = new JPasswordField();
		userPass.addKeyListener(new KeyAdapter() {
			@Override
			// try to log in user when Enter is pressed in password-field
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
		        if (key == KeyEvent.VK_ENTER) {
		        	loginBtnAuthUser();
		        }
			}
		});
		userPass.setBounds(542, 340, 242, 22);

		add(userMailLbl);
		add(userMail);
		add(userPassLbl);
		add(userPass);
		
		// Welcome message
		lblWelcomeCbsCalendar = new JLabel("<html><div style='text-align:center;'>Welcome to CBS Calendar<br>Please log in below</div></html>");
		lblWelcomeCbsCalendar.setBounds(557, 194, 210, 59);
		lblWelcomeCbsCalendar.setFont(new Font("Helvetica", Font.BOLD, 16));
		add(lblWelcomeCbsCalendar);
		
		// Error message when login failed
		lblInvalidUserPass = new JLabel("<html>Invalid username or password <br/>Please try again</html>");
		lblInvalidUserPass.setForeground(Color.red);
		lblInvalidUserPass.setBounds(552, 502, 232, 32);
		lblInvalidUserPass.setVisible(false);
		add(lblInvalidUserPass);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(6, 6, 197, 240);
		add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon(LoginPanel.class.getResource("/CBSLogotrans.png")));
		
		
		lblLogIn_1 = new JLabel("");
		lblLogIn_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loginBtnAuthUser();
			}
		});
		lblLogIn_1.setIcon(new ImageIcon(LoginPanel.class.getResource("/unlocked46.png")));
		lblLogIn_1.setBounds(598, 374, 117, 105);
		add(lblLogIn_1);
	}
	
	// Login button actions
	public void loginBtnAuthUser() {
		User user = new User();
		user.usermail = userMail.getText();
		user.password = userPass.getText();
		try {
			if(user.isValid()){
				MainContainer.switchPanel(mainCon.CALENDAR);
				ApplicationState.currentUser = user;
				lblInvalidUserPass.setVisible(false);
				userMail.setText(""); //reset username-field
				userPass.setText(""); //reset password-field
			} else {
				// login failed
				lblInvalidUserPass.setVisible(true);
			};
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}