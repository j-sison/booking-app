package com.jpmc.booking.bookingapp.controller;

import com.jpmc.booking.bookingapp.util.BookingConstant;
import com.jpmc.booking.bookingapp.util.BookingManager;
import com.jpmc.booking.bookingapp.util.ShowManager;
import com.jpmc.booking.bookingapp.util.exception.BookingException;
import com.jpmc.booking.bookingapp.vo.Seat;
import com.jpmc.booking.bookingapp.vo.Show;

import java.util.List;


/** @version  $Revision$, $Date$ */
public final class BuyerCommandProcessor
{
	//~ Constructors -----------------------------
	/** Creates a new BuyerCommandProcessor object. */
	private BuyerCommandProcessor( ) { }
	//~ Methods ----------------------------------
	/**
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
			BookingException.throwException(BookingConstant.ERROR_RETURN_TO_MAIN_MENU, true);
		}
	}
	
	/**
	 * @param   params
	 * @throws  BookingException
	 */
	private static void availability(String[] params) throws BookingException
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
		else
		{
			BookingException.throwException(BookingConstant.ERROR_SHOW_NOT_FOUND);
		}
	}
	
	/**
	 * @param   params
	 * @throws  BookingException
	 */
	private static void book(String[] params) throws BookingException
	{
		String showNumber = params[1];
		String phoneNumber = params[2];
		String seatNumbers = params[3];
		String[] seatNumberArr = seatNumbers.split(",");

		Show show = ShowManager.retrieveShow(showNumber);

		if (show != null)
		{
			String ticketNumber = BookingManager.generateTicket(showNumber);
			BookingManager.book(show, seatNumberArr, phoneNumber, ticketNumber);
			System.out.println("Booking as been created. Ticket number: " + ticketNumber);
		}
	}
	
	/**
	 * @param   params
	 * @throws  BookingException
	 */
	private static void cancel(String[] params) throws BookingException
	{
		String ticketNumber = params[1];
		String phoneNumber = params[2];

		String showNumber = parseShowNumber(ticketNumber);

		BookingManager.cancelBooking(showNumber, ticketNumber, phoneNumber);
		System.out.println("Ticket has been cancelled.");
	}
	
	/**
	 * @param   ticketNumber
	 * @return
	 */
	private static String parseShowNumber(String ticketNumber)
	{
		String[] ticketArr = ticketNumber.split("-");

		return ticketArr[0];
	}
}
