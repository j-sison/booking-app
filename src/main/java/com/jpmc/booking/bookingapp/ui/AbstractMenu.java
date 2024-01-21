package com.jpmc.booking.bookingapp.ui;

import com.jpmc.booking.bookingapp.controller.AdminCommandProcessor;
import com.jpmc.booking.bookingapp.controller.BuyerCommandProcessor;
import com.jpmc.booking.bookingapp.util.exception.BookingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public abstract class AbstractMenu
{
	//~ Static fields/initializers ---------------
	/**  */
	static final Logger LOGGER = LogManager.getLogger(AbstractMenu.class);
	//~ Instance fields --------------------------
	/**  */
	BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));
	//~ Methods ----------------------------------
	/** DOCUMENT ME! */
	public abstract void inputCommand();
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param   command
	 * @throws  BookingException
	 */
	void processCommand(String command) throws BookingException
	{
		if (this instanceof AdminMenu)
		{
			AdminCommandProcessor.process(command);
		}
		else if (this instanceof BuyerMenu)
		{
			BuyerCommandProcessor.process(command);
		}
	}
}
