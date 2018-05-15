package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * @author Kevin
 * This class represents a Prototype with no actionListeners/keyListeners
 */
@SuppressWarnings("serial")
public class LayoutTest extends JFrame implements ActionListener{

	//JFrame
	private static JFrame frame;
	private static JPanel mainPanel, panelCalendar, infoPanel;
	
	//TODO: Resolution
	private final static int resolutionMultiplier = 2;
	private final static int calendarColumnWidth = 60;

	//JMenuBar
	private static JMenuBar menuBar;
	private static JMenu menuFile, menu1, menu2, menu3, menu4; 
	private static JMenuItem menuItemFile, menuItem1, menuItem2, menuItem3, menuItem4;

	//JButtons
	private static JButton buttonLeft, buttonRight;

	//JLabels
	private static JLabel labelWeek, labelDay;

	//Fonts
	private static Font defaultFont = new Font("SansSerif", Font.BOLD, 24); 
	private static Font calendarDayFont = new Font("Dialog", Font.BOLD, 18);
	private static Font calendarBlockNameFont = new Font("Dialog", Font.BOLD, 12);
	private static Font dateFont = new Font("SansSerif", Font.BOLD, 40);

	//Icons and Images
	private static BufferedImage imagePrev, imageNext;
	
	//JButtons for the actionListener
	private static JButton monC, monF, monMTG, monD, monE, monG, monLUN, monB, monA, monL,
	tuesD, tuesA, tuesXAS, tuesC, tuesB, tuesH, tuesLUN, tuesF, tuesE, tuesL,
	wedB, wedC, wedXCH, wedA, wedF, wedG, wedLUN,
	thursFCMTG, thursE, thursD, thursH, thursF, thursLUN, thursB, thursC, thursL,
	friF, friE, friSEN, friB, friC, friG, friLUN, friA, friD, friL,
	satA, satH, satXAS, satE, satD, satLUN;
	

