package com.jpmc.booking.bookingapp.vo;

import org.apache.commons.lang3.StringUtils;

import java.time.temporal.ChronoUnit;

import java.util.Calendar;


/** @version  $Revision$, $Date$ */
public class Booking
{
	//~ Static fields/initializers ---------------
	/**  */
	private static int ticketCounter;
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
	 */
	public Booking(Seat seat, String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
		this.ticketNumber = "TICKET#" + StringUtils.leftPad(String.valueOf(++Booking.ticketCounter), 8, "0");
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
