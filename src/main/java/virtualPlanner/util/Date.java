package virtualPlanner.util;

import java.text.DateFormat;
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
	 * Returns a String representation of this {@code Date}.
	 */
	@Override
	public String toString() {
		return dayOfWeek.getName() + " " + DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
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
