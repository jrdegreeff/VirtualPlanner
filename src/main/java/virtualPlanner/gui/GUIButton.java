package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

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
import javax.swing.plaf.synth.SynthSeparatorUI;

import virtualPlanner.backend.Assignment;
import virtualPlanner.backend.Course;
import virtualPlanner.reference.AssignmentTypes;
import virtualPlanner.reference.Days;
import virtualPlanner.reference.Fonts;
import virtualPlanner.util.Date;
import virtualPlanner.reference.Preferences;
import virtualPlanner.util.Block;

/**
 * This class is used in place of JButton to provide extra functionality to the JButtons in the Calendar
 * @author KevinGao
 *
 */
@SuppressWarnings("serial")
public class GUIButton extends JButton implements ActionListener {

	/**The GUIController for this GUIButton*/
	private GUIController controller;

	/**Default Border which all buttons are instantiated with*/
	private static final Border DEFAULT_BORDER = BorderFactory.createEtchedBorder(1);
	/**Highlight Border used to indicate the last edited GUIButton which represents a block*/
	private static final Border HIGHLIGHTED_BORDER = BorderFactory.createEtchedBorder(1, Color.RED, Color.WHITE);

	/**Size of the Add Assignment Window*/
	private static final Dimension ASSIGNMENT_WINDOW_SIZE = new Dimension(400, 775);
	/**Size of the Current Assignments JList*/
	private static final Dimension ASSIGNMENT_LIST_SIZE = new Dimension(275, 200);
	/**Size of the Input Fields within the Add Assignment Window*/
	private static final Dimension INPUT_FIELD_SIZE = new Dimension(250, 35);

	/**Date Object which represents the current date in real life*/
	private static Date currentDate = MainCalendarWindow.getCurrentDate();

	/**Reference to the last highlighted button so that it can be easily reset to normal*/
	private static GUIButton highlightedButton;

	/**String array which represents all the months in a year*/
	private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	/**String array which represents all the days in a month*/
	private static final String[] DAYS = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	/**String array which represents years*/
	private static final String[] YEARS = {"2018", "2019", "2020", "2021"};
	/**AssignmentTypes in the JComboBox drop-down used in selecting the type of Assignment*/
	private static final AssignmentTypes[] TYPES = {AssignmentTypes.HOMEWORK, AssignmentTypes.TEST, AssignmentTypes.QUIZ, AssignmentTypes.ESSAY, AssignmentTypes.PROJECT};

	/**Reference to Parent Window*/
	private MainCalendarWindow mainWindow;

	/**ArrayList of all uncompleted assignments within this GUIButton*/
	private ArrayList<Assignment> assignments;
	/**ArrayList of all completed assignments within this GUIButton*/
	private ArrayList<Assignment> completedAssignments;

	/**The block that corresponds to this GUIButton*/
	private Block block;
	/**The course that corresponds to this GUIButton*/
	private Course course;
	/**The name and text of this GUIButton*/
	private String name;
	/**Variable used to track the color of this GUIButton*/
	private Color color;
	/**The Calendar Date on which this GUIButton exists*/
	private Date date;

	/**Boolean to prevent multiple instatiations of the OptionsWindow*/
	private boolean hasAssignmentsWindow;

	/**
	 * If true, this GUIButton represents a label for the day of week
	 * If false, this GUIButton represnets a block on the schedule
	 */
	private boolean isDayLabel;

	//The following variables must have references here in order to be able to be accessed in actionPerformed.

	/**The JTextField in the Add Assignment Window which holds the name of the assignment*/
	private JTextField nameField; 
	/**The JTextField in the Add Assignment Window which holds the description of the assignment*/
	private JTextField descField;

	/**JComboBox which represents the Month that the assignment was assigned*/
	private JComboBox<String> assignedMonthBox;
	/**JComboBox which represents the Day that the assignment was assigned*/
	private JComboBox<String> assignedDayBox;
	/**JComboBox which represents the Year that the assignment was assigned*/
	private JComboBox<String> assignedYearBox;
	/**JComboBox which represents the Month that the assignment is due*/
	private JComboBox<String> dueMonthBox;
	/**JComboBox which represents the Day that the assignment is due*/
	private JComboBox<String> dueDayBox;
	/**JComboBox which represents the Year that the assignment is due*/
	private JComboBox<String> dueYearBox;
	/**JComboBox which represents the AssignmentType of the assignment*/
	private JComboBox<String> typeBox;

