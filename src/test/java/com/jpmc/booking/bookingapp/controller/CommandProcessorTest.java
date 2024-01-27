package com.jpmc.booking.bookingapp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.jpmc.booking.bookingapp.controller.impl.AdminCommandProcessor;
import com.jpmc.booking.bookingapp.controller.impl.BuyerCommandProcessor;
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
	private static final String SAMPLE_SHOW_NUMBER = "show101";

	/**  */
	private static final String SETUP = "setup ";

	/**  */
	private static final String SAMPLE_TICKET_NUMBER = "-00000001";

	/**  */
	private static final String VIEW = "view ";

	/**  */
	private static final String AVAILABILITY = "availability ";

	/**  */
	private static final String CANCEL = "cancel ";

	/**  */
	private static final String PHONE_NUMBER_SAMPLE = "123-45678";

	/**  */
	private static final String BOOK = "book ";

	/**  */
	private static final String SAMPLE_SEAT_NUMBER = " A1,B2";

	/**  */
	private static final Logger LOGGER = LogManager.getLogger(CommandProcessorTest.class);
	//~ Instance fields --------------------------
	/**  */
	private AdminCommandProcessor adminCommandProcessor = new AdminCommandProcessor();

	/**  */
	private BuyerCommandProcessor buyerCommandProcessor = new BuyerCommandProcessor();
	//~ Methods ----------------------------------
	/**  */
	@Test
	public void adminProcessSetupOk()
	{
		try
		{
			adminCommandProcessor.process(SETUP + SAMPLE_SHOW_NUMBER + " 26 10 5");

			Show show = ShowManager.getInstance().retrieveShow(SAMPLE_SHOW_NUMBER);
			List<Seat> availableSeats = show.getAvailableSeats();
			assertTrue(show != null);
			assertEquals(SAMPLE_SHOW_NUMBER, show.getShowNumber());
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
			adminCommandProcessor.process(SETUP + SAMPLE_SHOW_NUMBER + " 26 10 5");
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
			adminCommandProcessor.process(SETUP + SAMPLE_SHOW_NUMBER + " 27 10 5");
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
			adminCommandProcessor.process(SETUP + SAMPLE_SHOW_NUMBER + " 26 11 5");
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
			String sampleShowNumber = SAMPLE_SHOW_NUMBER;
			adminCommandProcessor.process(VIEW + sampleShowNumber);
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
			adminCommandProcessor.process(VIEW + sampleShowNumber);
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
			adminCommandProcessor.process("quit");
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
			buyerCommandProcessor.process(AVAILABILITY + SAMPLE_SHOW_NUMBER);
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
			buyerCommandProcessor.process(AVAILABILITY + sampleShowNumber);
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
			buyerCommandProcessor.process(BOOK + SAMPLE_SHOW_NUMBER + " " + PHONE_NUMBER_SAMPLE + SAMPLE_SEAT_NUMBER);

			Show show = ShowManager.getInstance().retrieveShow(SAMPLE_SHOW_NUMBER);
			List<Seat> bookedSeats = show.getBookedSeats();
			assertTrue(show != null);
			assertEquals(SAMPLE_SHOW_NUMBER, show.getShowNumber());
			assertEquals(260, show.getSeats().size());
			assertEquals(258, show.getAvailableSeats().size());
			assertEquals(2, show.getBookedSeats().size());

			assertEquals("A1", bookedSeats.get(0).getSeatNumber());
			assertEquals("B2", bookedSeats.get(1).getSeatNumber());

			assertEquals(PHONE_NUMBER_SAMPLE, bookedSeats.get(0).getBooking().getPhoneNumber());
			assertEquals(PHONE_NUMBER_SAMPLE, bookedSeats.get(1).getBooking().getPhoneNumber());
			assertFalse(bookedSeats.get(0).isAvailable());
			assertFalse(bookedSeats.get(1).isAvailable());

			assertEquals(SAMPLE_SHOW_NUMBER + SAMPLE_TICKET_NUMBER, bookedSeats.get(0).getBooking().getTicketNumber());
			assertEquals(SAMPLE_SHOW_NUMBER + SAMPLE_TICKET_NUMBER, bookedSeats.get(1).getBooking().getTicketNumber());

			adminCommandProcessor.process(VIEW + SAMPLE_SHOW_NUMBER);
			buyerCommandProcessor.process(AVAILABILITY + SAMPLE_SHOW_NUMBER);
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
			buyerCommandProcessor.process(BOOK + SAMPLE_SHOW_NUMBER + " " + PHONE_NUMBER_SAMPLE + SAMPLE_SEAT_NUMBER);
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
			String tickerNumberSample = SAMPLE_SHOW_NUMBER + "-00000002";
			buyerCommandProcessor.process(CANCEL + tickerNumberSample + " " + PHONE_NUMBER_SAMPLE);
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
			String tickerNumberSample = SAMPLE_SHOW_NUMBER + SAMPLE_TICKET_NUMBER;
			buyerCommandProcessor.process(CANCEL + tickerNumberSample + " " + PHONE_NUMBER_SAMPLE);

			Show show = ShowManager.getInstance().retrieveShow(SAMPLE_SHOW_NUMBER);
			assertTrue(show != null);
			assertEquals(SAMPLE_SHOW_NUMBER, show.getShowNumber());
			assertEquals(260, show.getSeats().size());
			assertEquals(260, show.getAvailableSeats().size());
			assertEquals(0, show.getBookedSeats().size());

			adminCommandProcessor.process(VIEW + SAMPLE_SHOW_NUMBER);
			buyerCommandProcessor.process(AVAILABILITY + SAMPLE_SHOW_NUMBER);
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
			String tickerNumberSample = SAMPLE_SHOW_NUMBER + SAMPLE_TICKET_NUMBER;
			buyerCommandProcessor.process(CANCEL + tickerNumberSample + " " + PHONE_NUMBER_SAMPLE);
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
			String tickerNumberSample = SAMPLE_SHOW_NUMBER + "-00000003";
			buyerCommandProcessor.process(BOOK + SAMPLE_SHOW_NUMBER + " " + PHONE_NUMBER_SAMPLE + SAMPLE_SEAT_NUMBER);

			List<Booking> bookList = BookingManager.getInstance().retrieveBooking(SAMPLE_SHOW_NUMBER,
					PHONE_NUMBER_SAMPLE);

			Calendar earlierTime = Calendar.getInstance();
			earlierTime.add(Calendar.MINUTE, -6);

			for (Booking booking : bookList)
			{
				booking.setBookTime(earlierTime);
			}

			buyerCommandProcessor.process(CANCEL + tickerNumberSample + " " + PHONE_NUMBER_SAMPLE);
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
			String tickerNumberSample = sampleShowNumber + SAMPLE_TICKET_NUMBER;
			buyerCommandProcessor.process(CANCEL + tickerNumberSample + " " + PHONE_NUMBER_SAMPLE);
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
			buyerCommandProcessor.process("quit");
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
			String sampleShowNumber = SAMPLE_SHOW_NUMBER;
			String phoneNumberSample = "123-45677";
			buyerCommandProcessor.process(BOOK + sampleShowNumber + " " + phoneNumberSample + " A10,B200");
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
			String sampleShowNumber = SAMPLE_SHOW_NUMBER;
			String phoneNumberSample = "123-45676";
			buyerCommandProcessor.process(BOOK + sampleShowNumber + " " + phoneNumberSample + " A100,B200");
			fail();
		}
		catch (BookingException e)
		{
			assertEquals("Seats not found/already booked.A100 B200 ", e.getMessage());
		}
	}
}
