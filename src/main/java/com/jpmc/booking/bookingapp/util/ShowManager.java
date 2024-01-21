package com.jpmc.booking.bookingapp.util;

import com.jpmc.booking.bookingapp.vo.Show;

import java.util.concurrent.ConcurrentHashMap;


/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public final class ShowManager
{
	//~ Static fields/initializers ---------------
	/**  */
	private static ConcurrentHashMap<String, Show> shows = new ConcurrentHashMap<>();
	//~ Methods ----------------------------------
	/**
	 * DOCUMENT ME!
	 *
	 * @param  showNumber
	 * @param  numOfRows
	 * @param  numOfSeatsPerRow
	 * @param  cancelWindow
	 */
	public static void setup(String showNumber, int numOfRows, int numOfSeatsPerRow, int cancelWindow)
	{
		if (!shows.contains(showNumber))
		{
			shows.put(showNumber, new Show(showNumber, numOfRows, numOfSeatsPerRow, cancelWindow));
		}
		else
		{
			// TODO unable to setup show
		}
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param   showNumber
	 * @return
	 */
	public static Show retrieveShow(String showNumber)
	{
		return shows.get(showNumber);
	}
}
