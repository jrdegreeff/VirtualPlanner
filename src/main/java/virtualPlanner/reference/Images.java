package virtualPlanner.reference;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Stores static references to images used by the project.
 * 
 * @author JeremiahDeGreeff
 */
public class Images {
	
	/**
	 * The previous arrow image.
	 */
	private static Image previous;
	/**
	 * The next arrow image.
	 */
	private static Image next;
	/**
	 * The gradebook image.
	 */
	private static Image gradebook;
	/**
	 * The settings image.
	 */
	private static Image settings;
	
	/**
	 * Loads all the images.
	 */
	public static void loadImages() {
		try {
			previous = ImageIO.read(Images.class.getResource(Paths.IMAGE_PREVIOUS));
			next = ImageIO.read(Images.class.getResource(Paths.IMAGE_NEXT));
			gradebook = ImageIO.read(Images.class.getResource(Paths.IMAGE_GRADEBOOK));
			settings = ImageIO.read(Images.class.getResource(Paths.IMAGE_SETTINGS));
			System.out.println("Images Loaded.");
		} catch (IOException e) {
			System.out.println("Images Load Failure.");
			e.printStackTrace();
		}
	}
	
	/**
	 * @return The previous arrow image.
	 */
	public static Image getPrevious() {
		return previous;
	}
	
	/**
	 * @return The next arrow image.
	 */
	public static Image getNext() {
		return next;
	}
	
	/**
	 * @return The gradebook image.
	 */
	public static Image getGradebook() {
		return gradebook;
	}
	
	/**
	 * @return The settings image.
	 */
	public static Image getSettings() {
		return settings;
	}
	
}