	/**Button clicked by user to 'complete' an existing assignment*/
	private JButton completeButton;

	/**Button clicked by user to 'submit' a new assignment*/
	private JButton submitButton;

	/**JList of all current, uncompleted assignments*/
	private JList<String> assignmentJList = new JList<String>();
	/**JScrollPane to provide scrollable features to assignmentJList*/
	private JScrollPane assignmentScrollPane = new JScrollPane();

	/**Reference to the JLabel in the Add Assignment Window which informs the user what the window is for*/
	private JLabel labelNewAssignment;

	/**
	 * If true, the user is currently editing an existing assignment
	 * If false, the user is currently adding a new assignment
	 */
	private Boolean isEditMode;

	/**Reference of assignment that the user wants to update*/
	private Assignment assignmentToEdit;

	/**
	 * Base constructor with call to super 
	 * Sets the universal defaults for all GUIButtons
	 */
	public GUIButton(String name) {
		super();
		hasAssignmentsWindow = false;
		this.addActionListener(this);
		this.name = name;
		this.setOpaque(true);
		this.setFocusPainted(false);
		this.setBackground(color);
		this.setBorder(DEFAULT_BORDER);
		this.setAlignmentX(CENTER_ALIGNMENT);
		isEditMode = false;
	}

	/**
	 * Constructor for the GUIButtons in the Calendar that represent Blocks
	 * @param block the block that corresponds to the GUIButton
	 * @param courseID the courseID of the course 
	 * @param assignments an ArrayList of type Assignment which holds all existing Assignments for this GUIButton
	 * @param size the preferred size of this GUIButton
	 */
	public GUIButton(String name, Block block, int courseID, ArrayList<Assignment> assignments, Dimension size, Font font, GUIController controller, Course course, MainCalendarWindow mainWindow, Date date) {
		this(name);
		this.block = block;
		this.color = Preferences.getColor(courseID);
		this.setBackground(color);
		this.assignments = assignments;
		this.setPreferredSize(size);
		this.setVerticalAlignment(SwingConstants.TOP);
		this.setFont(font);
		this.isDayLabel = false;
		this.setMultiLineText("");
		this.controller = controller;
		this.course = course;
		this.mainWindow = mainWindow;
		this.date = date;
		updateButton();
	}

	/**
	 * Construction for the GUIButtons in the Calendar that are the day of week labels
	 * @param name
	 * @param size
	 * @param font
	 */
	public GUIButton(String name, Dimension size, Font font) {
		this(name);
		this.setPreferredSize(size);
		this.setFont(font);
		this.isDayLabel = true;
		this.setText(name);
	}

	/**
	 * Enables multi-line text
	 * PRECONDITION: New lines are denotated by a "\n"
	 * @param text the text which this GUIButton will be set to display
	 */
	public void setMultiLineText(String text) {
		String newText = name + text;
		this.setText("<html><center>" + newText.replaceAll("\\n", "<br>") + "</center></html>");
	}

