package net.veritran.ard.exception;

public class ARDException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1595561244522950284L;

	/**
	 * 
	 */
	public ARDException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ARDException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ARDException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ARDException(Throwable cause) {
		super(cause);
	}

}
