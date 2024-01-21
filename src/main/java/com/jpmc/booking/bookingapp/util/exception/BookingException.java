package com.jpmc.booking.bookingapp.util.exception;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class BookingException extends Exception
{
	//~ Static fields/initializers ---------------
	/**  */
	private static final long serialVersionUID = 1L;
	//~ Instance fields --------------------------
	/**  */
	private boolean isQuit;
	//~ Constructors -----------------------------
	/**
	 * Creates a new BookingException object.
	 *
	 * @param  message
	 */
	public BookingException(String message)
	{
		super(message);
	}

	/**
	 * Creates a new BookingException object.
	 *
	 * @param  message
	 * @param  isQuit
	 */
	public BookingException(String message, boolean isQuit)
	{
		this(message);
		setQuit(isQuit);
	}
	//~ Methods ----------------------------------
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public boolean isQuit()
	{
		return isQuit;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param  isQuit
	 */
	public void setQuit(boolean isQuit)
	{
		this.isQuit = isQuit;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param   message
	 * @throws  BookingException
	 */
	public static void throwException(String message) throws BookingException
	{
		throw new BookingException(message);
	}
	

	/**
	 * DOCUMENT ME!
	 *
	 * @param   message
	 * @param   isQuit
	 * @throws  BookingException
	 */
	public static void throwException(String message, boolean isQuit) throws BookingException
	{
		throw new BookingException(message, isQuit);
	}
}
