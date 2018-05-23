package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import virtualPlanner.backend.Assignment;
import virtualPlanner.reference.AssignmentTypes;
import virtualPlanner.util.Date;
import virtualPlanner.reference.Preferences;
import virtualPlanner.util.Block;

/**
 * This class is used in place of JButton to provide extra functionality to the JButtons in the Calendar
 * @author Kevin
 *
 */
@SuppressWarnings("serial")
public class GUIButton extends JButton implements ActionListener {

	private static final Border defaultBorder = BorderFactory.createEtchedBorder(1);
	private static final Border highlightedBorder = BorderFactory.createEtchedBorder(1, Color.RED, Color.WHITE);

	private static final Dimension showAssignmentsSize = new Dimension(400, 650);
	private static final Dimension assignmentListSize = new Dimension(275, 200);
	private static final Dimension inputFieldSize = new Dimension(250, 35);


	private static final Font titleFont = new Font("Dialog", Font.BOLD, 26);
	private static final Font assignmentFont = new Font("Dialog", Font.BOLD, 20);
	private static final Font buttonFont = new Font("SansSerif", Font.BOLD, 20);
	private static final Font newAssignmentFont = new Font("Dialog", Font.BOLD, 18);



	private static GUIButton highlightedButton;


	private ArrayList<Assignment> assignments = new ArrayList<Assignment>();
	private Block block;
	private boolean hasOptionsWindow;
	private String name;
	private Color color;
	private boolean isDayLabel;

	//Variables for adding new assignments
	private JTextField nameField, descField;
	private JComboBox<String> monthBox, dayBox, yearBox, typeBox;
	private JButton submitButton, completeButton;

	/**
	 * Base constructor with call to super 
	 * Sets the universal settings for the buttons
	 */
	public GUIButton(String name) {
		super();
		hasOptionsWindow = false;
		this.addActionListener(this);
		this.name = name + "\n";
		this.setMultiLineText("");
		this.setOpaque(true);
		this.setFocusable(false);
		this.setBackground(color);
		this.setBorder(defaultBorder);
		highlightedButton = null;
		//		this.setBorder(BorderFactory.createEtchedBorder(1, Color.RED, Color.RED));
	}

	/**
	 * Constructor for the JButtons in the Calendar that represent Blocks
	 * @param block
	 * @param courseID
	 * @param assignments
	 * @param size
	 */
	public GUIButton(String name, Block block, int courseID, ArrayList<Assignment> assignments, Dimension size, Font font) {
		this(name);
		this.block = block;
		this.color = Preferences.getColor(courseID);
		this.setBackground(color);
		this.assignments = assignments;
		this.setPreferredSize(size);
		this.setVerticalAlignment(SwingConstants.TOP);
		this.setFont(font);
		this.isDayLabel = false;
	}
	
	public GUIButton(String name, Dimension size, Font font) {
		this(name);
		this.setPreferredSize(size);
		this.setFont(font);
		this.isDayLabel = true;
	}

	/**
	 * Enables multi-line text
	 * PRECONDITION: New lines are denotated by a "\n"
	 * @param text
	 */
	public void setMultiLineText(String text) {
		String newText = name + text;
		this.setText("<html><center>" + newText.replaceAll("\\n", "<br>") + "</center></html>");
		this.setAlignmentX(CENTER_ALIGNMENT);
	}

	/**
	 * Creates a window of options for this GUIButton
	 */
	
