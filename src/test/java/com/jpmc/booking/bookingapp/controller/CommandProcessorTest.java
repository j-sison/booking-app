package com.jpmc.booking.bookingapp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.jpmc.booking.bookingapp.util.BookingConstant;
import com.jpmc.booking.bookingapp.util.BookingManager;
import com.jpmc.booking.bookingapp.util.ShowManager;
import com.jpmc.booking.bookingapp.util.exception.BookingException;
import com.jpmc.booking.bookingapp.vo.Booking;
import com.jpmc.booking.bookingapp.vo.Seat;
import com.jpmc.booking.bookingapp.vo.Show;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;

import java.util.Calendar;
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
			fail();
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
			fail();
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
			fail();
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
			fail();
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
			fail();
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_SHOW_NOT_FOUND, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void buyerProcessBook_1_Ok()
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
	public void buyerProcessBook_2_OnlyOneBookingAllowedError()
	{
		try
		{
			String sampleShowNumber = "show101";
			String phoneNumberSample = "123-45678";
			BuyerCommandProcessor.process("book " + sampleShowNumber + " " + phoneNumberSample + " A1,B2");
			fail();
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_ONLY_ONE_BOOKING_ALLOWED, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void buyerProcessCancel_1_TicketNotMatchedError()
	{
		try
		{
			String sampleShowNumber = "show101";
			String phoneNumberSample = "123-45678";
			String tickerNumberSample = sampleShowNumber + "-00000002";
			BuyerCommandProcessor.process("cancel " + tickerNumberSample + " " + phoneNumberSample);
			fail();
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_TICKET_NOT_MATCHED, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void buyerProcessCancel_2_Ok()
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
			assertEquals(260, show.getAvailableSeats().size());
			assertEquals(0, show.getBookedSeats().size());

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
	public void buyerProcessCancel_3_TicketNoBooking()
	{
		try
		{
			String sampleShowNumber = "show101";
			String phoneNumberSample = "123-45678";
			String tickerNumberSample = sampleShowNumber + "-00000001";
			BuyerCommandProcessor.process("cancel " + tickerNumberSample + " " + phoneNumberSample);
			fail();
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_NO_BOOKING_FOUND, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void buyerProcessCancel_4_ErrorCancellationWindow()
	{
		try
		{
			String sampleShowNumber = "show101";
			String phoneNumberSample = "123-45678";
			String tickerNumberSample = sampleShowNumber + "-00000003";
			BuyerCommandProcessor.process("book " + sampleShowNumber + " " + phoneNumberSample + " A1,B2");

			List<Booking> bookList = BookingManager.retrieveBooking(sampleShowNumber, tickerNumberSample,
					phoneNumberSample);

			Calendar earlierTime = Calendar.getInstance();
			earlierTime.add(Calendar.MINUTE, -6);

			for (Booking booking : bookList)
			{
				booking.setBookTime(earlierTime);
			}

			BuyerCommandProcessor.process("cancel " + tickerNumberSample + " " + phoneNumberSample);
			fail();
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_CANCELLATION_NOT_ALLOWED, e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void buyerProcessCancel_5_NonExistingShowError()
	{
		try
		{
			String sampleShowNumber = "show1001";
			String phoneNumberSample = "123-45678";
			String tickerNumberSample = sampleShowNumber + "-00000001";
			BuyerCommandProcessor.process("cancel " + tickerNumberSample + " " + phoneNumberSample);
			fail();
		}
		catch (BookingException e)
		{
			assertEquals(BookingConstant.ERROR_SHOW_NOT_EXISTS, e.getMessage());
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
	
	/**  */
	@Test
	public void buyerProcessXBook_3_SomeSeatsNotFoundError()
	{
		try
		{
			String sampleShowNumber = "show101";
			String phoneNumberSample = "123-45677";
			BuyerCommandProcessor.process("book " + sampleShowNumber + " " + phoneNumberSample + " A10,B200");
			fail();
		}
		catch (BookingException e)
		{
			assertEquals("Booking as been created. Ticket number: show101-00000004\n"
				+ "Some seats were not found/already booked: B200 ", e.getMessage());
		}
	}
	
	/**  */
	@Test
	public void buyerProcessXBook_4_SeatsNotFoundError()
	{
		try
		{
			String sampleShowNumber = "show101";
			String phoneNumberSample = "123-45676";
			BuyerCommandProcessor.process("book " + sampleShowNumber + " " + phoneNumberSample + " A100,B200");
			fail();
		}
		catch (BookingException e)
		{
			assertEquals("Seats not found/already booked.A100 B200 ", e.getMessage());
		}
	}
}
