package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

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

	//JMenuBar
	private static JMenuBar menuBar;
	private static JMenu menuFile, menu1, menu2, menu3; 
	private static JMenuItem menuItemFile, menuItem1, menuItem2, menuItem3;

	//JButtons
	private static JButton buttonLeft, buttonRight;

	//JLabels
	private static JLabel labelWeek;
	
	//Font
	private static Font defaultFont = new Font("SansSerif", Font.BOLD, 24); 
	
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

		menuBar.add(menuFile);
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);

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
		    System.out.println("Image loaded");
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
		labelWeek = new JLabel("May 5  �  May 8");
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
		
		JButton button1 = new JButton("Wednesday");
		JButton button2 = new JButton("Wednesday");
		JButton button3 = new JButton("Wednesday");
		JButton button4 = new JButton("Wednesday");
		JButton button5 = new JButton("Wednesday");
		JButton button6 = new JButton("Wednesday");
		JButton button7 = new JButton("Wednesday");
		JButton button8 = new JButton("Wednesday");
		
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 10;
		c.ipady = 10;
		
		c.gridx = 0;
		c.gridy = 0;
		panelCalendar.add(button1, c);
		
		c.gridx = 1;
		c.gridy = 0;
		panelCalendar.add(button2, c);
		
		c.gridx = 2;
		c.gridy = 0;
		panelCalendar.add(button3, c);
		
		c.gridx = 3;
		c.gridy = 0;
		panelCalendar.add(button4, c);
		
		c.gridx = 4;
		c.gridy = 0;
		panelCalendar.add(button5, c);
		
		c.gridx = 0;
		c.gridy = 1;
		panelCalendar.add(button6, c);
		
		c.gridx = 0;
		c.gridy = 4;
		panelCalendar.add(button7, c);
		
		//Final Box
		Box calendarVertical = Box.createVerticalBox();
		calendarVertical.add(level1);
		calendarVertical.add(panelCalendar);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.add(calendarVertical);
		frame.add(mainPanel);
		//frame.setJMenuBar(menuBar);
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
