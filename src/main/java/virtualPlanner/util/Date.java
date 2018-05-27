package virtualPlanner.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import virtualPlanner.reference.Days;

/**
 * Represents a date and automatically determines the day of the week from {@link Days}.
 * 
 * @author JeremiahDeGreeff
 */
public class Date implements Comparable<Date> {
	
	/**
	 * The {@code Calendar} for this {@code Date}.
	 */
	private Calendar calendar;
	/**
	 * The day of the week of this {@code Date}.
	 */
	private Days dayOfWeek;
	
	/**
	 * Creates a new {@code Date} instance for the current day.
	 */
	public Date() {
		calendar = new GregorianCalendar();
		dayOfWeek = Days.getDay(calendar.get(Calendar.DAY_OF_WEEK));
	}
	
	/**
	 * Creates a new {@code Date} instance from a day, month, and year.
	 * 
	 * @param day The day of the month. (1-indexed)
	 * @param month The month of the year. (1-indexed)
	 * @param year The year of this {@code Date}.
	 * @throws IllegalArgumentException If either {@code day} or {@code month} is out of its valid range.
	 */
	public Date(int day, int month, int year) {
		calendar = new GregorianCalendar(year, month - 1, day);
		dayOfWeek = Days.getDay(calendar.get(Calendar.DAY_OF_WEEK));
	}
	
	/**
	 * Creates a new {@code Date} instance from an sql Date String.
	 * 
	 * @param sqlDate The date String (yyyy-MM-dd).
	 */
	public Date(String sqlDate) {
		this(Integer.parseInt(sqlDate.substring(8)), Integer.parseInt(sqlDate.substring(5, 7)), Integer.parseInt(sqlDate.substring(0, 4)));
	}
	
	/**
	 * @return The day of the month.
	 */
	public int getDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * @return The month of the year.
	 */
	public int getMonth() {
		return calendar.get(Calendar.MONTH);
	}
	
	/**
	 * @return The year of this {@code Date}.
	 */
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * @return The day of the week of this {@code Date}.
	 */
	public Days getDayOfWeek() {
		return dayOfWeek;
	}
	
	/**
	 * Creates a Unix timestamp (milliseconds since Epoch) for this {@code Date} object.
	 * 
	 * @return The Unis timestamp.
	 */
	public long getTimestamp() {
		return calendar.getTimeInMillis();
	}
	
	/**
	 * Returns a long string representation of this {@code Date}.
	 * Equivalent to toStringLong().
	 */
	@Override
	public String toString() {
		return toString(DateFormat.FULL);
	}
	
	/**
	 * Generates a string representation of this {@code Date} with a particular format.
	 * 
	 * @param format A format constant from {@link DateFormat}.
	 * @return A string representation of this {@code Date} with the specified format.
	 */
	public String toString(int format) {
		return DateFormat.getDateInstance(format).format(calendar.getTime());
	}
	
	/**
	 * Generates a string representation of this {@code Date} in the format "yyyy-MM-dd" as SQL uses.
	 * 
	 * @return The String representation.
	 */
	public String toStringSQL() {
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern("yyyy-MM-dd");
		return format.format(calendar.getTime());
	}
	
	/**
	 * Indicates whether another {@code Object} is equal to this one.
	 * 
	 * The {@code Object}s are considered equal if they are both instances of {@code Date} and satisfy either of the following:
	 * <ul>
	 * <li>both references point to the same object</li>
	 * <li>both {@code Object}s have the same day, month, and year fields</li>
	 * </ul>
	 */
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Date && this.calendar.equals(((Date) o).calendar);
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	/**
	 * Compares this {@code Date} with the specified {@code Date} for order.
	 */
	@Override
	public int compareTo(Date o) {
		return this.calendar.compareTo(o.calendar);
	}
	
	/**
	 * Returns a {@code Date} instance that represents the Monday of the week of this {@code Date}.
	 * 
	 * @return The specified {@code Date} object.
	 */
	public Date getWeekStartDate() {
		Calendar clone = (Calendar)calendar.clone();
		if(clone.get(Calendar.DAY_OF_WEEK) == 1)
			clone.set(Calendar.WEEK_OF_MONTH, clone.get(Calendar.WEEK_OF_MONTH) - 1);
		clone.set(Calendar.DAY_OF_WEEK, 2);
		return new Date(clone.get(Calendar.DAY_OF_MONTH), clone.get(Calendar.MONTH) + 1, clone.get(Calendar.YEAR));
	}
	
	/**
	 * Returns a {@code Date} instance for a day a specified number of days away from this {@code Date}.
	 * 
	 * @param increment The number of days from this {@code Date} to the desired {@code Date}.
	 * @return The specified {@code Date} object.
	 */
	public Date getUpcomingDate(int increment) {
		Calendar clone = (Calendar)calendar.clone();
		clone.add(Calendar.DAY_OF_MONTH, increment);
		return new Date(clone.get(Calendar.DAY_OF_MONTH), clone.get(Calendar.MONTH) + 1, clone.get(Calendar.YEAR));
	}
	
}
