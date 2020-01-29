package com.github.jwong12.exceptions;

/**
 * Application Exception Class
 */
@SuppressWarnings("serial")
public class ApplicationException extends Exception {
	/**
	 * Default constructor
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * ApplicationException constructor
	 * 
	 * @param message
	 *            the String to set
	 * @param cause
	 *            the Throwable to set
	 * @param enableSuppression
	 *            the boolean to set
	 * @param writableStackTrace
	 *            the boolean to set
	 */
	public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * ApplicationException constructor
	 * 
	 * @param message
	 *            the String to set
	 * @param cause
	 *            the Throwable to set
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * ApplicationException constructor
	 * 
	 * @param message
	 *            the String to set
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * ApplicationException constructor
	 * 
	 * @param cause
	 *            the Throwable to set
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}
}
