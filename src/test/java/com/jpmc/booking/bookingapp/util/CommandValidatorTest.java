package com.jpmc.booking.bookingapp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.jpmc.booking.bookingapp.ui.impl.AdminMenu;
import com.jpmc.booking.bookingapp.ui.impl.BuyerMenu;
import com.jpmc.booking.bookingapp.util.exception.BookingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Test;


/** @version  $Revision$, $Date$ */
public class CommandValidatorTest
{
	//~ Static fields/initializers ---------------
	/**  */
	private static final Logger LOGGER = LogManager.getLogger(CommandValidatorTest.class);
	//~ Methods ----------------------------------
	/**  */
	@Test
	public void validateAdminSetupCommandCorrectParameters()
	{
		AdminMenu cmdScreen = new AdminMenu();
		String cmd = "setup show101 2 10 5";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			LOGGER.error(e);
			fail();
		}
	}
	
	/**  */
	@Test
	public void validateAdminSetupCommandIncorrectParameters()
	{
		AdminMenu cmdScreen = new AdminMenu();
		String cmd = "setup show101 2 10";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_INCORRECT_NUMBER_OF_PARAMETERS, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void validateAdminViewCommandCorrectParameters()
	{
		AdminMenu cmdScreen = new AdminMenu();
		String cmd = "view show101";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			LOGGER.error(e);
			fail();
		}
	}
	
	/**  */
	@Test
	public void validateAdminViewCommandIncorrectParameters()
	{
		AdminMenu cmdScreen = new AdminMenu();
		String cmd = "view show101 10";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_INCORRECT_NUMBER_OF_PARAMETERS, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void validateBuyerAvailabilityCommandCorrectParameters()
	{
		BuyerMenu cmdScreen = new BuyerMenu();
		String cmd = "availability show101";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			LOGGER.error(e);
			fail();
		}
	}
	
	/**  */
	@Test
	public void validateBuyerAvailabilityCommandIncorrectParameters()
	{
		BuyerMenu cmdScreen = new BuyerMenu();
		String cmd = "availability show101 10 10";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_INCORRECT_NUMBER_OF_PARAMETERS, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void validateBuyerBookCommandCorrectParameters()
	{
		BuyerMenu cmdScreen = new BuyerMenu();
		String cmd = "book show101 123-45678 A1,A2,A3";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			LOGGER.error(e);
			fail();
		}
	}
	
	/**  */
	@Test
	public void validateBuyerBookCommandIncorrectParameters()
	{
		BuyerMenu cmdScreen = new BuyerMenu();
		String cmd = "book show101 123-45678 A1,A2 A3";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_INCORRECT_NUMBER_OF_PARAMETERS, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void validateBuyerCancelCommandCorrectParameters()
	{
		BuyerMenu cmdScreen = new BuyerMenu();
		String cmd = "cancel show101 123-45678";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			LOGGER.error(e);
			fail();
		}
	}
	
	/**  */
	@Test
	public void validateBuyerCancelCommandIncorrectParameters()
	{
		BuyerMenu cmdScreen = new BuyerMenu();
		String cmd = "cancel show101 123-45678 10";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_INCORRECT_NUMBER_OF_PARAMETERS, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void validateNoCommand()
	{
		BuyerMenu cmdScreen = new BuyerMenu();
		String cmd = "";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_NO_COMMAND, e.getMessage());
		}

		AdminMenu cmdScreen2 = new AdminMenu();
		String cmd2 = null;
		try
		{
			CommandValidator.validate(cmdScreen2, cmd2);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_NO_COMMAND, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void validateAdminUnsupportedCommand()
	{
		AdminMenu cmdScreen = new AdminMenu();
		String cmd = "cancel show101 123-45678";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_INVALID_COMMAND, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void validateBuyerUnsupportedCommand()
	{
		BuyerMenu cmdScreen = new BuyerMenu();
		String cmd = "setup show101 2 10 5";
		try
		{
			CommandValidator.validate(cmdScreen, cmd);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_INVALID_COMMAND, e.getMessage());
		}
	}
}
