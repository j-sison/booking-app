package com.jpmc.booking.bookingapp.util;

import com.jpmc.booking.bookingapp.util.exception.BookingException;
import com.jpmc.booking.bookingapp.vo.Booking;
import com.jpmc.booking.bookingapp.vo.Seat;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/** @version  $Revision$, $Date$ */
public final class BookingManager
{
	//~ Static fields/initializers ---------------
	/**  */
	private static ConcurrentHashMap<String, Booking> bookingList = new ConcurrentHashMap<>();

	/**  */
	private static ConcurrentHashMap<String, List<String>> usedPhoneNumber = new ConcurrentHashMap<>();
	//~ Methods ----------------------------------
	/**
	 * @param   seat
	 * @param   showNumber
	 * @param   numOfRows
	 * @param   numOfSeatsPerRow
	 * @param   cancelWindow
	 * @return
	 * @throws  BookingException
	 */
	public static Booking book(Seat seat, String phoneNumber) throws BookingException
	{
		Booking booking = null;
		// if (!usedPhoneNumber.contains(phoneNumber)
		// && ((usedPhoneNumber.get(phoneNumber) != null)
		// && !usedPhoneNumber.get(phoneNumber).contains(seat.getShow().getShowNumber())))
		// {
		booking = new Booking(seat, phoneNumber);
		bookingList.put(booking.getTicketNumber(), booking);
		//      usedPhoneNumber.get(phoneNumber).add(seat.getShow().getShowNumber());     }     else     {
		//          BookingException.throwException("Only one booking per phone # is allowed per show.");     }

		return booking;
	}
	
	/**
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
