package com.jpmc.booking.bookingapp.controller;

import com.jpmc.booking.bookingapp.util.BookingManager;
import com.jpmc.booking.bookingapp.util.ShowManager;
import com.jpmc.booking.bookingapp.util.exception.BookingException;
import com.jpmc.booking.bookingapp.vo.Booking;
import com.jpmc.booking.bookingapp.vo.Seat;
import com.jpmc.booking.bookingapp.vo.Show;

import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class BuyerCommandProcessor
{
	//~ Methods ----------------------------------
	/**
	 * DOCUMENT ME!
	 *
	 * @param   command
	 * @throws  BookingException
	 */
	public static void process(String command) throws BookingException
	{
		String[] params = command.split(" ");

		if ("availability".compareToIgnoreCase(params[0]) == 0)
		{
			availability(params);
		}
		else if ("book".compareToIgnoreCase(params[0]) == 0)
		{
			book(params);
		}
		else if ("cancel".compareToIgnoreCase(params[0]) == 0)
		{
			cancel(params);
		}
		else if ("quit".compareToIgnoreCase(params[0]) == 0)
		{
			BookingException.throwException("Return to main menu", true);
		}
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param  params
	 */
	private static void availability(String[] params)
	{
		String showNumber = params[1];

		Show show = ShowManager.retrieveShow(showNumber);

		if (show != null)
		{
			List<Seat> available = show.getAvailableSeats();

			System.out.println("List of available seats under show: " + show.getShowNumber());
			for (Seat seat : available)
			{
				System.out.println(seat.getSeatInfo());
			}
		}
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param  params
	 */
	private static void book(String[] params)
	{
		String showNumber = params[1];
		String phoneNumber = params[2];
		String seatNumbers = params[3];
		String[] seatNumberArr = seatNumbers.split(",");

		StringBuilder messages = new StringBuilder();

		Show show = ShowManager.retrieveShow(showNumber);

		if (show != null)
		{
			for (String seatNum : seatNumberArr)
			{
				Seat seat = show.getSeats().get(seatNum);

				if (seat == null)
				{
					messages.append("Seat not found: " + seatNum);
				}
				else
				{
					Booking booking = BookingManager.book(seat, phoneNumber);
					System.out.println("Booking as been created. Ticket number: " + booking.getTicketNumber());
				}
			}
		}
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param   params
	 * @throws  BookingException
	 */
	private static void cancel(String[] params) throws BookingException
	{
		String ticketNumber = params[1];
		String phoneNumber = params[2];

		BookingManager.cancelBooking(ticketNumber, phoneNumber);
		System.out.println("Ticket has been cancelled.");
	}
}
