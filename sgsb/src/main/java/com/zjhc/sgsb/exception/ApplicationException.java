package com.zjhc.sgsb.exception;
/**
 * @author rtm 2008-5-8
 */
public class ApplicationException extends RuntimeException{
	public ApplicationException(String e) {
		super(e);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>).
	 * This constructor is useful for runtime exceptions that are little more
	 * than wrappers for other throwables.
	 *
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 * @since 1.4
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}
}
