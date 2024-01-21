package com.jpmc.booking.bookingapp.ui;

import com.jpmc.booking.bookingapp.util.CommandValidator;
import com.jpmc.booking.bookingapp.util.exception.BookingException;

import java.io.IOException;


/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class AdminMenu extends AbstractMenu
{
	//~ Methods ----------------------------------
	/**  */
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
				"*         Setup  <Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes>");
			System.out.println("*         View <Show Number>");
			System.out.println("*         Quit");
			System.out.println(
				"=======================================================================================================");
			try
			{
				System.out.println("Please enter your command:");
				String cmd = reader.readLine();
				CommandValidator.validate(this, cmd);
				processCommand(cmd);
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
