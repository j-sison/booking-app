package com.jpmc.booking.bookingapp.util;

import com.jpmc.booking.bookingapp.ui.AbstractMenu;
import com.jpmc.booking.bookingapp.ui.AdminMenu;
import com.jpmc.booking.bookingapp.ui.BuyerMenu;


/** @version  $Revision$, $Date$ */
public final class CommandScreenFactory
{
	//~ Constructors -----------------------------
	/** Creates a new CommandScreenFactory object. */
	private CommandScreenFactory( ) { }
	//~ Methods ----------------------------------
	/**
	 * @param   user
	 * @return
	 */
	public static AbstractMenu getCommandScreen(String user)
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
		}

		return cmd;
	}
}
