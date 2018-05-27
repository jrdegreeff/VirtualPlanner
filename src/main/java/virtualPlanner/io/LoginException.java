package virtualPlanner.io;

@SuppressWarnings("serial")
public class LoginException extends Exception {
	
	/**
	 * The error code for an SQL error.
	 */
	public static final int SQL_ERROR = -1;
	/**
	 * The error code for a username which does not exist.
	 */
	public static final int USER_NOT_REGISTERED = -2;
	/**
	 * The error code for an incorrect password.
	 */
	public static final int INVALID_PASSWORD = -3;
	
	/**
	 * The error code for this {@code LoginException}.
	 */
	private final int errorCode;
	
	/**
	 * Instantiates an {@code LoginException} with an error code.
	 * 
	 * @param errorCode The error code of the {@code LoginException}.
	 */
	public LoginException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	/**
	 * @return The error code for this {@code LoginException}.
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
}
