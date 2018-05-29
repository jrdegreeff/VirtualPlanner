package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import virtualPlanner.backend.Assignment;
import virtualPlanner.backend.Course;
import virtualPlanner.gui.MainCalendarWindow.CalendarButton;
import virtualPlanner.reference.AssignmentTypes;
import virtualPlanner.reference.Fonts;
import virtualPlanner.util.Block;
import virtualPlanner.util.Date;

/**
 * This class represents an Assignment Window for each individual block in the schedule
 * @author KevinGao
 *
 */
public class AssignmentWindow implements ActionListener{

	private GUIController controller;
	private JFrame frame;

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

	/**String array which represents all the months in a year*/
	private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	/**String array which represents all the days in a month*/
	private static final String[] DAYS = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	/**String array which represents years*/
	private static final String[] YEARS = {"2018", "2019", "2020", "2021"};
	/**AssignmentTypes in the JComboBox drop-down used in selecting the type of Assignment*/
	private static final AssignmentTypes[] TYPES = {AssignmentTypes.HOMEWORK, AssignmentTypes.TEST, AssignmentTypes.QUIZ, AssignmentTypes.ESSAY, AssignmentTypes.PROJECT};
	/**Size of the Add Assignment Window*/
	private static final Dimension ASSIGNMENT_WINDOW_SIZE = new Dimension(400, 775);
	/**Size of the Current Assignments JList*/
	private static final Dimension ASSIGNMENT_LIST_SIZE = new Dimension(275, 200);
	/**Size of the Input Fields within the Add Assignment Window*/
	private static final Dimension INPUT_FIELD_SIZE = new Dimension(250, 35);
	
	/**Date Object which represents the current date in real life*/
	private static Date currentDate = MainCalendarWindow.getCurrentDate();

	private ArrayList<Assignment> assignments;
	private Date date;
	private Block block;
	private Course course;

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

	public AssignmentWindow(String title, ArrayList<Assignment> assignments, Date date, Block block, Course course, CalendarButton parent, GUIController controller) {
		//Make new JFrame for the New Assignment Window
		frame = new JFrame(title);
		frame.setResizable(false);
		frame.setSize(ASSIGNMENT_WINDOW_SIZE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//Override the Close Operation
		frame.addWindowListener(new WindowAdapter() {
			//This method is called when the user closes the New Assignment Window
			public void windowClosing(WindowEvent windowEvent) {
				parent.assignmentWindowClosed();
				//Dispose the JFrame
				frame.dispose();
			}
		});

		this.assignments = assignments;
		this.date = date;
		this.block = block;
		this.course = course;
		this.controller = controller;
		this.isEditMode = false;
		
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

		//Nested Panel 
		JPanel panelCompleteButton = new JPanel();
		panelCompleteButton.add(completeButton);

		//JLabel which notifies the user of the current mode
		//Defaults to New Assignment mode
		labelNewAssignment = new JLabel("New Assignment:");
		labelNewAssignment.setBackground(Color.GREEN);
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
		//Default button of JFrame >> Enter defaults to calling actionPerformed with submitButton as the source
		frame.getRootPane().setDefaultButton(submitButton);
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
		frame.add(mainVertical);
		frame.setVisible(true);
	}

	/**
	 * Updates the JList of current assignments in the New Assignment Window
	 */
	public void updateAssignmentList() {

		//Use GUIController to obtain most updated list of assignments
		assignments = controller.getAssignments(date, block);

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
			curAssignments[i] = a.getName() + " (" + a.getDescrip() + ")";
		}

		//Set the assignmentJList to display the current Assignments
		assignmentJList.setListData(curAssignments);
		assignmentScrollPane.revalidate();
		assignmentScrollPane.repaint();
	}

	/**
	 * Enables the edit assignment features within the Add Assignment Window for a particular existing Assignment
	 */
	public void showEditAssignmentMode(int index) {

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
	public void hideEditAssignmentMode() {
		//Quit if
		//1) Already in New Assignment mode
		if (!isEditMode)
			return;
		isEditMode = false;

		//Revert the visual change in showEditAssignmentMode() to notify/remind user they have returned to New Assignment Mode
		labelNewAssignment.setText("New Assignment:");
		labelNewAssignment.setBackground(Color.GREEN);
		submitButton.setText("Submit");
		submitButton.setBackground(Color.GREEN);
		nameField.setText("");
		descField.setText("");
		assignedMonthBox.setSelectedItem(MONTHS[currentDate.getMonth()]);
		assignedDayBox.setSelectedItem(DAYS[currentDate.getDay()-1]);
		assignedYearBox.setSelectedItem(YEARS[currentDate.getYear()-2018]);
		dueMonthBox.setSelectedItem(MONTHS[currentDate.getMonth()]);
		dueDayBox.setSelectedItem(DAYS[currentDate.getDay()-1]);
		dueYearBox.setSelectedItem(YEARS[currentDate.getYear()-2018]);
	}
	
	/**
	 * This method gives focus to the Name Field in this AssignmentWindow
	 */
	public void giveNameFieldFocus() {
		//Give focus to the intial nameField
		nameField.grabFocus();
		nameField.requestFocus();
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
		
		nameField.setText("");
		descField.setText("");
		assignedMonthBox.setSelectedItem(MONTHS[currentDate.getMonth()]);
		assignedDayBox.setSelectedItem(DAYS[currentDate.getDay()-1]);
		assignedYearBox.setSelectedItem(YEARS[currentDate.getYear()-2018]);
		dueMonthBox.setSelectedItem(MONTHS[currentDate.getMonth()]);
		dueDayBox.setSelectedItem(DAYS[currentDate.getDay()-1]);
		dueYearBox.setSelectedItem(YEARS[currentDate.getYear()-2018]);
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

	public void actionPerformed(ActionEvent e){
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

	}

}
