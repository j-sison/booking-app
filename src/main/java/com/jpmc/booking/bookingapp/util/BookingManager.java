package com.jpmc.booking.bookingapp.util;

import com.jpmc.booking.bookingapp.util.exception.BookingException;
import com.jpmc.booking.bookingapp.vo.Booking;
import com.jpmc.booking.bookingapp.vo.Seat;

import java.util.concurrent.ConcurrentHashMap;


/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public final class BookingManager
{
	//~ Static fields/initializers ---------------
	/**  */
	private static ConcurrentHashMap<String, Booking> bookingList = new ConcurrentHashMap<>();
	//~ Methods ----------------------------------
	/**
	 * DOCUMENT ME!
	 *
	 * @param   seat
	 * @param   showNumber
	 * @param   numOfRows
	 * @param   numOfSeatsPerRow
	 * @param   cancelWindow
	 * @return
	 */
	public static Booking book(Seat seat, String phoneNumber)
	{
		Booking booking = new Booking(seat, phoneNumber);
		if (!bookingList.contains(booking.getTicketNumber()))
		{
			bookingList.put(booking.getTicketNumber(), booking);
		}
		else
		{
			// TODO unable to setup show
		}

		return booking;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param   showNumber
	 * @return
	 * @throws  BookingException
	 */
	public static Booking retrieveBooking(String ticketNumber, String phoneNumber) throws BookingException
	{
		Booking booking = bookingList.get(ticketNumber);

		if (!booking.getPhoneNumber().equals(phoneNumber))
		{
			BookingException.throwException("Error: Ticket and Phone number has no match.");
		}

		return booking;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param   ticketNumber
	 * @param   phoneNumber
	 * @throws  BookingException
	 */
	public static void cancelBooking(String ticketNumber, String phoneNumber) throws BookingException
	{
		Booking booking = retrieveBooking(ticketNumber, phoneNumber);

		if (booking.isWithinCancellationTime())
		{
			bookingList.remove(booking.getTicketNumber());
			booking.getSeat().freeUp();
		}
		else
		{
			BookingException.throwException("Error: Cancellation is no longer allowed.");
		}
	}
}
