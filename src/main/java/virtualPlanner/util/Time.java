package virtualPlanner.util;

/**
 * Represents a time of day with minute precision.
 * 
 * @author JeremiahDeGreeff
 */
public class Time {
	
	/**
	 * The hour represented by this {@code Time}. Must be in the range [0, 24).
	 */
	private int hour;
	/**
	 * The minute represented by this {@code Time}. Must be in the range [0, 60).
	 */
	private int minute;
	
	/**
	 * Constructs a {@code Time} at a specified hour.
	 * 
	 * @param hour The hour represented by this {@code Time}. Must be in the range [0, 24).
	 * @throws IllegalArgumentException If {@code hour} is out of its valid range.
	 */
	public Time(int hour) {
		this(hour, 0);
	}
	
	/**
	 * Constructs a {@code Time} at a specified hour and minute.
	 * 
	 * @param hour The hour represented by this {@code Time}. Must be in the range [0, 24).
	 * @param minute The minute represented by this {@code Time}. Must be in the range [0, 60).
	 * @throws IllegalArgumentException If either {@code hour} or {@code minute} is out of its valid range.
	 */
	public Time(int hour, int minute) {
		if(hour < 0 || hour >= 24)
			throw new IllegalArgumentException("Argument hour has illegal value: " + hour + ". It must be in the range [0, 24).");
		if(minute < 0 || minute >= 60)
			throw new IllegalArgumentException("Argument minute has illegal value: " + minute + ". It must be in the range [0, 60).");
		
		this.hour = hour;
		this.minute = minute;
	}
	
	/**
	 * @return The hour represented by this {@code Time}.
	 */
	public int getHour() {
		return hour;
	}
	
	/**
	 * @return The minute represented by this {@code Time}.
	 */
	public int getMinute() {
		return minute;
	}
	
}
