package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import virtualPlanner.io.LoginException;
import virtualPlanner.reference.Fonts;

/**
 * This class implments both a Login and a SignUp Window for the VirtualPlanner
 * @author KevinGao
 */
public class LoginWindow implements ActionListener, FocusListener, KeyListener {

	/**The size of the JFrame*/
	private static final Dimension FRAME_SIZE = new Dimension(450, 500);
	/**Default Sizes of Fields and Buttons*/
	private static final Dimension TITLE_SIZE = new Dimension(375, 35);
	/**Default Sizes of Fields and Buttons*/
	private static final Dimension DEFAULT_SIZE = new Dimension(300, 35);
	
	//The following three variables are used in the manual implementation of a JTextField suggested text
	/**Default Hint/Suggested text for the username JTextField*/
	private static final String USERNAME_FIELD_DEFAULT_TEXT = "Username";
	/**Default Hint/Suggested text for the password JTextField*/
	private static final String PASSWORD_FIELD_DEFAULT_TEXT = "Password";
	/**Default Hint/Suggested text for the realname JTextField*/
	private static final String NAME_FIELD_DEFAULT_TEXT = "Name";

	/**This class' JFrame*/
	private JFrame frame;

	/**Title label shown at the top of the window*/
	private static JLabel titleLabel = new JLabel("Virtual Planner Login");

	/**Info label to directly contact/inform the user*/
	private JLabel infoLabel;

	/**JTextField for username*/
	private JTextField usernameField;

	/**JPasswordField for password*/
	private JPasswordField passwordField;

	/**JTextField for user's real name - used for personalization*/
	private JTextField nameField;

	/**JButton to submit user input*/
	private JButton loginButton;

	/**JButton for new Account Window*/
	private JButton createAccountButton;
	
	/**Corresponding GUIController*/
	private GUIController controller;

	/**Boolean used in FocusListener: ignoring the default firstFocus*/
	private boolean firstFocus;

	/**Boolean used in toggling between Log-in and Create Account features*/
	private boolean showingLoginFeatures;

	/**
	 * Constructor which initializes a Login Window
	 */
	protected LoginWindow(GUIController controller) {
		//Set controller
		this.controller = controller;

		//Frame settings
		frame = new JFrame("Virtual Planner");
		frame.setSize(FRAME_SIZE);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponents();
		frame.setVisible(true);
	}
	
	/**
	 * Add all of the Java Swing components to this LoginWindow's JFrame
	 */
	private void addComponents() {
		//Title JLabel
		titleLabel.setFont(Fonts.LOGIN_TITLE);
		titleLabel.setPreferredSize(TITLE_SIZE);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		//Nested Panels are the best way to ensure that the JFrame and other JPanels respect the inner components' preferred settings
		JPanel panelTitleLabel = new JPanel();
		panelTitleLabel.add(titleLabel);

		infoLabel = new JLabel("Please Enter User Credentials:");
		infoLabel.setPreferredSize(DEFAULT_SIZE);
		infoLabel.setFont(Fonts.LOGIN_INFO);
		infoLabel.setForeground(Color.BLACK);
		//Nested JPanel
		JPanel panelInfoLabel = new JPanel();
		panelInfoLabel.add(infoLabel);

		//Username JTextField
		usernameField = new JTextField(USERNAME_FIELD_DEFAULT_TEXT);
		usernameField.setPreferredSize(DEFAULT_SIZE);
		usernameField.setFont(Fonts.LOGIN_DEFAULT);
		//Set Color to Gray for suggested text
		usernameField.setForeground(Color.GRAY);
		usernameField.addFocusListener(this);
		usernameField.addKeyListener(this);
		JPanel panelUsernameField = new JPanel();
		panelUsernameField.add(usernameField);

		//Password JTextField
		passwordField = new JPasswordField(PASSWORD_FIELD_DEFAULT_TEXT);
		passwordField.setPreferredSize(DEFAULT_SIZE);
		passwordField.setFont(Fonts.LOGIN_DEFAULT);
		//Set Color to Gray for suggested text
		passwordField.setForeground(Color.GRAY);
		passwordField.addFocusListener(this);
		//Set passwordField to SHOW contents, since it is initialized to the suggested text
		passwordField.setEchoChar((char) 0);
		JPanel panelPasswordField = new JPanel();
		panelPasswordField.add(passwordField);

		//Real Name JTextField
		nameField = new JTextField(NAME_FIELD_DEFAULT_TEXT);
		nameField.setPreferredSize(DEFAULT_SIZE);
		nameField.setFont(Fonts.LOGIN_DEFAULT);
		//Set Color to Gray for suggested text
		nameField.setForeground(Color.GRAY);
		nameField.addFocusListener(this);
		nameField.addKeyListener(this);
		//Only used in signing up and personalization: not actually a credential used in logging in
		nameField.setVisible(false);
		JPanel panelNameField = new JPanel();
		panelNameField.add(nameField);

		//Login JButton
		loginButton = new JButton("Log in");
		loginButton.setPreferredSize(DEFAULT_SIZE);
		loginButton.setBackground(Color.CYAN);
		loginButton.setForeground(Color.WHITE);
		loginButton.setFont(Fonts.LOGIN_BUTTON);
		loginButton.setFocusPainted(false);
		loginButton.addActionListener(this);
		loginButton.setBorderPainted(false);
		loginButton.setOpaque(true);
		//This JFrame's default button >> Enter automatically calls actionPerformed with loginButton as the source
		frame.getRootPane().setDefaultButton(loginButton);
		JPanel panelLoginButton = new JPanel();
		panelLoginButton.add(loginButton);

		//New Account JButton
		createAccountButton = new JButton("Create an Account");
		createAccountButton.setPreferredSize(DEFAULT_SIZE);
		createAccountButton.setBackground(Color.BLUE);
		createAccountButton.setForeground(Color.WHITE);
		createAccountButton.setFont(Fonts.LOGIN_BUTTON);
		createAccountButton.setFocusPainted(false);
		createAccountButton.addActionListener(this);
		createAccountButton.setBorderPainted(false);
		createAccountButton.setOpaque(true);
		JPanel panelCreateAccountButton = new JPanel();
		panelCreateAccountButton.add(createAccountButton);

		//Final Organizing Box
		Box mainVertical = Box.createVerticalBox();
		mainVertical.add(Box.createVerticalStrut(5));
		mainVertical.add(panelTitleLabel);
		mainVertical.add(Box.createVerticalStrut(20));
		mainVertical.add(panelInfoLabel);
		mainVertical.add(panelUsernameField);
		mainVertical.add(panelPasswordField);
		mainVertical.add(panelNameField);
		mainVertical.add(Box.createVerticalStrut(5));
		mainVertical.add(panelLoginButton);
		mainVertical.add(panelCreateAccountButton);

		//Final Settings
		firstFocus = true;
		showingLoginFeatures = true;
		JPanel mainPanel = new JPanel();
		mainPanel.add(mainVertical);
		frame.add(mainPanel);
	}

