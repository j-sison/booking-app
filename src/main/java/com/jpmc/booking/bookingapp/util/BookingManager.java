package com.jpmc.booking.bookingapp.util;

import com.jpmc.booking.bookingapp.util.exception.BookingException;
import com.jpmc.booking.bookingapp.vo.Booking;
import com.jpmc.booking.bookingapp.vo.Seat;
import com.jpmc.booking.bookingapp.vo.Show;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/** @version  $Revision$, $Date$ */
public final class BookingManager
{
	//~ Static fields/initializers ---------------
	/**  */
	private static int ticketCounter;
	//~ Methods ----------------------------------
	/**
	 * @param   show
	 * @param   seatNumberArr
	 * @param   seat
	 * @param   ticketNumber
	 * @param   showNumber
	 * @param   numOfRows
	 * @param   numOfSeatsPerRow
	 * @param   cancelWindow
	 * @return
	 * @throws  BookingException
	 */
	public static void book(Show show, String[] seatNumberArr, String phoneNumber, String ticketNumber)
		throws BookingException
	{
		ConcurrentHashMap<String, List<Booking>> bookingList = show.getBookingList();
		StringBuilder messages = new StringBuilder();
		if (!bookingList.contains(phoneNumber))
		{
			bookingList.put(phoneNumber, new ArrayList<Booking>());
			for (String seatNum : seatNumberArr)
			{
				Seat seat = show.getSeats().get(seatNum);

				if (seat == null)
				{
					messages.append("Seat not found: " + seatNum);
				}
				else
				{
					Booking booking = new Booking(seat, phoneNumber, ticketNumber);
					bookingList.get(phoneNumber).add(booking);
					System.out.println("Booking as been created. Ticket number: " + booking.getTicketNumber());
				}
			}
		}
		else
		{
			BookingException.throwException("Only one booking per phone # is allowed per show.");
		}
	}
	
	/**
	 * @param   showNumber
	 * @param   showNumber
	 * @return
	 * @throws  BookingException
	 */
	public static List<Booking> retrieveBooking(String showNumber, String ticketNumber, String phoneNumber)
		throws BookingException
	{
		Show show = ShowManager.retrieveShow(showNumber);

		ConcurrentHashMap<String, List<Booking>> bookingList = show.getBookingList();
		List<Booking> booking = bookingList.get(phoneNumber);

		return booking;
	}
	
	/**
	 * @param   showNumber
	 * @param   ticketNumber
	 * @param   phoneNumber
	 * @throws  BookingException
	 */
	public static void cancelBooking(String showNumber, String ticketNumber, String phoneNumber) throws BookingException
	{
		List<Booking> bookingList = retrieveBooking(showNumber, ticketNumber, phoneNumber);

		for (Booking booking : bookingList)
		{
			if (booking.isWithinCancellationTime())
			{
				bookingList.remove(booking);
				booking.getSeat().freeUp();
			}
			else
			{
				BookingException.throwException("Error: Cancellation is no longer allowed.");
			}
		}
	}
	
	/**
	 * @param   showNumber
	 * @return
	 */
	public static String generateTicket(String showNumber)
	{
		return showNumber + "-" + StringUtils.leftPad(String.valueOf(++ticketCounter), 8, "0");
	}
}
