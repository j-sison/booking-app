package com.jpmc.booking.bookingapp.util.exception;

/** @version  $Revision$, $Date$ */
public class BookingException extends Exception
{
	//~ Static fields/initializers ---------------
	/**  */
	private static final long serialVersionUID = 1L;
	//~ Instance fields --------------------------
	/**  */
	private final boolean isQuit;
	//~ Constructors -----------------------------
	/**
	 * Creates a new BookingException object.
	 *
	 * @param  message
	 * @param  isQuit
	 */
	public BookingException(String message, boolean isQuit)
	{
		super(message);
		this.isQuit = isQuit;
	}
	//~ Methods ----------------------------------
	/** @return */
	public boolean isQuit()
	{
		return isQuit;
	}
	
	/**
	 * @param   message
	 * @throws  BookingException
	 */
	public static void throwException(String message) throws BookingException
	{
		throw new BookingException(message, false);
	}
	
	/**
	 * @param   message
	 * @param   isQuit
	 * @throws  BookingException
	 */
	public static void throwException(String message, boolean isQuit) throws BookingException
	{
		throw new BookingException(message, isQuit);
	}
}
