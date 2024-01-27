package com.jpmc.booking.bookingapp.ui.impl;

import com.jpmc.booking.bookingapp.ui.AbstractMenu;
import com.jpmc.booking.bookingapp.util.CommandValidator;
import com.jpmc.booking.bookingapp.util.exception.BookingException;

import java.io.IOException;


/** @version  $Revision$, $Date$ */
public class BuyerMenu extends AbstractMenu
{
	//~ Methods ----------------------------------
	/** @see  com.jpmc.booking.bookingapp.ui.AbstractMenu#inputCommand() */
	@Override
	public void inputCommand()
	{
		boolean quit = false;

		while (!quit)
		{
			System.out.println(
				"=======================================================================================================");
			System.out.println("Available commands:");
			System.out.println(
				"*\t\tAvailability <Show Number>");
			System.out.println("*\t\tBook <Show Number> <Phone#> <Comma separated list of seats>");
			System.out.println("*\t\tCancel <Ticket#> <Phone#>");
			System.out.println("*\t\tQuit");
			System.out.println(
				"=======================================================================================================");
			try
			{
				System.out.println("Please enter your command:");
				String cmd = reader.readLine();
				CommandValidator.validate(this, cmd);
				processCommand(this, cmd);
			}
			catch (BookingException e)
			{
				quit = e.isQuit();
				System.err.println(e.getMessage());
			}
			catch (IOException e)
			{
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
