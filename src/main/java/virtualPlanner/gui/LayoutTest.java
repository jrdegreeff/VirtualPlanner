package virtualPlanner.gui;

import java.awt.Color;
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

import virtualPlanner.reference.Days;

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
	private GUIButton[][] buttons;
	

	/**
	 * Constructor: Creates the main GUI
	 */
	public LayoutTest() {
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
		buttons = new GUIButton[7][];
		updateGUI();

		//Frame
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Loads the images from the source folder
	 */
	private void loadImages() {
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
	public void updateGUI() {
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
		buttonLeft.setFocusable(false);
		
		buttonRight = new JButton();
		buttonRight.setIcon(new ImageIcon(imageNext));
		buttonRight.setOpaque(false);
		buttonRight.setContentAreaFilled(false);
		buttonRight.setBorderPainted(false);
		buttonRight.addActionListener(this);
		buttonRight.setFocusable(false);

		//JPanel for the calendar itself
		panelCalendar = new JPanel();
		labelWeek = new JLabel("May 5  �  May 8");
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
		spaceTaker.setForeground(Color.CYAN);
		spaceTaker.setBackground(Color.CYAN);
		spaceTaker.setFont(dateFont);
		spaceTaker.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(spaceTaker);

		
		//TODO: Unique font

		//Level 1 -> labelWeek and Prev + Next buttons
		Box level1 = Box.createHorizontalBox();
		level1.add(Box.createHorizontalGlue());
		level1.add(Box.createHorizontalGlue());
		level1.add(buttonLeft);
		level1.add(Box.createHorizontalGlue());
		level1.add(labelWeek);
		level1.add(Box.createHorizontalGlue());
		level1.add(buttonRight);
		level1.add(Box.createHorizontalGlue());
		level1.add(Box.createHorizontalGlue());
		
		initButtons();
		
		//Final Box
		JPanel calendarVertical = new JPanel();
		
		//TODO: Spacing between the week/calendar
		calendarVertical.add(level1);
		calendarVertical.add(panelCalendar);
		
		calendarVertical.setOpaque(true);
		calendarVertical.setBackground(Color.pink);
		calendarVertical.setLayout(new BoxLayout(calendarVertical, BoxLayout.Y_AXIS));
		
		Box infoVertical = Box.createVerticalBox();
		infoVertical.add(infoPanel);
		
		JPanel panelInfo = new JPanel();
		panelInfo.setOpaque(true);
		panelInfo.setBackground(Color.blue);

		panelInfo.add(infoVertical);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.add(calendarVertical);
		mainPanel.add(Box.createHorizontalGlue());
		mainPanel.add(panelInfo);

		frame.add(mainPanel);
	}
	
	public void initButtons() {
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
		ArrayList<GUIButton> days = new ArrayList<GUIButton>();
		days.add(new GUIButton("Mon"));
		days.add(new GUIButton("Tue"));
		days.add(new GUIButton("Wed"));
		days.add(new GUIButton("Thu"));
		days.add(new GUIButton("Fri"));
		days.add(new GUIButton("Sat"));
		days.add(new GUIButton("Sun"));


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
		for (int i = 0; i < days.size(); i++) {
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

		//Blocks
		for(int i = 0; i < Days.values().length; i++) {
			c.ipady = 25;
			c.gridx = i;
			Days blockOrder = Days.values()[i];
			buttons[i] = new GUIButton[blockOrder.getBlockCount()];
			for(int j = 0; j < blockOrder.getBlockCount(); j++) {
				c.gridy = j + 1;
				GUIButton button = new GUIButton(blockOrder.getBlock(j).getBlock().getAbbreviation());
				button.setVerticalAlignment(SwingConstants.TOP);
				button.setPreferredSize(new Dimension(calendarColumnWidth, 30));
				button.setOpaque(true);
				button.setBackground(Color.WHITE);
				button.setFont(calendarBlockNameFont);
				panelCalendar.add(button, c);
				button.addActionListener(this);
				buttons[i][j] = button;
			}
		}
	}
	
	

	/**
	 * Instantiates a LayoutPrototype
	 * @param args
	 */
	public static void main (String[] args) {
		new LayoutTest();
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e)  {
		Object src = e.getSource();
		//Left button on the calendar
		if (src.equals(buttonLeft)) {
			labelWeek.setText("May 1  �  May 3");
		}
		
		//Right button on the calendar
		else if (src.equals(buttonRight)) {
			labelWeek.setText("May 5  �  May 7");
		}
		
		else if (src instanceof JButton) {
			System.out.println(((JButton) src).getText());
			labelDay.setText(((JButton) src).getText());
		}
	}

	//TODO ActionListener with bordercolors
}
