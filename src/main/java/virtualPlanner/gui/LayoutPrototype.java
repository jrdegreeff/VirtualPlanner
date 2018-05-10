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
	private static Font calendarFont = new Font("Dialog", Font.BOLD, 18);

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
	public void loadImages()
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

		//Calendar Portion
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

		//Day Labels
		ArrayList<JButton> days = new ArrayList<JButton>();
		days.add(new JButton("Mon"));
		days.add(new JButton("Tue"));
		days.add(new JButton("Wed"));
		days.add(new JButton("Thu"));
		days.add(new JButton("Fri"));
		days.add(new JButton("Sat"));
		days.add(new JButton("Sun"));
		
		
		//Default constant values
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 10;
		int calendarMarkerHeight = days.get(0).getPreferredSize().height;
		System.out.println(calendarMarkerHeight);
		for (int i = 0; i < days.size(); i++)
		{
			c.gridx = i;
			JButton button = days.get(i);
			button.setPreferredSize(new Dimension(calendarColumnWidth, calendarMarkerHeight));
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.setFont(calendarFont);
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
			button.setFont(calendarFont);
			button.setPreferredSize(new Dimension(calendarColumnWidth, 30));
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.setFont(calendarFont);
			panelCalendar.add(mondayButtons.get(i), c);
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

}
