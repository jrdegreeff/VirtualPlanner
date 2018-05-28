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
 * This window is used during the adding of courses to allow the user to input special circumstances for class meeting times (ie. Drop blocks, L blocks, Middle of day double-blocks)
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
		frame = new JFrame(name);
		frame.setSize(BLOCK_PICKER_SIZE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				frame.dispose();
				//TODO: Pass in the number of blocks
			}
		});

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
	 * This method instantiates the GUICheckBoxes and adds them to their corresponding ArrayList and TreeMap
	 */
	private void addCheckBoxes(){
		monC = new GUICheckBox("C", Blocks.C, "Monday");
		checkBoxes.add(monC);
		//blockMap.put(monC, null);

		monF = new GUICheckBox("F", Blocks.F, "Monday");
		checkBoxes.add(monF);
		//blockMap.put(monF, null);

		monD = new GUICheckBox("D", Blocks.D, "Monday");
		checkBoxes.add(monD);
		//blockMap.put(monD, null);

		monE = new GUICheckBox("E", Blocks.E, "Monday");
		checkBoxes.add(monE);
		//blockMap.put(monE, null);

		monG = new GUICheckBox("G", Blocks.G, "Monday");
		checkBoxes.add(monG);
		//blockMap.put(monG, null);

		monB = new GUICheckBox("B", Blocks.B, "Monday");
		checkBoxes.add(monB);
		//blockMap.put(monB, null);

		monA = new GUICheckBox("A", Blocks.A, "Monday");
		checkBoxes.add(monC);
		//blockMap.put(monC, null);

		monL = new GUICheckBox("L", Blocks.L, "Monday");
		checkBoxes.add(monL);
		//blockMap.put(monL, null);

		tueD = new GUICheckBox("D", Blocks.D, "Tuesday");
		checkBoxes.add(tueD);
		//blockMap.put(tueD, null);

		tueA = new GUICheckBox("A", Blocks.A, "Tuesday");
		checkBoxes.add(tueA);
		//blockMap.put(tueA, null);

		tueC = new GUICheckBox("C", Blocks.C, "Tuesday");
		checkBoxes.add(tueC);
		//blockMap.put(tueC, null);

		tueB = new GUICheckBox("B", Blocks.B, "Tuesday");
		checkBoxes.add(tueB);
		//blockMap.put(tueB, null);

		tueH = new GUICheckBox("H", Blocks.H, "Tuesday");
		checkBoxes.add(tueH);
		//blockMap.put(tueH, null);

		tueF = new GUICheckBox("F", Blocks.F, "Tuesday");
		checkBoxes.add(tueF);
		//blockMap.put(tueF, null);

		tueE = new GUICheckBox("E", Blocks.E, "Tuesday");
		checkBoxes.add(tueE);
		//blockMap.put(tueE, null);

		tueL = new GUICheckBox("L", Blocks.L, "Tuesday");
		checkBoxes.add(tueL);
		//blockMap.put(tueL, null);

		wedB = new GUICheckBox("B", Blocks.B, "Wednesday");
		checkBoxes.add(wedB);
		//blockMap.put(wedB, null);

		wedC = new GUICheckBox("C", Blocks.C, "Wednesday");
		checkBoxes.add(wedC);
		//blockMap.put(wedC, null);

		wedA = new GUICheckBox("A", Blocks.A, "Wednesday");
		checkBoxes.add(wedA);
		//blockMap.put(wedA, null);

		wedF = new GUICheckBox("F", Blocks.F, "Wednesday");
		checkBoxes.add(wedF);
		//blockMap.put(wedF, null);

		wedG = new GUICheckBox("G", Blocks.G, "Wednesday");
		checkBoxes.add(wedG);
		//blockMap.put(wedG, null);

		thuE = new GUICheckBox("E", Blocks.E, "Thursday");
		checkBoxes.add(thuE);
		//blockMap.put(thuE, null);

		thuD = new GUICheckBox("D", Blocks.D, "Thursday");
		checkBoxes.add(thuD);
		//blockMap.put(thuD, null);

		thuH = new GUICheckBox("H", Blocks.H, "Thursday");
		checkBoxes.add(thuH);
		//blockMap.put(thuH, null);

		thuF = new GUICheckBox("F", Blocks.F, "Thursday");
		checkBoxes.add(thuF);
		//blockMap.put(thuF, null);

		thuB = new GUICheckBox("B", Blocks.B, "Thursday");
		checkBoxes.add(thuB);
		//blockMap.put(thuB, null);

		thuC = new GUICheckBox("C", Blocks.C, "Thursday");
		checkBoxes.add(thuC);
		//blockMap.put(thuC, null);

		thuL = new GUICheckBox("L", Blocks.L, "Thursday");
		checkBoxes.add(thuL);
		//blockMap.put(thuL, null);

		friF = new GUICheckBox("F", Blocks.F, "Friday");
		checkBoxes.add(friF);
		//blockMap.put(friF, null);

		friE = new GUICheckBox("E", Blocks.E, "Friday");
		checkBoxes.add(friE);
		//blockMap.put(friE, null);

		friB = new GUICheckBox("B", Blocks.B, "Friday");
		checkBoxes.add(friB);
		//blockMap.put(friB, null);

		friC = new GUICheckBox("C", Blocks.C, "Friday");
		checkBoxes.add(friC);
		//blockMap.put(friC, null);

		friG = new GUICheckBox("G", Blocks.G, "Friday");
		checkBoxes.add(friG);
		//blockMap.put(friG, null);

		friA = new GUICheckBox("A", Blocks.A, "Friday");
		checkBoxes.add(friA);
		//blockMap.put(friA, null);

		friD = new GUICheckBox("D", Blocks.D, "Friday");
		checkBoxes.add(friD);
		//blockMap.put(friD, null);

		friL = new GUICheckBox("L", Blocks.L, "Friday");
		checkBoxes.add(friL);
		//blockMap.put(friL, null);

		satA = new GUICheckBox("A", Blocks.A, "Saturday");
		checkBoxes.add(satA);
		//blockMap.put(satA, null);

		satH = new GUICheckBox("H", Blocks.H, "Saturday");
		checkBoxes.add(satH);
		//blockMap.put(satH, null);

		satE = new GUICheckBox("E", Blocks.E, "Saturday");
		checkBoxes.add(satE);
		//blockMap.put(satE, null);

		satD = new GUICheckBox("D", Blocks.D, "Saturday");
		checkBoxes.add(satD);
		//blockMap.put(satD, null);
		
		for(GUICheckBox c: checkBoxes){
			c.setFocusPainted(false);
		}
	}
	
	public static void main(String[] args){
		new GUIBlockPicker("Pick the meeting times");
	}

}

/**
 * This class is a very simple extension of javax.swing.JCheckBox which helps correspond JCheckBoxes to their corresponding Block and Day of Week
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
	 * Constructor for GUICheckBox
	 * GUICheckBox for uncheckable placeholders (ie. Assembly)
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
