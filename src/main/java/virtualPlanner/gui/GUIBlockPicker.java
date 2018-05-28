package virtualPlanner.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import virtualPlanner.reference.Blocks;

/**
 * This class creates a pop-up window which holds a model of the MX block-schedule
 * This window is used during the adding of courses to allow the user to input special circumstances for class meeting times (ie. Drop blocks, L blocks, Middle-of-day double blocks)
 * @author Kevin
 *
 */
public class GUIBlockPicker {

	/**The JFrame of this GUIBlockPicker instance*/
	private JFrame frame;

	/**Size of the window*/
	private static final Dimension BLOCK_PICKER_SIZE = new Dimension(500, 400);

	/**ArrayList of the GUICheckBoxes*/
	private ArrayList<GUICheckBox> checkBoxes = new ArrayList<GUICheckBox>();

	/**The GUICheckBoxes which represent Blocks*/
	private GUICheckBox monC, monF, monD, monE, monG, monB, monA, monL, tueD, tueA, tueC, tueB, tueH, tueF, tueE, tueL, wedB, wedC, wedA, wedF, wedG, thuE, thuD, thuH, thuF, thuB, thuC, thuL, friF, friE, friB, friC, friG, friA, friD, friL, satA, satH, satE, satD;


	/**
	 * Constructor for the GUIBlockPicker which creates a BlockPicker Window
	 * @param name the title of the JFrame
	 */
	public GUIBlockPicker(String name) {
		//Frame Settings
		frame = new JFrame(name);
		frame.setSize(BLOCK_PICKER_SIZE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//Override close operation
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				getSelectedBlocks();
				frame.dispose();
			}
		});

		//Instantiate all the CheckBoxes
		addCheckBoxes();
		
		frame.add(getModelCalendar());
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	/**
	 * @return the blocks that correspond to the currently selected GUICheckBoxes
	 */
	public ArrayList<Blocks> getSelectedBlocks() {
		ArrayList<Blocks> result = new ArrayList<Blocks>();
		for(GUICheckBox c: checkBoxes) {
			if(c.isSelected()) {
				result.add(c.getBlock());
			}
		}
		//TODO: CORRECT BLOCKS?
		return result;
	}

	/**
	 * @return the JPanel with all of the GUICheckBoxes modeling the Block Schedule
	 */
	private JPanel getModelCalendar(){
		JPanel result = new JPanel();
		result.setLayout(new GridLayout(11,6));

		result.add(new JLabel("Mon", JLabel.CENTER));
		result.add(new JLabel("Tue", JLabel.CENTER));
		result.add(new JLabel("Wed", JLabel.CENTER));
		result.add(new JLabel("Thu", JLabel.CENTER));
		result.add(new JLabel("Fri", JLabel.CENTER));
		result.add(new JLabel("Sat", JLabel.CENTER));

		result.add(monC);
		result.add(tueD);
		result.add(wedB);
		result.add(new GUICheckBox("FCMTG", false));
		result.add(friF);
		result.add(satA);
		result.add(monF);
		result.add(tueA);
		result.add(wedC);
		result.add(thuE);
		result.add(friE);
		result.add(satH);
		result.add(new GUICheckBox("MTNG", false));
		result.add(new GUICheckBox("ASSEM", false));
		result.add(new GUICheckBox("CHAPEL", false));
		result.add(thuD);
		result.add(new GUICheckBox("SENATE", false));
		result.add(new GUICheckBox("ASSEM", false));
		result.add(monD);
		result.add(tueC);
		result.add(wedA);
		result.add(thuH);
		result.add(friB);
		result.add(satE);
		result.add(monE);
		result.add(tueB);
		result.add(wedF);
		result.add(thuF);
		result.add(friC);
		result.add(satD);
		result.add(monG);
		result.add(tueH);
		result.add(wedG);
		result.add(new GUICheckBox("LUNCH", false));
		result.add(friG);
		for(int i = 0; i < 4; i ++)
			result.add(new GUICheckBox("LUNCH", false));
		result.add(thuB);
		result.add(new GUICheckBox("LUNCH", false));
		result.add(new JPanel());
		result.add(monB);
		result.add(tueF);
		result.add(new JPanel());
		result.add(thuC);
		result.add(friA);
		result.add(new JPanel());
		result.add(monA);
		result.add(tueE);
		result.add(new JPanel());
		result.add(thuL);
		result.add(friD);
		result.add(new JPanel());
		result.add(monL);
		result.add(tueL);
		for(int i = 0; i < 2; i ++)
			result.add(new JPanel());
		result.add(friL);
		result.add(new JPanel());

		return result;
	}

	/**
	 * This method instantiates the GUICheckBoxes, and corresponds them to their appropriate Blocks and Days
	 */
	private void addCheckBoxes(){
		monC = new GUICheckBox("C", Blocks.C, "Monday");
		checkBoxes.add(monC);

		monF = new GUICheckBox("F", Blocks.F, "Monday");
		checkBoxes.add(monF);

		monD = new GUICheckBox("D", Blocks.D, "Monday");
		checkBoxes.add(monD);

		monE = new GUICheckBox("E", Blocks.E, "Monday");
		checkBoxes.add(monE);

		monG = new GUICheckBox("G", Blocks.G, "Monday");
		checkBoxes.add(monG);

		monB = new GUICheckBox("B", Blocks.B, "Monday");
		checkBoxes.add(monB);

		monA = new GUICheckBox("A", Blocks.A, "Monday");
		checkBoxes.add(monC);

		monL = new GUICheckBox("L", Blocks.L, "Monday");
		checkBoxes.add(monL);

		tueD = new GUICheckBox("D", Blocks.D, "Tuesday");
		checkBoxes.add(tueD);

		tueA = new GUICheckBox("A", Blocks.A, "Tuesday");
		checkBoxes.add(tueA);

		tueC = new GUICheckBox("C", Blocks.C, "Tuesday");
		checkBoxes.add(tueC);

		tueB = new GUICheckBox("B", Blocks.B, "Tuesday");
		checkBoxes.add(tueB);

		tueH = new GUICheckBox("H", Blocks.H, "Tuesday");
		checkBoxes.add(tueH);

		tueF = new GUICheckBox("F", Blocks.F, "Tuesday");
		checkBoxes.add(tueF);

		tueE = new GUICheckBox("E", Blocks.E, "Tuesday");
		checkBoxes.add(tueE);

		tueL = new GUICheckBox("L", Blocks.L, "Tuesday");
		checkBoxes.add(tueL);

		wedB = new GUICheckBox("B", Blocks.B, "Wednesday");
		checkBoxes.add(wedB);

		wedC = new GUICheckBox("C", Blocks.C, "Wednesday");
		checkBoxes.add(wedC);

		wedA = new GUICheckBox("A", Blocks.A, "Wednesday");
		checkBoxes.add(wedA);

		wedF = new GUICheckBox("F", Blocks.F, "Wednesday");
		checkBoxes.add(wedF);

		wedG = new GUICheckBox("G", Blocks.G, "Wednesday");
		checkBoxes.add(wedG);

		thuE = new GUICheckBox("E", Blocks.E, "Thursday");
		checkBoxes.add(thuE);

		thuD = new GUICheckBox("D", Blocks.D, "Thursday");
		checkBoxes.add(thuD);

		thuH = new GUICheckBox("H", Blocks.H, "Thursday");
		checkBoxes.add(thuH);

		thuF = new GUICheckBox("F", Blocks.F, "Thursday");
		checkBoxes.add(thuF);

		thuB = new GUICheckBox("B", Blocks.B, "Thursday");
		checkBoxes.add(thuB);

		thuC = new GUICheckBox("C", Blocks.C, "Thursday");
		checkBoxes.add(thuC);

		thuL = new GUICheckBox("L", Blocks.L, "Thursday");
		checkBoxes.add(thuL);

		friF = new GUICheckBox("F", Blocks.F, "Friday");
		checkBoxes.add(friF);

		friE = new GUICheckBox("E", Blocks.E, "Friday");
		checkBoxes.add(friE);

		friB = new GUICheckBox("B", Blocks.B, "Friday");
		checkBoxes.add(friB);

		friC = new GUICheckBox("C", Blocks.C, "Friday");
		checkBoxes.add(friC);

		friG = new GUICheckBox("G", Blocks.G, "Friday");
		checkBoxes.add(friG);

		friA = new GUICheckBox("A", Blocks.A, "Friday");
		checkBoxes.add(friA);

		friD = new GUICheckBox("D", Blocks.D, "Friday");
		checkBoxes.add(friD);

		friL = new GUICheckBox("L", Blocks.L, "Friday");
		checkBoxes.add(friL);

		satA = new GUICheckBox("A", Blocks.A, "Saturday");
		checkBoxes.add(satA);

		satH = new GUICheckBox("H", Blocks.H, "Saturday");
		checkBoxes.add(satH);

		satE = new GUICheckBox("E", Blocks.E, "Saturday");
		checkBoxes.add(satE);

		satD = new GUICheckBox("D", Blocks.D, "Saturday");
		checkBoxes.add(satD);
		
		for(GUICheckBox c: checkBoxes){
			c.setFocusPainted(false);
		}
	}
}

