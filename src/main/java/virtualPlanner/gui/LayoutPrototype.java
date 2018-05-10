package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
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
public class LayoutPrototype extends JFrame{

	//JFrame
	private static JFrame frame;
	private static JPanel mainPanel, panelCalendar;
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
	private static JLabel labelWeek;

	//Fonts
	private static Font defaultFont = new Font("SansSerif", Font.BOLD, 24); 
	private static Font calendarDayFont = new Font("Dialog", Font.BOLD, 18);
	private static Font calendarBlockNameFont = new Font("Dialog", Font.BOLD, 12);

	//Icons and Images
	private static BufferedImage imagePrev, imageNext;

	/**
	 * Constructor: Creates the main GUI
	 */
	public LayoutPrototype()
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
	public static void updateGUI()
	{
		//Clear frame
		frame.getContentPane().removeAll();

		//Calendar 
		buttonLeft = new JButton();
		buttonLeft.setIcon(new ImageIcon(imagePrev));
		buttonLeft.setOpaque(false);
		buttonLeft.setContentAreaFilled(false);
		buttonLeft.setBorderPainted(false);

		buttonRight = new JButton();
		buttonRight.setIcon(new ImageIcon(imageNext));
		buttonRight.setOpaque(false);
		buttonRight.setContentAreaFilled(false);
		buttonRight.setBorderPainted(false);

		panelCalendar = new JPanel();
		labelWeek = new JLabel("May 5  –  May 8");
		labelWeek.setOpaque(true);
		labelWeek.setBackground(Color.WHITE);
		labelWeek.setForeground(Color.MAGENTA);
		labelWeek.setFont(defaultFont);

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
			//			if(i == 1)
			//				button.setBackground(Color.RED);
			panelCalendar.add(button, c);
		}


		//Monday Blocks
		ArrayList<JButton> mondayButtons = new ArrayList<JButton>();
		JButton monC = new JButton("C");
		JButton monF = new JButton("F");
		JButton monMTG = new JButton("MTNG");
		JButton monD = new JButton("D");
		JButton monE = new JButton("E");
		JButton monG = new JButton("G");
		JButton monLUN = new JButton("LUNCH");
		JButton monB = new JButton("B");
		JButton monA = new JButton("A");
		JButton monL = new JButton("L");

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
		JButton tuesD = new JButton("D");
		JButton tuesA = new JButton("A");
		JButton tuesXAS = new JButton("XAS:ASSEM");
		JButton tuesC = new JButton("C");
		JButton tuesB = new JButton("B");
		JButton tuesH = new JButton("H");
		JButton tuesLUN = new JButton("LUNCH");
		JButton tuesF = new JButton("F");
		JButton tuesE = new JButton("E");
		JButton tuesL = new JButton("L");

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
		JButton wedB = new JButton("B");
		JButton wedC = new JButton("C");
		JButton wedXCH = new JButton("XCH:CHAPEL");
		JButton wedA = new JButton("A");
		JButton wedF = new JButton("F");
		JButton wedG = new JButton("G");
		JButton wedLUN = new JButton("LUNCH");

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
		JButton thursFCMTG = new JButton("FCMTG");
		JButton thursE = new JButton("E");
		JButton thursD = new JButton("D");
		JButton thursH = new JButton("H");
		JButton thursF = new JButton("F");
		JButton thursLUN = new JButton("LUNCH");
		JButton thursB = new JButton("B");
		JButton thursC = new JButton("C");
		JButton thursL = new JButton("L");

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
		JButton friF = new JButton("F");
		JButton friE = new JButton("E");
		JButton friSEN = new JButton("SENATE");
		JButton friB = new JButton("B");
		JButton friC = new JButton("C");
		JButton friG = new JButton("G");
		JButton friLUN = new JButton("LUNCH");
		JButton friA = new JButton("A");
		JButton friD = new JButton("D");
		JButton friL = new JButton("L");

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
		JButton satA = new JButton("A");
		JButton satH = new JButton("H");
		JButton satXAS = new JButton("XAS:ASSEM");
		JButton satE = new JButton("E");
		JButton satD = new JButton("D");
		JButton satLUN = new JButton("LUNCH");

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

		//Final Box
		Box calendarVertical = Box.createVerticalBox();
		calendarVertical.add(Box.createVerticalStrut(5));
		calendarVertical.add(level1);
		calendarVertical.add(Box.createVerticalStrut(10));
		calendarVertical.add(panelCalendar);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.add(calendarVertical);
		frame.add(mainPanel);
	}

	/**
	 * Instantiates a LayoutPrototype
	 * @param args
	 */
	public static void main (String[] args)
	{
		new LayoutPrototype();
	}

	//TODO ActionListener with bordercolors
}
