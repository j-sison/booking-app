package com.jpmc.booking.bookingapp.vo;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class Seat
{
	//~ Instance fields --------------------------
	/**  */
	private Booking booking;

	/**  */
	private boolean isAvailable;

	/**  */
	private String seatNumber;

	/**  */
	private Show show;
	//~ Constructors -----------------------------
	/**
	 * Creates a new Seat object.
	 *
	 * @param  show
	 * @param  seatNumber
	 */
	public Seat(Show show, String seatNumber)
	{
		this.show = show;
		this.seatNumber = seatNumber;
		this.isAvailable = true;
	}
	//~ Methods ----------------------------------
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public Show getShow()
	{
		return show;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public Booking getBooking()
	{
		return booking;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param  booking
	 */
	public void setBooking(Booking booking)
	{
		this.booking = booking;
		if (booking != null)
		{
			isAvailable = false;
		}
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public String getSeatNumber()
	{
		return seatNumber;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public boolean isAvailable()
	{
		return isAvailable;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public String getSeatInfo()
	{
		return seatNumber;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public String getBuyerInfo()
	{
		return (booking == null) ? "" : (booking.getPhoneNumber() + " " + booking.getTicketNumber() + " " + seatNumber);
	}
	
	/** DOCUMENT ME! */
	public void freeUp()
	{
		this.isAvailable = true;
		setBooking(null);
	}
	
	/** @see  java.lang.Object#toString() */
	public String toString()
	{
		return seatNumber;
	}
}
