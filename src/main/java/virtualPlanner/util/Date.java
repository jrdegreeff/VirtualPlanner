package virtualPlanner.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import virtualPlanner.reference.Days;

/**
 * Represents a date and automatically determines the day of the week from {@link Days}.
 * 
 * @author JeremiahDeGreeff
 */
public class Date {
	
	/**
	 * The day of the month. Must be in the range [1, 31].
	 */
	private int day;
	/**
	 * The month of the year. Must be in the range [1, 12].
	 */
	private int month;
	/**
	 * The year of this {@code Date}.
	 */
	private int year;
	/**
	 * The day of the week of this {@code Date}.
	 */
	private Days dayOfWeek;
	
	/**
	 * @param day The day of the month. Must be in the range [1, 31].
	 * @param month The month of the year. Must be in the range [1, 12].
	 * @param year The year of this {@code Date}.
	 * @throws IllegalArgumentException If either {@code day} or {@code month} is out of its valid range.
	 */
	public Date(int day, int month, int year) {
		if(day < 1 || day > 31)
			throw new IllegalArgumentException("Argument day has illegal value: " + day + ". It must be in the range [1, 31].");
		if(month < 1 || month > 12)
			throw new IllegalArgumentException("Argument month has illegal value: " + month + ". It must be in the range [1, 12].");
		
		this.day = day;
		this.month = month;
		this.year = year;
		dayOfWeek = Days.getDay(new GregorianCalendar(year, month - 1, day).get(Calendar.DAY_OF_WEEK));
	}
	
	/**
	 * @return The day of the month.
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * @return The month of the year.
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * @return The year of this {@code Date}.
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * @return The day of the week of this {@code Date}.
	 */
	public Days getDayOfWeek() {
		return dayOfWeek;
	}
	
	/**
	 * Indicates whether another Object is equal to this one.
	 * 
	 * The objects are considered equal if they are both instances of {@code Date} and satisfy either of the following:
	 * <ul>
	 * <li>both references point to the same object</li>
	 * <li>both objects have the same day, month, and year fields</li>
	 * </ul>
	 */
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Date && (this == o || this.day == ((Date) o).day && this.month == ((Date) o).month && this.year == ((Date) o).year);
	}
	
	@Override
	public int hashCode() {
		return (int) new GregorianCalendar(year, month - 1, day).getTimeInMillis();
	}
}
