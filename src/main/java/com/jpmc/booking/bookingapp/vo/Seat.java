package com.jpmc.booking.bookingapp.vo;

/** @version  $Revision$, $Date$ */
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
	/** @return */
	public Show getShow()
	{
		return show;
	}
	
	/** @return */
	public Booking getBooking()
	{
		return booking;
	}
	
	/** @param  booking */
	public void setBooking(Booking booking)
	{
		this.booking = booking;
		if (booking != null)
		{
			isAvailable = false;
		}
	}
	
	/** @return */
	public String getSeatNumber()
	{
		return seatNumber;
	}
	
	/** @return */
	public boolean isAvailable()
	{
		return isAvailable;
	}
	
	/** @return */
	public String getBuyerInfo()
	{
		return (booking == null) ? "" : (booking.getPhoneNumber() + " " + booking.getTicketNumber() + " " + seatNumber);
	}
	
	/**  */
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
