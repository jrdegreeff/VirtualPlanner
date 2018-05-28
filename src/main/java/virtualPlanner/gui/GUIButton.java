package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import virtualPlanner.backend.Course;
import virtualPlanner.reference.AssignmentTypes;
import virtualPlanner.reference.Days;
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

	private static final Dimension showAssignmentsSize = new Dimension(400, 775);
	private static final Dimension assignmentListSize = new Dimension(275, 200);
	private static final Dimension inputFieldSize = new Dimension(250, 35);

	private static final Font titleFont = new Font("Dialog", Font.BOLD, 26);
	private static final Font assignmentFont = new Font("Dialog", Font.BOLD, 20);
	private static final Font buttonFont = new Font("SansSerif", Font.BOLD, 20);
	private static final Font newAssignmentFont = new Font("Dialog", Font.BOLD, 18);
	
	private GUIController controller;

	//TODO: Better retrieval
	private static Date currentDate = MainCalendarWindow.getCurrentDate();

	private static GUIButton highlightedButton;

	private static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private static final String[] days = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	private static final String[] years = {"2018", "2019", "2020", "2021"};
	private static final AssignmentTypes[] TYPES = {AssignmentTypes.HOMEWORK, AssignmentTypes.TEST, AssignmentTypes.QUIZ, AssignmentTypes.ESSAY, AssignmentTypes.PROJECT};
	
	private ArrayList<Assignment> assignments;
	private ArrayList<Assignment> completedAssignments;

	private Block block;
	private boolean hasOptionsWindow;
	private String name;
	private Color color;
	private boolean isDayLabel;
	private Course course;
	private MainCalendarWindow mainWindow;

	//Variables for adding new assignments
	private JTextField nameField, descField;
	private JComboBox<String> assignedMonthBox, assignedDayBox, assignedYearBox, dueMonthBox, dueDayBox, dueYearBox, typeBox;
	private JButton submitButton, completeButton;

	private JList<String> assignmentList = new JList<String>();
	private JScrollPane assignmentScrollPane = new JScrollPane();
	private JLabel labelNewAssignment;

	private Boolean isEditMode;
	private Assignment assignmentToEdit;

	/**
	 * Base constructor with call to super 
	 * Sets the universal settings for the buttons
	 */
	public GUIButton(String name) {
		super();
		hasOptionsWindow = false;
		this.addActionListener(this);
		this.name = name;
		this.setOpaque(true);
		this.setFocusable(false);
		this.setBackground(color);
		this.setBorder(defaultBorder);
		highlightedButton = null;
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.assignments = new ArrayList<Assignment>();
		isEditMode = false;
	}

	/**
	 * Constructor for the GUIButtons in the Calendar that represent Blocks
	 * @param block
	 * @param courseID
	 * @param assignments
	 * @param size
	 */
	public GUIButton(String name, Block block, int courseID, ArrayList<Assignment> assignments, Dimension size, Font font, GUIController controller, Course course, MainCalendarWindow mainWindow) {
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
	 * @param text
	 */
	public void setMultiLineText(String text) {
		String newText = name + text;
		this.setText("<html><center>" + newText.replaceAll("\\n", "<br>") + "</center></html>");
		this.setAlignmentX(CENTER_ALIGNMENT);
	}

	/**
	 * Shows the Edit assignment features for an already exisiting assignment
	 */
	private void showEditAssignmentMode(int index) {
		if (isEditMode || assignments == null || assignments.size()-1 < index)
			return;
		isEditMode = true;

		Assignment a = assignments.get(index);

		nameField.setText(a.getName());
		descField.setText(a.getDescrip());
		Date assignedDate = a.getAssignedDate();
		assignedMonthBox.setSelectedItem(months[assignedDate.getMonth()]);
		assignedDayBox.setSelectedItem(days[assignedDate.getDay()-1]);
		assignedYearBox.setSelectedItem(years[assignedDate.getYear()-2018]);
		Date dueDate = a.getDue();
		dueMonthBox.setSelectedItem(months[dueDate.getMonth()]);
		dueDayBox.setSelectedItem(days[dueDate.getDay()-1]);
		dueYearBox.setSelectedItem(years[dueDate.getYear()-2018]);

		labelNewAssignment.setText("Edit Assignment");
		labelNewAssignment.setBackground(Color.YELLOW);
		submitButton.setText("Finish");
		submitButton.setBackground(Color.YELLOW);

		assignmentToEdit = a;
	}

	/**
	 * Hides the Edit assignment features, returns to the "New Assignment" mode
	 */
	private void hideEditAssignmentMode() {
		if (!isEditMode)
			return;
		isEditMode = false;
		labelNewAssignment.setText("New Assignment");
		labelNewAssignment.setBackground(Color.GREEN);
		submitButton.setText("Submit");
		submitButton.setBackground(Color.GREEN);
	}

	/**
	 * Creates a window of options for this GUIButton
	 */

	private void showAssignments() {
		if (hasOptionsWindow)
			return;

		hasOptionsWindow = true;

		JFrame assignmentsWindow = new JFrame(name);
		assignmentsWindow.setSize(showAssignmentsSize);
		assignmentsWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		assignmentsWindow.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				hasOptionsWindow = false;
				assignmentsWindow.dispose();
				updateButton();
				updateUpcomingEvents();
			}
		});



		//JLabel "Current Assignments"
		JLabel labelCurAssignments = new JLabel("Current Assignments:");
		labelCurAssignments.setFont(titleFont);
		labelCurAssignments.setOpaque(true);
		//		labelCurAssignments.setBackground(Color.WHITE);
		JPanel panelLabelCurAssignments = new JPanel();
		panelLabelCurAssignments.add(labelCurAssignments);

		//Assignment JList and JScrollPane
		updateAssignmentList();

		//TODO remove tester
		assignmentList.setFont(assignmentFont);

		//Double-Click edit feature
		assignmentList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					int index = assignmentList.locationToIndex(e.getPoint());
					showEditAssignmentMode(index);
				} 
			}
		});


		assignmentScrollPane = new JScrollPane(assignmentList);
		assignmentScrollPane.setPreferredSize(assignmentListSize);

		JPanel panelAssignmentList = new JPanel();
		panelAssignmentList.add(assignmentScrollPane);

		completeButton = new JButton("Complete Assignment");
		completeButton.setFont(buttonFont);
		completeButton.setBackground(Color.green);
		completeButton.addActionListener(this);
		completeButton.setFocusable(false);
		JPanel panelCompleteButton = new JPanel();
		panelCompleteButton.add(completeButton);

		//JLabel "New Assignment"
		labelNewAssignment = new JLabel("New Assignment:");
		labelNewAssignment.setFont(titleFont);
		labelNewAssignment.setOpaque(true);
		//		labelNewAssignment.setBackground(Color.WHITE);
		JPanel panelLabelNewAssignment = new JPanel();
		panelLabelNewAssignment.add(labelNewAssignment);

		//New Assignment: Name
		JLabel labelName = new JLabel("Assignment Name:");
		labelName.setFont(newAssignmentFont);
		JPanel panelLabelName = new JPanel();
		panelLabelName.add(labelName);

		//Starts with focus
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

		//Adding Assignment: AssignmentType
		String[] assignmentTypes = new String[AssignmentTypes.values().length];
		for(int i = 0; i < assignmentTypes.length; i++)
			assignmentTypes[i] = AssignmentTypes.values()[i].getName();
		typeBox = new JComboBox<String>(assignmentTypes);
		typeBox.setBackground(Color.WHITE);
		typeBox.setFont(newAssignmentFont);
		JPanel panelTypeBox = new JPanel();
		panelTypeBox.add(typeBox);

		//Adding Assignment: Date Assigned
		JLabel labelDateAssigned = new JLabel("Date Assigned:");
		labelDateAssigned.setFont(newAssignmentFont);
		JPanel panelLabelDateAssigned = new JPanel();
		panelLabelDateAssigned.add(labelDateAssigned);

		//Month
		assignedMonthBox = new JComboBox<String>(months);
		assignedMonthBox.setBackground(Color.WHITE);

		//Day
		assignedDayBox = new JComboBox<String>(days);
		assignedDayBox.setBackground(Color.WHITE);

		//Year
		assignedYearBox = new JComboBox<String>(years);
		assignedYearBox.setBackground(Color.WHITE);

		//TODO: BETTER METHOD HERE for current day

		//Set Defaults for ASSIGNED DATE
		assignedMonthBox.setSelectedItem(months[currentDate.getMonth()]);
		assignedDayBox.setSelectedItem(days[currentDate.getDay()-1]);
		assignedYearBox.setSelectedItem(years[currentDate.getYear()-2018]);

		Box assignedDatePicker = Box.createHorizontalBox();
		assignedDatePicker.add(assignedMonthBox);
		assignedDatePicker.add(assignedDayBox);
		assignedDatePicker.add(assignedYearBox);

		JPanel panelAssignedDatePicker = new JPanel();
		panelAssignedDatePicker.add(assignedDatePicker);

		//Adding Assignment: Date Due
		JLabel labelDateDue = new JLabel("Date Due:");
		labelDateDue.setFont(newAssignmentFont);
		JPanel panelLabelDateDue = new JPanel();
		panelLabelDateDue.add(labelDateDue);

		//Month
		dueMonthBox = new JComboBox<String>(months);
		dueMonthBox.setBackground(Color.WHITE);

		//Day
		dueDayBox = new JComboBox<String>(days);
		dueDayBox.setBackground(Color.WHITE);

		//Year
		dueYearBox = new JComboBox<String>(years);
		dueYearBox.setBackground(Color.WHITE);
		//TODO: BETTER METHOD HERE

		//Set Defaults for DUE DATE
		dueMonthBox.setSelectedItem(months[currentDate.getMonth()]);
		dueDayBox.setSelectedItem(days[currentDate.getDay()-1]);
		dueYearBox.setSelectedItem(years[currentDate.getYear()-2018]);

		Box dueDatePicker = Box.createHorizontalBox();
		dueDatePicker.add(dueMonthBox);
		dueDatePicker.add(dueDayBox);
		dueDatePicker.add(dueYearBox);

		JPanel panelDueDatePicker = new JPanel();
		panelDueDatePicker.add(dueDatePicker);

		//Submit Button
		submitButton = new JButton("Submit");
		submitButton.setFont(buttonFont);
		submitButton.setBackground(Color.green);
		submitButton.addActionListener(this);
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

		assignmentsWindow.add(mainVertical);
		assignmentsWindow.setVisible(true);
	}
	
	/**
	 * This method is called whenever the user is done adding an assignment so that the "Upcoming Events" list is updated
	 */
	private void updateUpcomingEvents(){
		//ArrayList of the upcoming events - to be converted back to normal array later
		ArrayList<String> arrayListEvents = new ArrayList<String>();
		//Number of days to include in coming events
		int numDaysUpcoming = Preferences.numDaysUpcoming();
		
		//For each day within the numDaysUpcoming range
		for(int i = 0; i < numDaysUpcoming; i ++){
			//Get the corresponding date and add its list of assignments
			Date upcomingDate = currentDate.getUpcomingDate(i);
			Block[] blocks = Days.getBlocksOnDay(upcomingDate);
			for(Block b: blocks)
				arrayListEvents.addAll(controller.getAssignmentNames(upcomingDate, b));
		}
		
		//Turn ArrayList back into array
		String[] arrayEvents = arrayListEvents.toArray(new String[arrayListEvents.size()]);
		//Set "Upcoming Events" to display the array
		mainWindow.setEventsList(arrayEvents);
	}

	/**
	 * Called when a user adds/removes/edits an assignment in a specific block to update the JList
	 */
	private void updateAssignmentList()
	{
		//TODO: Descriptions on new line
		if (assignments == null)
		{
			assignmentList = new JList<String>();
			return;
		}

		int length = assignments.size();
		String[] curAssignments = new String[length];
		for (int i = 0; i < length; i ++)
			curAssignments[i] = assignments.get(i).getName();

		assignmentList = new JList<String>(curAssignments);
	}

	/**
	 * Updates the text that shows on the button
	 */
	private void updateButton(){
		String result = name;
		if (assignments == null)
			return;
		for (Assignment a: assignments)
			result += "\n" + a.getName();
		this.setMultiLineText(result);
		//TODO: Check
	}

	/**
	 * This method adds an assignment to the JList 
	 * Only called when the user clicks the submitButton
	 */
	private void addAssignment()
	{
		Date assigned = new Date(assignedDayBox.getSelectedIndex()+1, assignedMonthBox.getSelectedIndex()+1, Integer.parseInt((String)(assignedYearBox.getSelectedItem())));		
		Date due = new Date(dueDayBox.getSelectedIndex()+1, dueMonthBox.getSelectedIndex()+1, Integer.parseInt((String)(dueYearBox.getSelectedItem())));
		
		AssignmentTypes type = TYPES[typeBox.getSelectedIndex()];
		
		controller.addAssignment(course, assigned, due, type, name, descField.getText());
		
//		Assignment newAssignment = new Assignment(0, assigned, due, type, nameField.getText(), descField.getText(), false);


		updateAssignmentList();
	}

	/**
	 * This method moves a current assignment to the completed list
	 */
	private void completeAssignment(int index)
	{
		if (assignments == null || assignments.size()-1 < index)
			return;

		completedAssignments.add(assignments.remove(index));
		//TODO: WORKS?
	}

	/**
	 * This method is the last part of the edit assignment procedure
	 * After the user re-enters all the information, this method edits the original Assignment
	 * Quits the edit assignment mode
	 */
	private void editAssignment(){
		Assignment a = assignmentToEdit;
		a.setName(nameField.getText());
		a.setDescrip(descField.getText());
		
		//TODO: Set Dates
		hideEditAssignmentMode();
	}
	
	/**
	 * @return an ArrayList<String> holding all the names of assignments
	 */
	public ArrayList<String> getEventsList(){
		ArrayList<String> result = new ArrayList<String>();
		for (Assignment a : assignments){
			result.add(a.getName());
		}
		
		return result;
	}

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

		MainCalendarWindow.highlightCurDay();

		if (src.equals(submitButton)){
			if (isEditMode){
				System.out.println("User tried to finish editing");
			}
			else{
				System.out.println("User tried to submit");
				addAssignment();
			}
		}

		else if (src.equals(completeButton)){
			completeAssignment(assignmentList.getSelectedIndex());
		}


		else if (src instanceof GUIButton) {	
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

					nameField.grabFocus();
					nameField.requestFocus();
				}
			}
		}
	}
}