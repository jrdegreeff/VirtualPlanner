package virtualPlanner.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import virtualPlanner.util.Block;

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
	private static final Dimension BLOCK_PICKER_SIZE = new Dimension(400, 400);

	/**ArrayList of the JCheckBoxes*/
	private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

	/**Map which corresponds the JCheckBoxes to the Block which they represent*/
	private TreeMap<JCheckBox, Block> blockMap = new TreeMap<JCheckBox, Block>();

	/**The JCheckBoxes which represent Blocks*/
	private JCheckBox monC, monF, monD, monE, monG, monB, monA, monL, tueD, tueA, tueC, tueB, tueH, tueF, tueE, tueL, wedB, wedC, wedA, wedF, wedG, thuE, thuD, thuH, thuF, thuB, thuC, thuL, friF, friE, friB, friC, friG, friA, friD, friL, satA, satH, satE, satD;


	/**
	 * Constructor for the GUIBlockPicker which creates a BlockPicker Window
	 * @param name the title of the JFrame
	 */
	public GUIBlockPicker(String name){
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
	 * @return the JPanel with all of the JCheckBoxes modeling the Block Schedule
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
		result.add(new JPanel());
		result.add(friF);
		result.add(satA);
		result.add(monF);
		result.add(tueA);
		result.add(wedC);
		result.add(thuE);
		result.add(friE);
		result.add(satH);
		for(int i = 0; i < 3; i ++)
			result.add(new JPanel());
		result.add(thuD);
		for(int i = 0; i < 2; i ++)
			result.add(new JPanel());
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
		result.add(new JPanel());
		result.add(friG);
		for(int i = 0; i < 4; i ++)
			result.add(new JPanel());
		result.add(thuB);
		for(int i = 0; i < 2; i ++)
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
	 * This method instantiates the JCheckBoxes and adds them to their corresponding ArrayList and TreeMap
	 */
	private void addCheckBoxes(){
		monC = new JCheckBox("C");
		checkBoxes.add(monC);
		//blockMap.put(monC, null);

		monF = new JCheckBox("F");
		checkBoxes.add(monF);
		//blockMap.put(monF, null);

		monD = new JCheckBox("D");
		checkBoxes.add(monD);
		//blockMap.put(monD, null);

		monE = new JCheckBox("E");
		checkBoxes.add(monE);
		//blockMap.put(monE, null);

		monG = new JCheckBox("G");
		checkBoxes.add(monG);
		//blockMap.put(monG, null);

		monB = new JCheckBox("B");
		checkBoxes.add(monB);
		//blockMap.put(monB, null);

		monA = new JCheckBox("A");
		checkBoxes.add(monC);
		//blockMap.put(monC, null);

		monL = new JCheckBox("L");
		checkBoxes.add(monL);
		//blockMap.put(monL, null);

		tueD = new JCheckBox("D");
		checkBoxes.add(tueD);
		//blockMap.put(tueD, null);

		tueA = new JCheckBox("A");
		checkBoxes.add(tueA);
		//blockMap.put(tueA, null);

		tueC = new JCheckBox("C");
		checkBoxes.add(tueC);
		//blockMap.put(tueC, null);

		tueB = new JCheckBox("B");
		checkBoxes.add(tueB);
		//blockMap.put(tueB, null);

		tueH = new JCheckBox("H");
		checkBoxes.add(tueH);
		//blockMap.put(tueH, null);

		tueF = new JCheckBox("F");
		checkBoxes.add(tueF);
		//blockMap.put(tueF, null);

		tueE = new JCheckBox("E");
		checkBoxes.add(tueE);
		//blockMap.put(tueE, null);

		tueL = new JCheckBox("L");
		checkBoxes.add(tueL);
		//blockMap.put(tueL, null);

		wedB = new JCheckBox("B");
		checkBoxes.add(wedB);
		//blockMap.put(wedB, null);

		wedC = new JCheckBox("C");
		checkBoxes.add(wedC);
		//blockMap.put(wedC, null);

		wedA = new JCheckBox("A");
		checkBoxes.add(wedA);
		//blockMap.put(wedA, null);

		wedF = new JCheckBox("F");
		checkBoxes.add(wedF);
		//blockMap.put(wedF, null);

		wedG = new JCheckBox("G");
		checkBoxes.add(wedG);
		//blockMap.put(wedG, null);

		thuE = new JCheckBox("E");
		checkBoxes.add(thuE);
		//blockMap.put(thuE, null);

		thuD = new JCheckBox("D");
		checkBoxes.add(thuD);
		//blockMap.put(thuD, null);

		thuH = new JCheckBox("H");
		checkBoxes.add(thuH);
		//blockMap.put(thuH, null);

		thuF = new JCheckBox("F");
		checkBoxes.add(thuF);
		//blockMap.put(thuF, null);

		thuB = new JCheckBox("B");
		checkBoxes.add(thuB);
		//blockMap.put(thuB, null);

		thuC = new JCheckBox("C");
		checkBoxes.add(thuC);
		//blockMap.put(thuC, null);

		thuL = new JCheckBox("L");
		checkBoxes.add(thuL);
		//blockMap.put(thuL, null);

		friF = new JCheckBox("F");
		checkBoxes.add(friF);
		//blockMap.put(friF, null);

		friE = new JCheckBox("E");
		checkBoxes.add(friE);
		//blockMap.put(friE, null);

		friB = new JCheckBox("B");
		checkBoxes.add(friB);
		//blockMap.put(friB, null);

		friC = new JCheckBox("C");
		checkBoxes.add(friC);
		//blockMap.put(friC, null);

		friG = new JCheckBox("G");
		checkBoxes.add(friG);
		//blockMap.put(friG, null);

		friA = new JCheckBox("A");
		checkBoxes.add(friA);
		//blockMap.put(friA, null);

		friD = new JCheckBox("D");
		checkBoxes.add(friD);
		//blockMap.put(friD, null);

		friL = new JCheckBox("L");
		checkBoxes.add(friL);
		//blockMap.put(friL, null);

		satA = new JCheckBox("A");
		checkBoxes.add(satA);
		//blockMap.put(satA, null);

		satH = new JCheckBox("H");
		checkBoxes.add(satH);
		//blockMap.put(satH, null);

		satE = new JCheckBox("E");
		checkBoxes.add(satE);
		//blockMap.put(satE, null);

		satD = new JCheckBox("D");
		checkBoxes.add(satD);
		//blockMap.put(satD, null);
		
		for(JCheckBox c: checkBoxes)
			c.setFocusPainted(false);
	}
	
	public static void main(String[] args){
		new GUIBlockPicker("Pick the meeting times");
	}

}