/**
 * This class is a very simple extension of javax.swing.JCheckBox which helps correspond JCheckBoxes to their corresponding Block and Day of Week
 * Used only within GUIBlockPicker
 * @author Kevin
 *
 */
@SuppressWarnings("serial")
class GUICheckBox extends JCheckBox {
	
	/**The block that corresponds to this JCheckBox*/
	private Blocks block;
	
	/**The Day of Week of this GUICheckBox*/
	private String dayOfWeek;
	
	/**
	 * Default constructor for GUICheckBox
	 * GUICheckBox for the clickable block options
	 * @param name the name of the CheckBox
	 * @param block the corresponding block
	 */
	public GUICheckBox(String name, Blocks block, String dayOfWeek) {
		super(name);
		this.block = block;
		this.dayOfWeek = dayOfWeek;
	}
	
	/**
	 * GUICheckBox for uncheckable placeholders (ie. Assembly, FCMTG, etc.)
	 * @param name the name of the CheckBox
	 * @param block the corresponding block
	 */
	public GUICheckBox(String name, boolean selectable) {
		super(name);
		this.setEnabled(selectable);
	}
	
	/**
	 * @return the block that corresponds to this GUICheckBox
	 */
	public Blocks getBlock() {
		return block;
	}
	
	/**
	 * @return the String representation of the Day of Week that corresponds to this GUICheckBox
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	
}
