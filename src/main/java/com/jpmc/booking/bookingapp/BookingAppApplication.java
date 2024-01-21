package com.jpmc.booking.bookingapp;

import com.jpmc.booking.bookingapp.ui.AbstractMenu;
import com.jpmc.booking.bookingapp.util.CommandScreenFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/** @version  $Revision$, $Date$ */
@SpringBootApplication
public class BookingAppApplication implements CommandLineRunner
{
	//~ Static fields/initializers ---------------
	/**  */
	private static final String VALID_USERS = "1 2";

	/**  */
	private static final Logger LOGGER = LogManager.getLogger(BookingAppApplication.class);
	//~ Methods ----------------------------------
	/** @param  args */
	public static void main(String[] args)
	{
		SpringApplication.run(BookingAppApplication.class, args);
	}
	
	/** @param  args */
	public void run(String... args)
	{
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));
		String user = null;
		try
		{
			while (true)
			{
				System.out.println(
					"=======================================================================================================");
				System.out.print("Please select user – (1) Admin (2) Buyer: ");

				user = reader.readLine();

				if (!isValidUser(user))
				{
					continue;
				}
				AbstractMenu cmd = CommandScreenFactory.getCommandScreen(user);
				cmd.inputCommand();
			}
		}
		catch (IOException e)
		{
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @param   user
	 * @return
	 */
	private boolean isValidUser(String user)
	{
		return VALID_USERS.contains(user);
	}
}