	/**
	 * Enables the edit assignment features within the Add Assignment Window for a particular existing Assignment
	 */
	private void showEditAssignmentMode(int index) {

		//Quit if
		//1) Already in Edit Mode
		//2) No assignments to edit
		//3) The given index to edit is invalid
		if (isEditMode || assignments == null || assignments.size()-1 < index)
			return;
		isEditMode = true;

		//Obtain Assignment to edit
		Assignment a = assignments.get(index);

		//Fill in the editable fields with the Assignment's EXISTING properties
		nameField.setText(a.getName());
		descField.setText(a.getDescrip());
		Date assignedDate = a.getAssignedDate();
		assignedMonthBox.setSelectedItem(MONTHS[assignedDate.getMonth()]);
		assignedDayBox.setSelectedItem(DAYS[assignedDate.getDay()-1]);
		assignedYearBox.setSelectedItem(YEARS[assignedDate.getYear()-2018]);
		Date dueDate = a.getDue();
		dueMonthBox.setSelectedItem(MONTHS[dueDate.getMonth()]);
		dueDayBox.setSelectedItem(DAYS[dueDate.getDay()-1]);
		dueYearBox.setSelectedItem(YEARS[dueDate.getYear()-2018]);

		//Make visual change to notify/remind user they have entered Edit Mode
		labelNewAssignment.setText("Edit Assignment");
		labelNewAssignment.setBackground(Color.YELLOW);
		submitButton.setText("Finish");
		submitButton.setBackground(Color.YELLOW);

		//Set reference to the editable Assignment
		assignmentToEdit = a;
	}

	/**
	 * Disables the Edit assignment features, returns to the New Assignment mode
	 */
	private void hideEditAssignmentMode() {
		//Quit if
		//1) Already in New Assignment mode
		if (!isEditMode)
			return;
		isEditMode = false;

		//Revert the visual change in showEditAssignmentMode() to notify/remind user they have returned to New Assignment Mode
		labelNewAssignment.setText("New Assignment");
		labelNewAssignment.setBackground(Color.GREEN);
		submitButton.setText("Submit");
		submitButton.setBackground(Color.GREEN);
	}

	/**
	 * Creates a New Assignment Window where a user can view current assignments, add new assignments, and edit/remove existing assignments
	 * Defaults to Add Assignment mode
	 */
	private void showAssignments() {
		//Quit if
		//1) A New Assignment Window already exists
		if (hasAssignmentsWindow)
			return;
		hasAssignmentsWindow = true;

		//Make new JFrame for the New Assignment Window
		JFrame assignmentsWindow = new JFrame(name);
		assignmentsWindow.setResizable(false);
		assignmentsWindow.setSize(ASSIGNMENT_WINDOW_SIZE);
		assignmentsWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		//Override the Close Operation
		assignmentsWindow.addWindowListener(new java.awt.event.WindowAdapter() {
			//This method is called when the user closes the New Assignment Window
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				//Result boolean which prevents multiple instantiations
				hasAssignmentsWindow = false;
//				//Updates the text on the Button, because the user may have edited some assignments
//				updateButton();
				//Updates the "Upcoming Events" JList on the right of the MainCalendarWindow
				mainWindow.update();
				//Dispose the JFrame
				assignmentsWindow.dispose();
			}
		});

		//JLabel "Current Assignments"
		JLabel labelCurAssignments = new JLabel("Current Assignments:");
		labelCurAssignments.setFont(Fonts.BUTTON_TITLE);
		labelCurAssignments.setOpaque(true);
		//Nested Panels are the best way to ensure that the JFrame and other JPanels respect the inner components' preferred settings
		JPanel panelLabelCurAssignments = new JPanel();
		panelLabelCurAssignments.add(labelCurAssignments);

		//Update and show the JList of current Assignments
		updateAssignmentList();

		assignmentJList.setFont(Fonts.BUTTON_ASSIGNMENT);


