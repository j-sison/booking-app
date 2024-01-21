package com.jpmc.booking.bookingapp.util;

import com.jpmc.booking.bookingapp.ui.AbstractMenu;
import com.jpmc.booking.bookingapp.ui.AdminMenu;
import com.jpmc.booking.bookingapp.ui.BuyerMenu;
import com.jpmc.booking.bookingapp.util.exception.BookingException;


/** @version  $Revision$, $Date$ */
public final class CommandValidator
{
	//~ Methods ----------------------------------
	/**
	 * @param   cmdScreen
	 * @param   cmd
	 * @throws  BookingException
	 */
	public static void validate(AbstractMenu cmdScreen, String cmd) throws BookingException
	{
		if ((cmd == null) || cmd.isEmpty())
		{
			BookingException.throwException(BookingConstant.ERROR_NO_COMMAND);
		}

		String[] params = cmd.split(" ");

		if (cmdScreen instanceof AdminMenu)
		{
			validateAdminCommand(params);
		}
		else if (cmdScreen instanceof BuyerMenu)
		{
			validateBuyerCommand(params);
		}
	}
	
	/**
	 * @param   params
	 * @throws  BookingException
	 */
	private static void validateAdminCommand(String[] params) throws BookingException
	{
		if (!org.apache.commons.lang3.StringUtils.containsIgnoreCase("setup,view,quit", params[0]))
		{
			BookingException.throwException(BookingConstant.ERROR_INVALID_COMMAND);
		}

		if (("setup".equalsIgnoreCase(params[0]) && (params.length != 5))
			|| ("view".equalsIgnoreCase(params[0]) && (params.length != 2)))
		{
			BookingException.throwException(BookingConstant.ERROR_INCORRECT_NUMBER_OF_PARAMETERS);
		}
	}
	
	/**
	 * @param   params
	 * @throws  BookingException
	 */
	private static void validateBuyerCommand(String[] params) throws BookingException
	{
		if (!org.apache.commons.lang3.StringUtils.containsIgnoreCase("availability,book,cancel,quit", params[0]))
		{
			BookingException.throwException(BookingConstant.ERROR_INVALID_COMMAND);
		}

		if (("availability".equalsIgnoreCase(params[0]) && (params.length != 2))
			|| ("book".equalsIgnoreCase(params[0]) && (params.length != 4))
			|| ("cancel".equalsIgnoreCase(params[0]) && (params.length != 3)))
		{
			BookingException.throwException(BookingConstant.ERROR_INCORRECT_NUMBER_OF_PARAMETERS);
		}
	}
}