	/**
	 * Constructor: Creates the main GUI
	 */
	public LayoutTest()
	{
		//Name
		super("Virtual Planner");

		//Reference
		frame = this;

		//Size
		frame.setSize(1280, 720);

		//Color
		frame.setBackground(Color.WHITE);

		//Main Panel
		mainPanel = new JPanel();

		//Load Images
		loadImages();

		//JMenuBar
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		menuFile = new JMenu("File");
		menuItemFile = new JMenuItem("There's nothing here ):");
		menuFile.add(menuItemFile);

		menu1 = new JMenu("Leo is cool");
		menuItem1 = new JMenuItem("Just kidding lol");
		menu1.add(menuItem1);

		menu2 = new JMenu("Jeremiah is cool");
		menuItem2 = new JMenuItem("Just kidding lol");
		menu2.add(menuItem2);

		menu3 = new JMenu("Anna is cool");
		menuItem3 = new JMenuItem("Just kidding lol");
		menu3.add(menuItem3);

		menu4 = new JMenu("Kevin is cool");
		menuItem4 = new JMenuItem("TRUE IF BIG.");
		menu4.add(menuItem4);

		menuBar.add(menuFile);
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		menuBar.add(menu4);

		//Add components
		updateGUI();

		//Frame
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Loads the images from the source folder
	 */
	private void loadImages()
	{
		try {
			imagePrev = ImageIO.read(getClass().getResource("previous.png"));
			imageNext = ImageIO.read(getClass().getResource("next.png"));
			System.out.println("Images loaded");
		} catch (Exception e) {
			System.out.println("Error loading images: " + e.getMessage());
		}
	}

	/**
	 * Updates the GUI
	 */
	public void updateGUI()
	{
		//Clear frame
		frame.getContentPane().removeAll();

		//Calendar 
		
		//Left and Right Calendar buttons
		buttonLeft = new JButton();
		buttonLeft.setIcon(new ImageIcon(imagePrev));
		buttonLeft.setOpaque(false);
		buttonLeft.setContentAreaFilled(false);
		buttonLeft.setBorderPainted(false);
		buttonLeft.addActionListener(this);
		
		buttonRight = new JButton();
		buttonRight.setIcon(new ImageIcon(imageNext));
		buttonRight.setOpaque(false);
		buttonRight.setContentAreaFilled(false);
		buttonRight.setBorderPainted(false);
		buttonRight.addActionListener(this);

		//JPanel for the calendar itself
		panelCalendar = new JPanel();
		labelWeek = new JLabel("May 5  –  May 8");
		labelWeek.setOpaque(true);
		labelWeek.setBackground(Color.WHITE);
		labelWeek.setForeground(Color.RED);
		labelWeek.setFont(defaultFont);
		
		//JPanel for the right of the calendar - the 'info' section
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(Color.BLUE);
		infoPanel.setAlignmentY(CENTER_ALIGNMENT);
		infoPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		labelDay = new JLabel("Monday May 14, 2018");
		labelDay.setOpaque(true);
		labelDay.setBackground(Color.PINK);
		labelDay.setForeground(Color.BLACK);
		labelDay.setFont(dateFont);
		labelDay.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(labelDay);
		
	
		JLabel spaceTaker = new JLabel("Monday May 14, 2018");
		spaceTaker.setOpaque(true);
		spaceTaker.setForeground(Color.WHITE);
		spaceTaker.setBackground(Color.WHITE);
		spaceTaker.setFont(dateFont);
		spaceTaker.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(spaceTaker);

		
		//TODO: Unique font

		//Level 1 -> labelWeek and Prev + Next buttons
		Box level1 = Box.createHorizontalBox();
		level1.add(buttonLeft);
		level1.add(Box.createHorizontalStrut(20));
		level1.add(labelWeek);
		level1.add(Box.createHorizontalStrut(20));
		level1.add(buttonRight);

		//Main Panel for Calendar
		panelCalendar = new JPanel();
		panelCalendar.setLayout(new GridBagLayout());

		//Initial default values for GridBagLayout constraints
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 50;
		c.ipady = -26;
		//For 0:
		//c.ipady = -26;

		//Day Name Labels
		ArrayList<JButton> days = new ArrayList<JButton>();
		days.add(new JButton("Mon"));
		days.add(new JButton("Tue"));
		days.add(new JButton("Wed"));
		days.add(new JButton("Thu"));
		days.add(new JButton("Fri"));
		days.add(new JButton("Sat"));
		days.add(new JButton("Sun"));


		//Default constant values

		//x-coordinate of GridBagLayout
		c.gridx = 0;
		//y-coordinate of GridBagLayout
		c.gridy = 0;
		//Vertical Padding
		c.ipady = 10;
		//Preferred height of all JButtons
		int calendarMarkerHeight = days.get(0).getPreferredSize().height;
		//Dimension to keep all sizes constant
		Dimension d = new Dimension(calendarColumnWidth, calendarMarkerHeight);

		//i >> row
		for (int i = 0; i < days.size(); i++)
		{
			c.gridx = i;
			JButton button = days.get(i);
			button.setPreferredSize(d);
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.setFont(calendarDayFont);
//					if(i == 1)
//							button.setBackground(Color.RED);
			panelCalendar.add(button, c);
		}


		//Monday Blocks
		ArrayList<JButton> mondayButtons = new ArrayList<JButton>();
		monC = new JButton("C");
		monF = new JButton("F");
		monMTG = new JButton("MTNG");
		monD = new JButton("D");
		monE = new JButton("E");
		monG = new JButton("G");
		monLUN = new JButton("LUNCH");
		monB = new JButton("B");
		monA = new JButton("A");
		monL = new JButton("L");

		mondayButtons.add(monC);
		mondayButtons.add(monF);
		mondayButtons.add(monMTG);
		mondayButtons.add(monD);
		mondayButtons.add(monE);
		mondayButtons.add(monG);
		mondayButtons.add(monLUN);
		mondayButtons.add(monB);
		mondayButtons.add(monA);
		mondayButtons.add(monL);

		//Add Monday Blocks
		c.ipady = 25;
		c.gridx = 0;
		for(int i = 0; i < mondayButtons.size(); i ++)
		{
			c.gridy = i+1;
			JButton button = mondayButtons.get(i);
			button.setVerticalAlignment(SwingConstants.TOP);
			button.setPreferredSize(new Dimension(calendarColumnWidth, 30));
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.setFont(calendarBlockNameFont);
			panelCalendar.add(button, c);
		}

		//Tuesday Blocks
		ArrayList<JButton> tuesdayButtons = new ArrayList<JButton>();
		tuesD = new JButton("D");
		tuesA = new JButton("A");
		tuesXAS = new JButton("XAS:ASSEM");
		tuesC = new JButton("C");
		tuesB = new JButton("B");
		tuesH = new JButton("H");
		tuesLUN = new JButton("LUNCH");
		tuesF = new JButton("F");
		tuesE = new JButton("E");
		tuesL = new JButton("L");

		tuesdayButtons.add(tuesD);
		tuesdayButtons.add(tuesA);
		tuesdayButtons.add(tuesXAS);
		tuesdayButtons.add(tuesC);
		tuesdayButtons.add(tuesB);
		tuesdayButtons.add(tuesH);
		tuesdayButtons.add(tuesLUN);
		tuesdayButtons.add(tuesF);
		tuesdayButtons.add(tuesE);
		tuesdayButtons.add(tuesL);

		//Add Tuesday Blocks
		c.ipady = 25;
		c.gridx = 1;
		for(int i = 0; i < tuesdayButtons.size(); i ++)
		{
			c.gridy = i+1;
			JButton button = tuesdayButtons.get(i);
			button.setVerticalAlignment(SwingConstants.TOP);
			button.setPreferredSize(new Dimension(calendarColumnWidth, 30));
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.setFont(calendarBlockNameFont);
			panelCalendar.add(button, c);
		}

		//Wednesday Blocks
		ArrayList<JButton> wednesdayButtons = new ArrayList<JButton>();
		wedB = new JButton("B");
		wedC = new JButton("C");
		wedXCH = new JButton("XCH:CHAPEL");
		wedA = new JButton("A");
		wedF = new JButton("F");
		wedG = new JButton("G");
		wedLUN = new JButton("LUNCH");

		wednesdayButtons.add(wedB);
		wednesdayButtons.add(wedC);
		wednesdayButtons.add(wedXCH);
		wednesdayButtons.add(wedA);
		wednesdayButtons.add(wedF);
		wednesdayButtons.add(wedG);
		wednesdayButtons.add(wedLUN);

		//Add Wednesday Blocks
		c.ipady = 25;
		c.gridx = 2;
		for(int i = 0; i < wednesdayButtons.size(); i ++)
		{
			c.gridy = i+1;
			JButton button = wednesdayButtons.get(i);
			button.setVerticalAlignment(SwingConstants.TOP);
			button.setPreferredSize(new Dimension(calendarColumnWidth, 30));
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.setFont(calendarBlockNameFont);
			panelCalendar.add(button, c);
		}

		//Thursday Blocks
		ArrayList<JButton> thursdayButtons = new ArrayList<JButton>();
		thursFCMTG = new JButton("FCMTG");
		thursE = new JButton("E");
		thursD = new JButton("D");
		thursH = new JButton("H");
		thursF = new JButton("F");
		thursLUN = new JButton("LUNCH");
		thursB = new JButton("B");
		thursC = new JButton("C");
		thursL = new JButton("L");

		thursdayButtons.add(thursFCMTG);
		thursdayButtons.add(thursE);
		thursdayButtons.add(thursD);
		thursdayButtons.add(thursH);
		thursdayButtons.add(thursF);
		thursdayButtons.add(thursLUN);
		thursdayButtons.add(thursB);
		thursdayButtons.add(thursC);
		thursdayButtons.add(thursL);

		//Add Thursday Blocks
		c.ipady = 25;
		c.gridx = 3;
		for(int i = 0; i < thursdayButtons.size(); i ++)
		{
			c.gridy = i+1;
			JButton button = thursdayButtons.get(i);
			button.setVerticalAlignment(SwingConstants.TOP);
			button.setPreferredSize(new Dimension(calendarColumnWidth, 30));
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.setFont(calendarBlockNameFont);
			//			if(i == 0) TODO: DOUBLE SPACE FCMTG
			//				button.setPreferredSize(new Dimension(calendarColumnWidth, ));
			panelCalendar.add(button, c);
		}

		//Friday Blocks
		ArrayList<JButton> fridayButtons = new ArrayList<JButton>();
		friF = new JButton("F");
		friE = new JButton("E");
		friSEN = new JButton("SENATE");
		friB = new JButton("B");
		friC = new JButton("C");
		friG = new JButton("G");
		friLUN = new JButton("LUNCH");
		friA = new JButton("A");
		friD = new JButton("D");
		friL = new JButton("L");

		fridayButtons.add(friF);
		fridayButtons.add(friE);
		fridayButtons.add(friSEN);
		fridayButtons.add(friB);
		fridayButtons.add(friC);
		fridayButtons.add(friG);
		fridayButtons.add(friLUN);
		fridayButtons.add(friA);
		fridayButtons.add(friD);
		fridayButtons.add(friL);

		//Add Friday Blocks
		c.ipady = 25;
		c.gridx = 4;
		for(int i = 0; i < fridayButtons.size(); i ++)
		{
			c.gridy = i+1;
			JButton button = fridayButtons.get(i);
			button.setVerticalAlignment(SwingConstants.TOP);
			button.setPreferredSize(new Dimension(calendarColumnWidth, 30));
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.setFont(calendarBlockNameFont);
			panelCalendar.add(button, c);
		}

		//Saturday Blocks
		ArrayList<JButton> saturdayButtons = new ArrayList<JButton>();
		satA = new JButton("A");
		satH = new JButton("H");
		satXAS = new JButton("XAS:ASSEM");
		satE = new JButton("E");
		satD = new JButton("D");
		satLUN = new JButton("LUNCH");
		
		saturdayButtons.add(satA);
		saturdayButtons.add(satH);
		saturdayButtons.add(satXAS);
		saturdayButtons.add(satE);
		saturdayButtons.add(satD);
		saturdayButtons.add(satLUN);

		//Add Saturday Blocks
		c.ipady = 25;
		c.gridx = 5;
		for(int i = 0; i < saturdayButtons.size(); i ++)
		{
			c.gridy = i+1;
			JButton button = saturdayButtons.get(i);
			button.setVerticalAlignment(SwingConstants.TOP);
			button.setPreferredSize(new Dimension(calendarColumnWidth, 30));
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.setFont(calendarBlockNameFont);
			panelCalendar.add(button, c);
		}

		//2D - ArrayList implementation of the days
		ArrayList<ArrayList<JButton>> weekdays = new ArrayList<ArrayList<JButton>>();
		mondayButtons.add(days.get(0));
		tuesdayButtons.add(days.get(1));
		wednesdayButtons.add(days.get(2));
		thursdayButtons.add(days.get(3));
		fridayButtons.add(days.get(4));
		saturdayButtons.add(days.get(5));
		weekdays.add(mondayButtons);
		weekdays.add(tuesdayButtons);
		weekdays.add(wednesdayButtons);
		weekdays.add(thursdayButtons);
		weekdays.add(fridayButtons);
		weekdays.add(saturdayButtons);
		for(ArrayList<JButton> list1: weekdays)
			for(JButton j: list1)
				j.addActionListener(this);
		
		//Final Box
		Box calendarVertical = Box.createVerticalBox();
		calendarVertical.add(Box.createVerticalStrut(5));
		calendarVertical.add(level1);
		calendarVertical.add(Box.createVerticalStrut(10));
		calendarVertical.add(panelCalendar);
		
		Box infoVertical = Box.createVerticalBox();
		infoVertical.add(infoPanel);
		
		Box mainHorizontal = Box.createHorizontalBox();
		mainHorizontal.add(calendarVertical);
		mainHorizontal.add(Box.createHorizontalStrut(20));
		mainHorizontal.add(infoVertical);
		
		mainPanel.setBackground(Color.WHITE);
		mainPanel.add(mainHorizontal);
		frame.add(mainPanel);
	}

	/**
	 * Instantiates a LayoutPrototype
	 * @param args
	 */
	public static void main (String[] args)
	{
		new LayoutTest();
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) 
	{
		Object src = e.getSource();
		//Left button on the calendar
		if (src.equals(buttonLeft))
		{
			labelWeek.setText("May 1  –  May 3");
		}
		
		//Right button on the calendar
		else if (src.equals(buttonRight))
		{
			labelWeek.setText("May 5  –  May 7");
		}
		
		else if (src instanceof JButton)
		{
			System.out.println(((JButton) src).getText());
			labelDay.setText("YAY");
		}
	}

	//TODO ActionListener with bordercolors
}
