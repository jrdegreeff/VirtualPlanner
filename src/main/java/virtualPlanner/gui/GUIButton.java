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
import virtualPlanner.reference.Fonts;
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

	private static final Border DEFAULT_BORDER = BorderFactory.createEtchedBorder(1);
	private static final Border HIGHLIGHTED_BORDER = BorderFactory.createEtchedBorder(1, Color.RED, Color.WHITE);

<<<<<<< HEAD
	private static final Dimension ASSIGNMENT_WINDOW_SIZE = new Dimension(400, 775);
	private static final Dimension ASSIGNMENT_LIST_SIZE = new Dimension(275, 200);
	private static final Dimension INPUT_FIELD_SIZE = new Dimension(250, 35);

	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 26);
	private static final Font ASSIGNMENT_FONT = new Font("Dialog", Font.BOLD, 20);
	private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 20);
	private static final Font NEW_ASSIGNMENT_FONT = new Font("Dialog", Font.BOLD, 18);
=======
	private static final Dimension showAssignmentsSize = new Dimension(400, 775);
	private static final Dimension assignmentListSize = new Dimension(275, 200);
	private static final Dimension inputFieldSize = new Dimension(250, 35);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
	
	private GUIController controller;

	private static Date currentDate = MainCalendarWindow.getCurrentDate();

	private static GUIButton highlightedButton;

	private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private static final String[] DAYS = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	private static final String[] YEARS = {"2018", "2019", "2020", "2021"};
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
		this.setBorder(DEFAULT_BORDER);
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
	 * Shows the Edit assignment features for an already existing assignment
	 */
	private void showEditAssignmentMode(int index) {
		if (isEditMode || assignments == null || assignments.size()-1 < index)
			return;
		isEditMode = true;

		Assignment a = assignments.get(index);

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
		assignmentsWindow.setSize(ASSIGNMENT_WINDOW_SIZE);
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
<<<<<<< HEAD
		labelCurAssignments.setFont(TITLE_FONT);
=======
		labelCurAssignments.setFont(Fonts.BUTTON_TITLE);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
		labelCurAssignments.setOpaque(true);
		JPanel panelLabelCurAssignments = new JPanel();
		panelLabelCurAssignments.add(labelCurAssignments);

		//Assignment JList and JScrollPane
		updateAssignmentList();

<<<<<<< HEAD
		assignmentList.setFont(ASSIGNMENT_FONT);
=======
		//TODO remove tester
		assignmentList.setFont(Fonts.BUTTON_ASSIGNMENT);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7

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
		assignmentScrollPane.setPreferredSize(ASSIGNMENT_LIST_SIZE);

		JPanel panelAssignmentList = new JPanel();
		panelAssignmentList.add(assignmentScrollPane);

		completeButton = new JButton("Complete Assignment");
<<<<<<< HEAD
		completeButton.setFont(BUTTON_FONT);
=======
		completeButton.setFont(Fonts.BUTTON_BUTTON);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
		completeButton.setBackground(Color.green);
		completeButton.addActionListener(this);
		completeButton.setFocusable(false);
		JPanel panelCompleteButton = new JPanel();
		panelCompleteButton.add(completeButton);

		//JLabel "New Assignment"
		labelNewAssignment = new JLabel("New Assignment:");
<<<<<<< HEAD
		labelNewAssignment.setFont(TITLE_FONT);
=======
		labelNewAssignment.setFont(Fonts.BUTTON_TITLE);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
		labelNewAssignment.setOpaque(true);
		JPanel panelLabelNewAssignment = new JPanel();
		panelLabelNewAssignment.add(labelNewAssignment);

		//New Assignment: Name
		JLabel labelName = new JLabel("Assignment Name:");
<<<<<<< HEAD
		labelName.setFont(NEW_ASSIGNMENT_FONT);
=======
		labelName.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
		JPanel panelLabelName = new JPanel();
		panelLabelName.add(labelName);

		//Starts with focus
		nameField = new JTextField();
<<<<<<< HEAD
		nameField.setPreferredSize(INPUT_FIELD_SIZE);
		nameField.setFont(NEW_ASSIGNMENT_FONT);
=======
		nameField.setPreferredSize(inputFieldSize);
		nameField.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
		JPanel panelNameField = new JPanel();
		panelNameField.add(nameField);

		//Adding Assignment: Description
		JLabel labelDesc = new JLabel("Assignment Description (Optional):");
<<<<<<< HEAD
		labelDesc.setFont(NEW_ASSIGNMENT_FONT);
=======
		labelDesc.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
		JPanel panelLabelDesc = new JPanel();
		panelLabelDesc.add(labelDesc);

		descField = new JTextField();
<<<<<<< HEAD
		descField.setPreferredSize(INPUT_FIELD_SIZE);
		descField.setFont(NEW_ASSIGNMENT_FONT);
=======
		descField.setPreferredSize(inputFieldSize);
		descField.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
		JPanel panelDescField = new JPanel();
		panelDescField.add(descField);

		//Adding Assignment: AssignmentType
		String[] assignmentTypes = new String[AssignmentTypes.values().length];
		for(int i = 0; i < assignmentTypes.length; i++)
			assignmentTypes[i] = AssignmentTypes.values()[i].getName();
		typeBox = new JComboBox<String>(assignmentTypes);
		typeBox.setBackground(Color.WHITE);
<<<<<<< HEAD
		typeBox.setFont(NEW_ASSIGNMENT_FONT);
=======
		typeBox.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
		JPanel panelTypeBox = new JPanel();
		panelTypeBox.add(typeBox);

		//Adding Assignment: Date Assigned
		JLabel labelDateAssigned = new JLabel("Date Assigned:");
<<<<<<< HEAD
		labelDateAssigned.setFont(NEW_ASSIGNMENT_FONT);
=======
		labelDateAssigned.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
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


		//Set Defaults for ASSIGNED DATE
		assignedMonthBox.setSelectedItem(MONTHS[currentDate.getMonth()]);
		assignedDayBox.setSelectedItem(DAYS[currentDate.getDay()-1]);
		assignedYearBox.setSelectedItem(YEARS[currentDate.getYear()-2018]);

		Box assignedDatePicker = Box.createHorizontalBox();
		assignedDatePicker.add(assignedMonthBox);
		assignedDatePicker.add(assignedDayBox);
		assignedDatePicker.add(assignedYearBox);

		JPanel panelAssignedDatePicker = new JPanel();
		panelAssignedDatePicker.add(assignedDatePicker);

		//Adding Assignment: Date Due
		JLabel labelDateDue = new JLabel("Date Due:");
<<<<<<< HEAD
		labelDateDue.setFont(NEW_ASSIGNMENT_FONT);
=======
		labelDateDue.setFont(Fonts.BUTTON_NEW_ASSIGNMENT);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
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

		//Set Defaults for DUE DATE
		dueMonthBox.setSelectedItem(MONTHS[currentDate.getMonth()]);
		dueDayBox.setSelectedItem(DAYS[currentDate.getDay()-1]);
		dueYearBox.setSelectedItem(YEARS[currentDate.getYear()-2018]);

		Box dueDatePicker = Box.createHorizontalBox();
		dueDatePicker.add(dueMonthBox);
		dueDatePicker.add(dueDayBox);
		dueDatePicker.add(dueYearBox);

		JPanel panelDueDatePicker = new JPanel();
		panelDueDatePicker.add(dueDatePicker);

		//Submit Button
		submitButton = new JButton("Submit");
<<<<<<< HEAD
		submitButton.setFont(BUTTON_FONT);
=======
		submitButton.setFont(Fonts.BUTTON_BUTTON);
>>>>>>> 92ddc8704b6540e318048e3d686f8ee5edf5bfe7
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
	private void updateUpcomingEvents() {
		//ArrayList of the upcoming events - to be converted back to normal array later
		ArrayList<String> arrayListEvents = new ArrayList<String>();
		//Number of days to include in coming events
		int numDaysUpcoming = Preferences.numDaysUpcoming();
		
		//For each day within the numDaysUpcoming range
		for(int i = 0; i < numDaysUpcoming; i++) {
			//Get the corresponding date and add its list of assignments
			Date upcomingDate = currentDate.getUpcomingDate(i);
			Block[] blocks = Days.getBlocksOnDay(upcomingDate);
			for(Block b : blocks)
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
	private void updateAssignmentList() {
		//TODO: Descriptions on new line
		if (assignments == null) {
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
	private void updateButton() {
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
	private void addAssignment() {
		Date assigned = new Date(assignedDayBox.getSelectedIndex()+1, assignedMonthBox.getSelectedIndex()+1, Integer.parseInt((String)(assignedYearBox.getSelectedItem())));		
		Date due = new Date(dueDayBox.getSelectedIndex()+1, dueMonthBox.getSelectedIndex()+1, Integer.parseInt((String)(dueYearBox.getSelectedItem())));
		
		AssignmentTypes type = TYPES[typeBox.getSelectedIndex()];
		
		controller.addAssignment(course, assigned, due, type, name, descField.getText());

		updateAssignmentList();
	}

	/**
	 * This method moves a current assignment to the completed list
	 */
	private void completeAssignment(int index) {
		if (assignments == null || assignments.size() - 1 < index)
			return;

		completedAssignments.add(assignments.remove(index));
		//TODO: WORKS?
	}

	/**
	 * This method is the last part of the edit assignment procedure
	 * After the user re-enters all the information, this method edits the original Assignment
	 * Quits the edit assignment mode
	 */
	private void editAssignment() {
		Assignment a = assignmentToEdit;
		a.setName(nameField.getText());
		a.setDescrip(descField.getText());
		
		//TODO: Set Dates
		hideEditAssignmentMode();
	}
	
	/**
	 * @return an ArrayList<String> holding all the names of assignments
	 */
	public ArrayList<String> getEventsList() {
		ArrayList<String> result = new ArrayList<String>();
		for (Assignment a : assignments)
			result.add(a.getName());
		
		return result;
	}

	/**
	 * Selects a particular button.
	 * 
	 * @param button The button to select.
	 */
	public static void select(GUIButton button) {
		highlightedButton = button;
		button.setBorder(HIGHLIGHTED_BORDER);
	}

	/**
	 * Deslects the button that is currently selected.
	 */
	public static void deselect() {
		if(highlightedButton != null) {
			highlightedButton.setBorder(DEFAULT_BORDER);
			highlightedButton = null;
		}
	}

	/**
	 * ActionListener for each individual JButton
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		MainCalendarWindow.highlightCurDay();

		if (src.equals(submitButton)) {
			if (isEditMode) {
				System.out.println("User tried to finish editing");
			} else {
				System.out.println("User tried to submit");
				addAssignment();
			}
		}

		else if (src.equals(completeButton)) {
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
