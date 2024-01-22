package com.jpmc.booking.bookingapp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.jpmc.booking.bookingapp.util.BookingConstant;
import com.jpmc.booking.bookingapp.util.ShowManager;
import com.jpmc.booking.bookingapp.util.exception.BookingException;
import com.jpmc.booking.bookingapp.vo.Seat;
import com.jpmc.booking.bookingapp.vo.Show;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;

import java.util.List;


/** @version  $Revision$, $Date$ */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandProcessorTest
{
	//~ Static fields/initializers ---------------
	/**  */
	private static final Logger LOGGER = LogManager.getLogger(CommandProcessorTest.class);
	//~ Methods ----------------------------------
	/**  */
	@Test
	public void adminProcessSetupOk()
	{
		try
		{
			String sampleShowNumber = "show101";
			AdminCommandProcessor.process("setup " + sampleShowNumber + " 26 10 5");

			Show show = ShowManager.retrieveShow(sampleShowNumber);
			List<Seat> availableSeats = show.getAvailableSeats();
			assertTrue(show != null);
			assertEquals(sampleShowNumber, show.getShowNumber());
			assertEquals(260, show.getSeats().size());
			assertEquals(260, availableSeats.size());
			assertEquals(0, show.getBookedSeats().size());
			assertEquals(5, show.getCancelWindow());

			assertEquals(availableSeats.get(0).getSeatNumber(), "A1");
			assertEquals(availableSeats.get(259).getSeatNumber(), "Z9");
			assertTrue(availableSeats.get(0).isAvailable());
			assertTrue(availableSeats.get(259).isAvailable());
		}
		catch (BookingException e)
		{
			fail();
			LOGGER.error(e);
		}
	}
	
	/**  */
	@Test
	public void adminProcessSetupShowAlreadyExistError()
	{
		try
		{
			String sampleShowNumber = "show101";
			AdminCommandProcessor.process("setup " + sampleShowNumber + " 26 10 5");
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_SHOW_ALREADY_EXISTS, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void adminProcessSetupMaxRowError()
	{
		try
		{
			String sampleShowNumber = "show101";
			AdminCommandProcessor.process("setup " + sampleShowNumber + " 27 10 5");
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_MAX_SEAT_ROW, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void adminProcessSetupMaxSeatsPerRowError()
	{
		try
		{
			String sampleShowNumber = "show101";
			AdminCommandProcessor.process("setup " + sampleShowNumber + " 26 11 5");
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_MAX_SEAT_PER_ROW, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void adminProcessViewOk()
	{
		try
		{
			String sampleShowNumber = "show101";
			AdminCommandProcessor.process("view " + sampleShowNumber);
		}
		catch (BookingException e)
		{
			fail();
			LOGGER.error(e);
		}
	}
	
	/**  */
	@Test
	public void adminProcessViewShowNotExistingError()
	{
		try
		{
			String sampleShowNumber = "show102";
			AdminCommandProcessor.process("view " + sampleShowNumber);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_SHOW_NOT_FOUND, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void adminProcessQuit()
	{
		try
		{
			AdminCommandProcessor.process("quit");
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_RETURN_TO_MAIN_MENU, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void buyerProcessAvailabilityOk()
	{
		try
		{
			String sampleShowNumber = "show101";
			BuyerCommandProcessor.process("availability " + sampleShowNumber);
		}
		catch (BookingException e)
		{
			fail();
			LOGGER.error(e);
		}
	}
	
	/**  */
	@Test
	public void buyerProcessAvailabilityShowNotFound()
	{
		try
		{
			String sampleShowNumber = "show102";
			BuyerCommandProcessor.process("availability " + sampleShowNumber);
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_SHOW_NOT_FOUND, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void buyerProcessBookOk()
	{
		try
		{
			String sampleShowNumber = "show101";
			String phoneNumberSample = "123-45678";
			BuyerCommandProcessor.process("book " + sampleShowNumber + " " + phoneNumberSample + " A1,B2");

			Show show = ShowManager.retrieveShow(sampleShowNumber);
			List<Seat> bookedSeats = show.getBookedSeats();
			assertTrue(show != null);
			assertEquals(sampleShowNumber, show.getShowNumber());
			assertEquals(260, show.getSeats().size());
			assertEquals(258, show.getAvailableSeats().size());
			assertEquals(2, show.getBookedSeats().size());

			assertEquals("A1", bookedSeats.get(0).getSeatNumber());
			assertEquals("B2", bookedSeats.get(1).getSeatNumber());

			assertEquals(phoneNumberSample, bookedSeats.get(0).getBooking().getPhoneNumber());
			assertEquals(phoneNumberSample, bookedSeats.get(1).getBooking().getPhoneNumber());
			assertFalse(bookedSeats.get(0).isAvailable());
			assertFalse(bookedSeats.get(1).isAvailable());

			assertEquals(sampleShowNumber + "-00000001", bookedSeats.get(0).getBooking().getTicketNumber());
			assertEquals(sampleShowNumber + "-00000001", bookedSeats.get(1).getBooking().getTicketNumber());

			AdminCommandProcessor.process("view " + sampleShowNumber);
			BuyerCommandProcessor.process("availability " + sampleShowNumber);
		}
		catch (BookingException e)
		{
			fail();
			LOGGER.error(e);
		}
	}
	
	/**  */
	@Test
	public void buyerProcessCancelOk()
	{
		try
		{
			String sampleShowNumber = "show101";
			String phoneNumberSample = "123-45678";
			String tickerNumberSample = sampleShowNumber + "-00000001";
			BuyerCommandProcessor.process("cancel " + tickerNumberSample + " " + phoneNumberSample);

			Show show = ShowManager.retrieveShow(sampleShowNumber);
			List<Seat> bookedSeats = show.getBookedSeats();
			assertTrue(show != null);
			assertEquals(sampleShowNumber, show.getShowNumber());
			assertEquals(260, show.getSeats().size());
			assertEquals(259, show.getAvailableSeats().size());
			assertEquals(1, show.getBookedSeats().size());

			assertEquals("B2", bookedSeats.get(0).getSeatNumber());
			assertFalse(bookedSeats.get(0).isAvailable());
			assertEquals(phoneNumberSample, bookedSeats.get(0).getBooking().getPhoneNumber());
			assertEquals(sampleShowNumber + "-00000001", bookedSeats.get(0).getBooking().getTicketNumber());

			AdminCommandProcessor.process("view " + sampleShowNumber);
			BuyerCommandProcessor.process("availability " + sampleShowNumber);
		}
		catch (BookingException e)
		{
			fail();
			LOGGER.error(e);
		}
	}
	
	/**  */
	@Test
	public void buyerProcessQuit()
	{
		try
		{
			BuyerCommandProcessor.process("quit");
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_RETURN_TO_MAIN_MENU, e.getMessage());
		}
	}
}
