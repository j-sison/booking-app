package com.jpmc.booking.bookingapp.vo;

import java.time.temporal.ChronoUnit;

import java.util.Calendar;


/** @version  $Revision$, $Date$ */
public class Booking
{
	//~ Instance fields --------------------------
	/**  */
	private Calendar bookTime;

	/**  */
	private String phoneNumber;

	/**  */
	private Seat seat;

	/**  */
	private String ticketNumber;
	//~ Constructors -----------------------------
	/**
	 * Creates a new Booking object.
	 *
	 * @param  seat
	 * @param  phoneNumber
	 * @param  ticketNumber
	 */
	public Booking(Seat seat, String phoneNumber, String ticketNumber)
	{
		this.phoneNumber = phoneNumber;
		this.ticketNumber = ticketNumber;
		this.bookTime = Calendar.getInstance();
		this.seat = seat;
		seat.setBooking(this);
	}
	//~ Methods ----------------------------------
	/** @return */
	public Calendar getBookTime()
	{
		return bookTime;
	}
	

	/**
	 * DOCUMENT ME!
	 *
	 * @param  bookTime
	 */
	public void setBookTime(Calendar bookTime)
	{
		this.bookTime = bookTime;
	}
	
	/** @return */
	public Seat getSeat()
	{
		return seat;
	}
	
	/** @return */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	/** @return */
	public String getTicketNumber()
	{
		return ticketNumber;
	}
	
	/** @return */
	public boolean isWithinCancellationTime()
	{
		long minutes = ChronoUnit.MINUTES.between(bookTime.toInstant(), Calendar.getInstance().toInstant());

		return seat.getShow().getCancelWindow() > minutes;
	}
}