	/**
	 * This method handles login attempts for the front-end
	 */
	private void login() {

		//Obtain current text in password
		String password = new String(passwordField.getPassword());

		//Attempt to login through the GUIController
		int loginResult = controller.login(usernameField.getText(), password); 

		switch (loginResult) {
		case 0:
			//Dispose of LoginWindow
			frame.dispose();
			break;
		case LoginException.USER_NOT_REGISTERED:
			//Inform user of incorrect credentials
			infoLabel.setForeground(Color.RED);
			infoLabel.setText("Invalid Username");
			break;
		case LoginException.USERNAME_ALREADY_EXISTS:
			//Inform user of incorrect credentials
			infoLabel.setForeground(Color.RED);
			infoLabel.setText("Invalid Password");
			break;
		}
	}

	/**
	 * This method handles the processing and creation of new accounts in the front-end
	 */
	private void createAccount() {
		
		//Obtain current text in passowrd
		String password = new String(passwordField.getPassword());

		//Attempt to signup through the GUIController
		int loginResult = controller.signUp(usernameField.getText(), password, nameField.getText()); //New Account/User(usernameField.getText, password);

		//Successful signup
		switch (loginResult) {
		case 0:
			login();
			break;
		case LoginException.USERNAME_ALREADY_EXISTS:
			//Inform user of invalid input
			infoLabel.setForeground(Color.RED);
			infoLabel.setText("Username already exists");
		}
	}

	/**
	 * This method toggles between the Log-in and Create New Account interfaces
	 * NOTE: Changes components within the JFrame
	 */
	private void toggleCreateAccountFeatures() {

		//From showingLoginFeatures to showingCreateAccountFeatures
		if(showingLoginFeatures) {
			//Update JLabel text, Button text, Button colors as visual cues to inform/remind user they are creating a new account
			infoLabel.setText("Create Account:");
			infoLabel.setForeground(Color.BLACK);
			loginButton.setText("Finish and Log in");
			loginButton.setBackground(Color.GREEN);
			createAccountButton.setText("Back");
			createAccountButton.setBackground(Color.RED);
			//Show the real name field
			nameField.setVisible(true);
			titleLabel.setText("Virtual Planner Signup");
			showingLoginFeatures = false;
		}
		//From showingCreateAccountFeatures to showingLoginFeatures
		else {
			//Revert the visual cues to inform/remind user they are in the login mode
			infoLabel.setText("Please Enter User Credentials:");
			infoLabel.setForeground(Color.BLACK);
			loginButton.setText("Log in");
			loginButton.setBackground(Color.CYAN);
			createAccountButton.setText("Create an Account");
			createAccountButton.setBackground(Color.BLUE);
			//Make the real name field invisible and take no space
			nameField.setVisible(false);
			titleLabel.setText("Virtual Planner Login");
			showingLoginFeatures = true;
		}
		
		frame.invalidate();
		frame.revalidate();
	}

