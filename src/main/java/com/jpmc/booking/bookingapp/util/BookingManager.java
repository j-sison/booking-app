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
	 * @throws  SeatNotFoundException
	 */
	public static void book(Show show, String[] seatNumberArr, String phoneNumber, String ticketNumber)
		throws BookingException
	{
		ConcurrentHashMap<String, List<Booking>> bookingList = show.getBookingList();
		StringBuilder messages = new StringBuilder();
		boolean bookOK = false;
		if (!bookingList.containsKey(phoneNumber)
			|| (bookingList.containsKey(phoneNumber) && bookingList.get(phoneNumber).isEmpty()))
		{
			bookingList.put(phoneNumber, new ArrayList<Booking>());
			for (String seatNum : seatNumberArr)
			{
				Seat seat = show.getSeats().get(seatNum);

				if ((seat == null) || ((seat != null) && !seat.isAvailable()))
				{
					messages.append(seatNum + " ");
				}
				else
				{
					Booking booking = new Booking(seat, phoneNumber, ticketNumber);
					bookingList.get(phoneNumber).add(booking);
					bookOK = true;
				}
			}
		}
		else
		{
			BookingException.throwException(BookingConstant.ERROR_ONLY_ONE_BOOKING_ALLOWED);
		}

		if (!messages.toString().isEmpty())
		{
			if (bookOK)
			{
				BookingException.throwException("Booking as been created. Ticket number: " + ticketNumber
					+ "\nSome seats were not found/already booked: " + messages.toString());
			}
			else
			{
				BookingException.throwException("Seats not found/already booked." + messages.toString());
			}
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

		if ((bookingList == null) || bookingList.isEmpty())
		{
			BookingException.throwException(BookingConstant.ERROR_NO_BOOKING_FOUND);
		}

		boolean isWithinCancellationTime = bookingList.get(0).isWithinCancellationTime();
		boolean isTicketNumberCorrect = ticketNumber.equals(bookingList.get(0).getTicketNumber());

		for (Booking booking : bookingList)
		{
			if (!isTicketNumberCorrect)
			{
				BookingException.throwException(BookingConstant.ERROR_TICKET_NOT_MATCHED);
			}
			else if (!isWithinCancellationTime)
			{
				BookingException.throwException(BookingConstant.ERROR_CANCELLATION_NOT_ALLOWED);
			}
			else
			{
				booking.getSeat().freeUp();
			}
		}
		bookingList.removeAll(bookingList);
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
