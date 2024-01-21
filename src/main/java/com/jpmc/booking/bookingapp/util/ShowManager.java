package com.jpmc.booking.bookingapp.util;

import com.jpmc.booking.bookingapp.util.exception.BookingException;
import com.jpmc.booking.bookingapp.vo.Show;

import java.util.concurrent.ConcurrentHashMap;


/** @version  $Revision$, $Date$ */
public final class ShowManager
{
	//~ Static fields/initializers ---------------
	/**  */
	private static ConcurrentHashMap<String, Show> shows = new ConcurrentHashMap<>();
	//~ Methods ----------------------------------
	/**
	 * @param   showNumber
	 * @param   numOfRows
	 * @param   numOfSeatsPerRow
	 * @param   cancelWindow
	 * @throws  BookingException
	 */
	public static void setup(String showNumber, int numOfRows, int numOfSeatsPerRow, int cancelWindow)
		throws BookingException
	{
		validateMaxSeats(numOfRows, numOfSeatsPerRow);
		if (!shows.contains(showNumber))
		{
			shows.put(showNumber, new Show(showNumber, numOfRows, numOfSeatsPerRow, cancelWindow));
		}
		else
		{
			BookingException.throwException(BookingConstant.ERROR_SHOW_ALREADY_EXISTS);
		}
	}
	
	/**
	 * @param   numOfRows
	 * @param   numOfSeatsPerRow
	 * @throws  BookingException
	 */
	private static void validateMaxSeats(int numOfRows, int numOfSeatsPerRow) throws BookingException
	{
		if (numOfRows > BookingConstant.MAX_SEAT_ROW)
		{
			BookingException.throwException("Number of rows exceeds the maximum allowed.");
		}
		else if (numOfSeatsPerRow > BookingConstant.MAX_SEAT_PER_ROW)
		{
			BookingException.throwException("Number of seats per row exceeds the maximum allowed.");
		}
	}
	
	/**
	 * @param   showNumber
	 * @return
	 */
	public static Show retrieveShow(String showNumber)
	{
		return shows.get(showNumber);
	}
}