		//MouseListener which enables editing of items in the JList
		assignmentJList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//If mouse1 is double-clicked within the assignmentJlist
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					//Retrieve the index at which the user double-clicked
					int index = assignmentJList.locationToIndex(e.getPoint());
					//Transition to Edit Assignment Mode
					showEditAssignmentMode(index);
				} 
			}
		});

		//JScrollPane to enable scrollable features of the Current Assignments JList
		assignmentScrollPane = new JScrollPane(assignmentJList);
		assignmentScrollPane.setPreferredSize(ASSIGNMENT_LIST_SIZE);

		//Nested Panels are the best way to ensure that the JFrame and other JPanels respect the inner components' preferred settings
		JPanel panelAssignmentList = new JPanel();
		panelAssignmentList.add(assignmentScrollPane);

		//Initialization of the Button the user clicks to mark an assignment as complete
		completeButton = new JButton("Complete Assignment");
		completeButton.setFont(Fonts.BUTTON_BUTTON);
		completeButton.setBackground(Color.green);
		completeButton.addActionListener(this);
		completeButton.setFocusPainted(false);
		completeButton.setOpaque(true);
		completeButton.setBorderPainted(false);

		//Nested Panel 
		JPanel panelCompleteButton = new JPanel();
		panelCompleteButton.add(completeButton);

		//JLabel which notifies the user of the current mode
		//Defaults to New Assignment mode
		labelNewAssignment = new JLabel("New Assignment:");
		labelNewAssignment.setFont(Fonts.BUTTON_TITLE);
		labelNewAssignment.setOpaque(true);
		JPanel panelLabelNewAssignment = new JPanel();
		panelLabelNewAssignment.add(labelNewAssignment);

		//Assignment Name JLabel and JTextField
		JLabel labelName = new JLabel("Assignment Name:");
		labelName.setPreferredSize(INPUT_FIELD_SIZE);
		labelName.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
		JPanel panelLabelName = new JPanel();
		panelLabelName.add(labelName);

		//This JTextField is set as default focus (text cursor defaults here as QOL feature for user) in the actionPerformed method when the Add Assignment Window is Created
		nameField = new JTextField();
		nameField.setPreferredSize(INPUT_FIELD_SIZE);
		nameField.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
		JPanel panelNameField = new JPanel();
		panelNameField.add(nameField);

		//Assignment Description JLabel and JTextField
		JLabel labelDesc = new JLabel("Assignment Description (Optional):");
		labelDesc.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
		JPanel panelLabelDesc = new JPanel();
		panelLabelDesc.add(labelDesc);

		descField = new JTextField();
		descField.setPreferredSize(INPUT_FIELD_SIZE);
		descField.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
		JPanel panelDescField = new JPanel();
		panelDescField.add(descField);

		//AssignmentType JComboBox
		String[] assignmentTypes = new String[AssignmentTypes.values().length];
		for(int i = 0; i < assignmentTypes.length; i++)
			assignmentTypes[i] = AssignmentTypes.values()[i].getName();
		typeBox = new JComboBox<String>(assignmentTypes);
		typeBox.setBackground(Color.WHITE);
		typeBox.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
		JPanel panelTypeBox = new JPanel();
		panelTypeBox.add(typeBox);

		//Date Assigned of Assignment
		JLabel labelDateAssigned = new JLabel("Date Assigned:");
		labelDateAssigned.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
		JPanel panelLabelDateAssigned = new JPanel();
		panelLabelDateAssigned.add(labelDateAssigned);

		//Month
		assignedMonthBox = new JComboBox<String>(MONTHS);
		assignedMonthBox.setBackground(Color.WHITE);

		//Day
		assignedDayBox = new JComboBox<String>(DAYS);
		assignedDayBox.setBackground(Color.WHITE);

		//Year
		assignedYearBox = new JComboBox<String>(YEARS);
		assignedYearBox.setBackground(Color.WHITE);


		//Set default for Assigned Date to today
		assignedMonthBox.setSelectedItem(MONTHS[currentDate.getMonth()]);
		assignedDayBox.setSelectedItem(DAYS[currentDate.getDay()-1]);
		assignedYearBox.setSelectedItem(YEARS[currentDate.getYear()-2018]);

		//Box for organizing purposes
		Box assignedDatePicker = Box.createHorizontalBox();
		assignedDatePicker.add(assignedMonthBox);
		assignedDatePicker.add(assignedDayBox);
		assignedDatePicker.add(assignedYearBox);

		//Nested Panel
		JPanel panelAssignedDatePicker = new JPanel();
		panelAssignedDatePicker.add(assignedDatePicker);

		//Date due of Assignment
		JLabel labelDateDue = new JLabel("Date Due:");
		labelDateDue.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
		JPanel panelLabelDateDue = new JPanel();
		panelLabelDateDue.add(labelDateDue);

		//Month
		dueMonthBox = new JComboBox<String>(MONTHS);
		dueMonthBox.setBackground(Color.WHITE);

		//Day
		dueDayBox = new JComboBox<String>(DAYS);
		dueDayBox.setBackground(Color.WHITE);

		//Year
		dueYearBox = new JComboBox<String>(YEARS);
		dueYearBox.setBackground(Color.WHITE);

		//Set default for due date to today
		dueMonthBox.setSelectedItem(MONTHS[currentDate.getMonth()]);
		dueDayBox.setSelectedItem(DAYS[currentDate.getDay()-1]);
		dueYearBox.setSelectedItem(YEARS[currentDate.getYear()-2018]);

		//Box for organizing purposes
		Box dueDatePicker = Box.createHorizontalBox();
		dueDatePicker.add(dueMonthBox);
		dueDatePicker.add(dueDayBox);
		dueDatePicker.add(dueYearBox);

		//Nested JPanel
		JPanel panelDueDatePicker = new JPanel();
		panelDueDatePicker.add(dueDatePicker);

		//Submit Button for the user to click when finished adding/editing an assignment
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.BUTTON_BUTTON);
		submitButton.setBackground(Color.green);
		submitButton.addActionListener(this);
		submitButton.setOpaque(true);
		submitButton.setBorderPainted(false);
		//Default button of JFrame >> Enter defaults to calling actionPerformed with submitButton as the source
		assignmentsWindow.getRootPane().setDefaultButton(submitButton);
		JPanel panelSubmitButton = new JPanel();
		panelSubmitButton.add(submitButton);

		//Main Horiztonal Box for all of the components
		Box mainVertical = Box.createVerticalBox();
		mainVertical.add(panelLabelCurAssignments);
		mainVertical.add(panelAssignmentList);
		mainVertical.add(panelCompleteButton);
		mainVertical.add(panelLabelNewAssignment);
		mainVertical.add(panelLabelName);
		mainVertical.add(panelNameField);
		mainVertical.add(panelLabelDesc);
		mainVertical.add(panelDescField);
		mainVertical.add(panelTypeBox);
		mainVertical.add(panelLabelDateAssigned);
		mainVertical.add(panelAssignedDatePicker);
		mainVertical.add(panelLabelDateDue);
		mainVertical.add(panelDueDatePicker);
		mainVertical.add(panelSubmitButton);

		//Final Add Assignment Window Settings
		assignmentsWindow.add(mainVertical);
		assignmentsWindow.setVisible(true);
	}

	/**
	 * Updates the JList of current assignments in the New Assignment Window
	 */
	private void updateAssignmentList() {

		//Use GUIController to obtain most updated list of assignments
		assignments = controller.getAssignments(date, block);
		System.out.println(assignments);

		//If null, create empty JList
		if (assignments == null) {
			assignmentJList = new JList<String>();
			return;
		}

		//Else, set the JList to the return ArrayList's contents
		int length = assignments.size();
		String[] curAssignments = new String[length];
		for (int i = 0; i < length; i ++){
			Assignment a = assignments.get(i);
			
			//This type of formatting is used to display the name and the description of the assignment on separate lines 
			//WHILE keeping the two lines as part of ONE INDEX in the JList 
			//Essential to the functionality of the Edit Mode
			//Note: \n does not work, and wrapping text is intentionally avoided, as it creates unpleasant visuals
			curAssignments[i] = a.getName() + "<br>" + a.getDescrip();
			System.out.println(a);
		}

		System.out.println(Arrays.toString(curAssignments));
		//Set the assignmentJList to display the current Assignments
		assignmentJList.setListData(curAssignments);
		assignmentScrollPane.revalidate();
		assignmentScrollPane.repaint();
	}

	/**
	 * Updates the text that shows on this GUIButton
	 */
	private void updateButton() {
		if (assignments == null)
			return;
		String result = "";
		for (Assignment a: assignments)
			result += "\n" + a.getName();
		this.setMultiLineText(result);
	}

	/**
	 * This method adds an assignment to the back-end, middle-end, and front-end through controllers
	 * Called only when the user clicks submitButton
	 */
	private void addAssignment() {
		System.out.println("Attempting to add assignment");
		//Retrieve Dates from JComboBoxes
		Date assigned = new Date(assignedDayBox.getSelectedIndex()+1, assignedMonthBox.getSelectedIndex()+1, Integer.parseInt((String)(assignedYearBox.getSelectedItem())));		
		Date due = new Date(dueDayBox.getSelectedIndex()+1, dueMonthBox.getSelectedIndex()+1, Integer.parseInt((String)(dueYearBox.getSelectedItem())));

		//Retrieve Type of Assignment
		AssignmentTypes type = TYPES[typeBox.getSelectedIndex()];

		//Call to controller to notify middle-end and back-end
		controller.addAssignment(course, assigned, due, type, nameField.getText(), descField.getText());
		
		//Update the JList of current Assignments
		updateAssignmentList();
	}

	/**
	 * This method markes an assignment at a specific index as complete and moves it to the completed list
	 */
	private void completeAssignment(int index) {
		//Quit if
		//1) There are no current assignments
		//2) The index of the assignment to complete is invalid
		if (assignments == null || assignments.size() - 1 < index)
			return;

		//Assignment to remove
		Assignment removedAssignment = assignments.remove(index);
		//Contact middle-end & back-end
		controller.setAssignmentComplete(removedAssignment, true);
		controller.removeAssignment(course, removedAssignment);
		//Add to the completed list
//		completedAssignments.add(removedAssignment);
	}

	/**
	 * This method is the last part of the edit assignment procedure
	 * After the user re-enters all the information, this method edits the original Assignment
	 * Quits the edit assignment mode after the user re-submits
	 */
	private void editAssignment() {
		//Assignment that the user edited
		Assignment a = assignmentToEdit;

		//Retrieve new Dates
		Date assigned = new Date(assignedDayBox.getSelectedIndex()+1, assignedMonthBox.getSelectedIndex()+1, Integer.parseInt((String)(assignedYearBox.getSelectedItem())));		
		Date due = new Date(dueDayBox.getSelectedIndex()+1, dueMonthBox.getSelectedIndex()+1, Integer.parseInt((String)(dueYearBox.getSelectedItem())));

		//Change the Assignment's fields
		a.setName(nameField.getText());
		a.setDescrip(descField.getText());
		a.changeAssignedDate(assigned);
		a.changeDueDate(due);

		//Quit the Edit Assignment Mode
		hideEditAssignmentMode();
	}

	/**
	 * Highlights a particular button.
	 * 
	 * @param button The button to select.
	 */
	public static void select(GUIButton button) {
		highlightedButton = button;
		button.setBorder(HIGHLIGHTED_BORDER);
	}

	/**
	 * De-Highlights the button that is currently highlighted.
	 */
	public static void deselect() {
		if(highlightedButton != null) {
			highlightedButton.setBorder(DEFAULT_BORDER);
			highlightedButton = null;
		}
	}

	/**
	 * actionPerformed method which handles all ActionEvents for GUIButtons
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		//The user clicks the submit button
		if (src.equals(submitButton)) {
			System.out.println("Submit clicked");
			//Edit mode
			if (isEditMode) {
				//This means the user is done editing the assignment: proceed to the final step of the Edit Assignment procedure
				editAssignment();
			} 
			//New Assignment Mode
			else {
				//Attempt to create a new Assignment with the current input
				addAssignment();
			}
		}

		//User wants to mark an assignment as complete
		else if (src.equals(completeButton)) {
			completeAssignment(assignmentJList.getSelectedIndex());
		}

		//All other GUIButtons
		else if (src instanceof GUIButton) {	
			GUIButton button = (GUIButton)src;

			//Button is a label for the day
			if(button.isDayLabel) {
				//Do Nothing
			}

			//Button is a Block
			else {
				//Un-highlight the already highlighted block
				deselect();
				//Re-highlight the clicked GUIButton if it represents a class
				if(block.getBlock().isClass()) {
					select(button);

					//Create the New Assignments window for that GUIButton
					showAssignments();

					//Give focus to the intial nameField
					nameField.grabFocus();
					nameField.requestFocus();
				}
			}
		}
	}
}
