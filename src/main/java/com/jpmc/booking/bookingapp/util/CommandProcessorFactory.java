package com.jpmc.booking.bookingapp.util;

import com.jpmc.booking.bookingapp.controller.ICommandProcessor;
import com.jpmc.booking.bookingapp.controller.impl.AdminCommandProcessor;
import com.jpmc.booking.bookingapp.controller.impl.BuyerCommandProcessor;
import com.jpmc.booking.bookingapp.ui.AbstractMenu;
import com.jpmc.booking.bookingapp.ui.impl.AdminMenu;
import com.jpmc.booking.bookingapp.ui.impl.BuyerMenu;


/** @version  $Revision$, $Date$ */
public class CommandProcessorFactory
{
	//~ Methods ----------------------------------
	/**
	 * @param   menu
	 * @param   command
	 * @return
	 */
	public ICommandProcessor getCommandScreen(AbstractMenu menu)
	{
		ICommandProcessor processor = null;

		if (menu instanceof AdminMenu)
		{
			processor = new AdminCommandProcessor();
		}
		else if (menu instanceof BuyerMenu)
		{
			processor = new BuyerCommandProcessor();
		}

		return processor;
	}
}
