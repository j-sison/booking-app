package com.jpmc.booking.bookingapp.ui;

import com.jpmc.booking.bookingapp.controller.ICommandProcessor;
import com.jpmc.booking.bookingapp.util.CommandProcessorFactory;
import com.jpmc.booking.bookingapp.util.exception.BookingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/** @version  $Revision$, $Date$ */
@Component
public abstract class AbstractMenu
{
	//~ Static fields/initializers ---------------
	/**  */
	protected static final Logger LOGGER = LogManager.getLogger(AbstractMenu.class);
	//~ Instance fields --------------------------
	/**  */
	protected BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));

	/**  */
	private CommandProcessorFactory commandProcessorFactory = new CommandProcessorFactory();
	//~ Methods ----------------------------------
	/**  */
	public abstract void inputCommand();
	
	/**
	 * @param   adminMenu
	 * @param   command
	 * @throws  BookingException
	 */
	protected void processCommand(AbstractMenu menu, String command) throws BookingException
	{
		ICommandProcessor processor = commandProcessorFactory.getCommandScreen(menu);
		processor.process(command);
	}
}