	/**
	 * This method handles ActionEvents for the LoginWindow
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		//Login Button Clicked - call Corresponding method

		if (src.equals(loginButton)) {
			//Login Mode
			if (showingLoginFeatures) {
				login();
			}
			//Signup mode
			else {
				createAccount();
			}
		}

		//Toggles between the Login and Signup features
		else if (src.equals(createAccountButton)) {
			toggleCreateAccountFeatures();
		}

	}

	/**
	 * This method handles focusGained FocusEvents for the Login Window
	 * Used for Manual Implementation of the JTextField Hint/Suggested text
	 */
	public void focusGained(FocusEvent e) {

		//The usernameField is set to the default focus on startup: 
		//Don't trigger any Events on simply on startup
		if (firstFocus) {
			return;
		}

		//Obtain Source
		Object src = e.getSource();

		//usernameField gains focus
		if (src.equals(usernameField)) {
			//User Clicks into the field with no user keyboard input
			if (usernameField.getText().equals(USERNAME_FIELD_DEFAULT_TEXT)) {
				//Remove hint and change color of text
				usernameField.setText("");
				usernameField.setForeground(Color.BLACK);
			}
		}

		//passwordField gains focus
		else if (src.equals(passwordField)) {
			//Obtain current text in passowrd
			String password = new String(passwordField.getPassword());

			//User Clicks into the field with no user keyboard input
			if (password.equals(PASSWORD_FIELD_DEFAULT_TEXT)) {
				//Remove hint and change color of text
				passwordField.setText("");
				passwordField.setForeground(Color.BLACK);
				//Set the passwordField to hide characters
				passwordField.setEchoChar((char)8226);
			}
		}

		//nameField gains focus
		else if (src.equals(nameField)) {
			//User Clicks into the field with no user keyboard input
			if (nameField.getText().equals(NAME_FIELD_DEFAULT_TEXT)) {
				//Remove hint and change color of text
				nameField.setText("");
				nameField.setForeground(Color.BLACK);
			}
		}
	}

	/**
	 * This method handles focusLost FocusEvents for the Login Window
	 * Used for Manual Implementation of the JTextField Hint/Suggested text
	 */
	public void focusLost(FocusEvent e) {

		//Obtain source
		Object src = e.getSource();

		//usernameField loses focus
		if (src.equals(usernameField)) {

			//usernameField has lost first focus: handle events as usual now
			firstFocus = false;

			//If the field is blank
			if(usernameField.getText().equals("")) {
				//Reinstate the "Hint", reset the color
				usernameField.setText(USERNAME_FIELD_DEFAULT_TEXT);
				usernameField.setForeground(Color.GRAY);
			}
		}

		//passwordField loses focus
		else if (src.equals(passwordField)) {
			//Obtain current text in passowrd
			String password = new String(passwordField.getPassword());

			//If the field is blacnk
			if (password.equals("")) {
				//Reinstate the "Hint, reset the color
				passwordField.setText(PASSWORD_FIELD_DEFAULT_TEXT);
				passwordField.setForeground(Color.GRAY);
				//Set the passwordField to show characters
				passwordField.setEchoChar((char)0);
			}
		}

		//nameField loses focus
		else if (src.equals(nameField)) {

			//usernameField has lost first focus: handle events as usual now
			firstFocus = false;

			//If the field is blank
			if(nameField.getText().equals("")) {
				//Reinstate the "Hint", reset the color
				nameField.setText(NAME_FIELD_DEFAULT_TEXT);
				nameField.setForeground(Color.GRAY);
			}
		}
	}

	/**
	 * This method handles keyPressed KeyEvents for the Login Window
	 * Used for Manual Implementation of the JTextField Hint/Suggested text
	 */
	public void keyPressed(KeyEvent e) {

		//Normally the "Hint" disappears as soon a field gains focus
		//However, on startup, in order for the "Hint" to show up on the first item which gains default focus,
		//A special KeyEvent handler is used to delete the "Hint" on KeyPressed ONLY when the user starts typing.
		//This works even if the user enters the password first
		if(firstFocus && usernameField.hasFocus()) {
			firstFocus = false;
			usernameField.setText("");
			usernameField.setForeground(Color.BLACK);
		}
	}

	/**
	 * This method handles keyReleased KeyEvents for the Login Window
	 * Unused method required by KeyListener
	 */
	public void keyReleased(KeyEvent e) {}

	/**
	 * This method handles keyTyped KeyEvents for the Login Window
	 * Unused method required by KeyListener
	 */
	public void keyTyped(KeyEvent e) {}
}