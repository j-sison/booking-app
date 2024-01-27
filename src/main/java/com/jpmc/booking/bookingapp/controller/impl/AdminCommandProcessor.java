package com.jpmc.booking.bookingapp.controller.impl;

import com.jpmc.booking.bookingapp.controller.ICommandProcessor;
import com.jpmc.booking.bookingapp.util.BookingConstant;
import com.jpmc.booking.bookingapp.util.ShowManager;
import com.jpmc.booking.bookingapp.util.exception.BookingException;
import com.jpmc.booking.bookingapp.vo.Seat;
import com.jpmc.booking.bookingapp.vo.Show;

import org.springframework.stereotype.Service;

import java.util.List;


/** @version  $Revision$, $Date$ */
@Service
public class AdminCommandProcessor implements ICommandProcessor
{
	//~ Methods ----------------------------------
	/**
	 * @param   command
	 * @throws  BookingException
	 */
	public void process(String command) throws BookingException
	{
		String[] params = command.split(" ");

		if ("setup".compareToIgnoreCase(params[0]) == 0)
		{
			setup(params);
		}
		else if ("view".compareToIgnoreCase(params[0]) == 0)
		{
			view(params);
		}
		else if ("quit".compareToIgnoreCase(params[0]) == 0)
		{
			BookingException.throwException("Return to main menu", true);
		}
	}
	
	/**
	 * @param   params
	 * @throws  BookingException
	 */
	private void setup(String[] params) throws BookingException
	{
		String showNumber = params[1];
		int numOfRows = Integer.parseInt(params[2]);
		int numOfSeatsPerRow = Integer.parseInt(params[3]);
		int cancelWindow = Integer.parseInt(params[4]);

		ShowManager.getInstance().setup(showNumber, numOfRows, numOfSeatsPerRow, cancelWindow);
		System.out.println("Show has been created.");
	}
	
	/**
	 * @param   params
	 * @throws  BookingException
	 */
	private void view(String[] params) throws BookingException
	{
		String showNumber = params[1];

		Show show = ShowManager.getInstance().retrieveShow(showNumber);

		if (show != null)
		{
			List<Seat> bookedSeats = show.getBookedSeats();

			if (bookedSeats.isEmpty())
			{
				System.out.println(BookingConstant.ERROR_THERE_ARE_NO_BOOKED_SEATS);
			}
			else
			{
				System.out.println("List of booked seats under show: " + show.getShowNumber());
			}

			for (Seat seat : bookedSeats)
			{
				System.out.println(seat.getBuyerInfo());
			}
		}
		else
		{
			BookingException.throwException(BookingConstant.ERROR_SHOW_NOT_FOUND);
		}
	}
}