	private void showAssignments() {
		if (hasOptionsWindow)
			return;

		hasOptionsWindow = true;

		JFrame optionsWindow = new JFrame(name);
		optionsWindow.setSize(showAssignmentsSize);
		optionsWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		optionsWindow.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				hasOptionsWindow = false;
				optionsWindow.dispose();
			}
		});



		//JLabel "Current Assignments"
		JLabel labelCurAssignments = new JLabel("Current Assignments:");
		labelCurAssignments.setFont(titleFont);
		JPanel panelLabelCurAssignments = new JPanel();
		panelLabelCurAssignments.add(labelCurAssignments);


		//Assignment JList and JScrollPane
		//TODO: Loop through assignments
		//TODO: Descriptions on new line
		String[] assignmentList = {"History Reading", "Computer Science Project"};
		;
		if (assignments != null){
			int numAssignments = assignments.size();

			assignmentList = new String[numAssignments];
			for (int i = 0; i < numAssignments; i ++)
				assignmentList[i] = assignments.get(i).getName();
		}

		JList<String> assignmentJList = new JList<String>(assignmentList);
		assignmentJList.setFont(assignmentFont);
		JScrollPane assignmentScrollPane = new JScrollPane(assignmentJList);
		assignmentScrollPane.setPreferredSize(assignmentListSize);

		JPanel panelAssignmentList = new JPanel();
		panelAssignmentList.add(assignmentScrollPane);



		completeButton = new JButton("Complete Assignment");
		completeButton.setFont(buttonFont);
		completeButton.setBackground(Color.green);
		completeButton.addActionListener(this);
		//TODO: Completed Tasks?
		//completeButton.setPreferredSize();
		//completeButton.setFocusable(false);
		JPanel panelCompleteButton = new JPanel();
		panelCompleteButton.add(completeButton);

		//New Assignment: Name
		JLabel labelName = new JLabel("Assignment Name:");
		labelName.setFont(newAssignmentFont);
		JPanel panelLabelName = new JPanel();
		panelLabelName.add(labelName);

		//TODO: This starts with focus
		nameField = new JTextField();
		nameField.setPreferredSize(inputFieldSize);
		nameField.setFont(newAssignmentFont);
		JPanel panelNameField = new JPanel();
		panelNameField.add(nameField);

		//Adding Assignment: Description
		JLabel labelDesc = new JLabel("Assignment Description (Optional):");
		labelDesc.setFont(newAssignmentFont);
		JPanel panelLabelDesc = new JPanel();
		panelLabelDesc.add(labelDesc);
		
		descField = new JTextField();
		descField.setPreferredSize(inputFieldSize);
		descField.setFont(newAssignmentFont);
		JPanel panelDescField = new JPanel();
		panelDescField.add(descField);

		//JComboBox for TYPE of Assignment
		String[] assignmentTypes = new String[AssignmentTypes.values().length];
		for(int i = 0; i < assignmentTypes.length; i++)
			assignmentTypes[i] = AssignmentTypes.values()[i].getName();
		typeBox = new JComboBox<String>(assignmentTypes);
		typeBox.setBackground(Color.WHITE);
		typeBox.setFont(newAssignmentFont);
		JPanel panelTypeBox = new JPanel();
		panelTypeBox.add(typeBox);

		//Adding Assignment: Date Due
		JLabel labelDateDue = new JLabel("Date Due:");
		labelDateDue.setFont(newAssignmentFont);
		JPanel panelLabelDateDue = new JPanel();
		panelLabelDateDue.add(labelDateDue);
		
		//TODO: Set to Current Date, functionality even needed?
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		monthBox = new JComboBox<String>(months);
		monthBox.setBackground(Color.WHITE);
		
		String[] days = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		dayBox = new JComboBox<String>(days);
		dayBox.setBackground(Color.WHITE);
		
		String[] years = {"2018", "2019", "2020"};
		yearBox = new JComboBox<String>(years);
		yearBox.setBackground(Color.WHITE);
		
		Box datePicker = Box.createHorizontalBox();
		datePicker.add(monthBox);
		datePicker.add(dayBox);
		datePicker.add(yearBox);
		
		JPanel panelDatePicker = new JPanel();
		panelDatePicker.add(datePicker);
		
		//Submit Button
		submitButton = new JButton("Submit");
		submitButton.setFont(buttonFont);
		submitButton.setBackground(Color.green);
		submitButton.addActionListener(this);
		JPanel panelSubmitButton = new JPanel();
		panelSubmitButton.add(submitButton);
		
		//Main Horiztonal Box for all of the components
		Box mainVertical = Box.createVerticalBox();
		mainVertical.add(panelLabelCurAssignments);
		mainVertical.add(panelAssignmentList);
		mainVertical.add(panelCompleteButton);
		mainVertical.add(panelLabelName);
		mainVertical.add(panelNameField);
		mainVertical.add(panelLabelDesc);
		mainVertical.add(panelDescField);
		mainVertical.add(panelTypeBox);
		mainVertical.add(panelLabelDateDue);
		mainVertical.add(panelDatePicker);
		mainVertical.add(panelSubmitButton);

		optionsWindow.add(mainVertical);
		optionsWindow.setVisible(true);

	}
	//TODO: KeyListener
	/**
	 * Selects a particular button.
	 * 
	 * @param button The button to select.
	 */
	public static void select(GUIButton button) {
		highlightedButton = button;
		button.setBorder(highlightedBorder);
	}
	
	/**
	 * Deslects the button that is currently selected.
	 */
	public static void deselect() {
		if(highlightedButton != null) {
			highlightedButton.setBorder(defaultBorder);
			highlightedButton = null;
		}
	}
	
	/**
	 * ActionListener for each individual JButtno
	 */
	public void actionPerformed(ActionEvent e)  {
		Object src = e.getSource();
		if (src instanceof GUIButton) {	
			GUIButton button = (GUIButton)src;

			//Button is a label for the day
			if(button.isDayLabel) {
				
			}

			//Button is a block
			else {
				deselect();
				if(block.getBlock().isClass()) {
					select(button);
					
					//Option Window
					showAssignments();
				}
			}
		}
	}
}