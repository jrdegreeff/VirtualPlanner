package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
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
		buttonRight = new JButton();
		panelCalendar = new JPanel();
		labelWeek = new JLabel("May 5 – May 8");
		labelWeek.setOpaque(true);
		labelWeek.setBackground(Color.WHITE);
		labelWeek.setForeground(Color.BLUE);
		labelWeek.setFont(defaultFont);

		
		Box level1 = Box.createHorizontalBox();
		level1.add(buttonLeft);
		level1.add(Box.createHorizontalStrut(30));
		level1.add(labelWeek);
		level1.add(Box.createHorizontalStrut(30));
		level1.add(buttonRight);
		
		Box calendarVertical = Box.createVerticalBox();
		
		calendarVertical.add(level1);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.add(calendarVertical);
		frame.add(mainPanel);



		
		//		frame.setJMenuBar(menuBar);
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
