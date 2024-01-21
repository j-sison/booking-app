package com.jpmc.booking.bookingapp.util;

import com.jpmc.booking.bookingapp.ui.AdminMenu;
import com.jpmc.booking.bookingapp.ui.BuyerMenu;
import com.jpmc.booking.bookingapp.util.exception.BookingException;


/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public final class CommandValidator
{
	//~ Methods ----------------------------------
	/**
	 * DOCUMENT ME!
	 *
	 * @param   cmdScreen
	 * @param   cmd
	 * @throws  BookingException
	 */
	public static void validate(Object cmdScreen, String cmd) throws BookingException
	{
		if ((cmd == null) || cmd.isEmpty())
		{
			BookingException.throwException("Error: No command");
		}

		String[] params = cmd.split(" ");

		if (cmdScreen instanceof AdminMenu)
		{
			if (!org.apache.commons.lang3.StringUtils.containsIgnoreCase("setup,view,quit", params[0]))
			{
				BookingException.throwException("Error: Invalid command");
			}
		}
		else if (cmdScreen instanceof BuyerMenu)
		{
			if (!org.apache.commons.lang3.StringUtils.containsIgnoreCase("availability,book,cancel,quit", params[0]))
			{
				BookingException.throwException("Error: Invalid command");
			}
		}
	}
}
