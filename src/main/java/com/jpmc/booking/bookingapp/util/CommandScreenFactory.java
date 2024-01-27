package com.jpmc.booking.bookingapp.util;

import com.jpmc.booking.bookingapp.ui.AbstractMenu;
import com.jpmc.booking.bookingapp.ui.impl.AdminMenu;
import com.jpmc.booking.bookingapp.ui.impl.BuyerMenu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/** @version  $Revision$, $Date$ */
public final class CommandScreenFactory
{
	//~ Static fields/initializers ---------------
	/**  */
	private static final Logger LOGGER = LogManager.getLogger(CommandScreenFactory.class);

	/**  */
	private static CommandScreenFactory commandScreenFactory;
	//~ Constructors -----------------------------
	/** Creates a new CommandScreenFactory object. */
	private CommandScreenFactory( ) { }
	//~ Methods ----------------------------------
	/** @return */
	public static CommandScreenFactory getInstance()
	{
		if (commandScreenFactory == null)
		{
			commandScreenFactory = new CommandScreenFactory();
		}

		return commandScreenFactory;
	}
	
	/**
	 * @param   user
	 * @return
	 */
	public AbstractMenu getCommandScreen(String user)
	{
		AbstractMenu cmd = null;

		switch (user.charAt(0))
		{
			case '1':
			{
				cmd = new AdminMenu();
				break;
			}

			case '2':
			{
				cmd = new BuyerMenu();
				break;
			}

			default:
			{
				LOGGER.error("Invalid User");
				break;
			}
		}

		return cmd;
	}
}
