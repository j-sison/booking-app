package com.jpmc.booking.bookingapp.util;

/** @version  $Revision$, $Date$ */
public final class BookingConstant
{
	//~ Static fields/initializers ---------------
	/**  */
	public static final int MAX_SEAT_PER_ROW = 10;

	/**  */
	public static final int MAX_SEAT_ROW = 26;

	/**  */
	public static final String ERROR_INCORRECT_NUMBER_OF_PARAMETERS = "Error: Incorrect number of parameters";

	/**  */
	public static final String ERROR_INVALID_COMMAND = "Error: Invalid command";

	/**  */
	public static final String ERROR_NO_COMMAND = "Error: No command";

	/**  */
	public static final String ERROR_MAX_SEAT_ROW = "Number of rows exceeds the maximum allowed.";

	/**  */
	public static final String ERROR_MAX_SEAT_PER_ROW = "Number of seats per row exceeds the maximum allowed.";

	/**  */
	public static final String ERROR_SHOW_ALREADY_EXISTS = "Show already exists.";

	/**  */
	public static final String ERROR_SHOW_NOT_FOUND = "Show not found.";

	/**  */
	public static final String ERROR_THERE_ARE_NO_BOOKED_SEATS = "There are no booked seats yet.";

	/**  */
	public static final String ERROR_RETURN_TO_MAIN_MENU = "Return to main menu";

	/**  */
	public static final String ERROR_NO_BOOKING_FOUND = "Error: There are no booking for the phone number.";

	/**  */
	public static final String ERROR_TICKET_NOT_MATCHED = "Error: Ticket Number doesn't not match";

	/**  */
	public static final String ERROR_CANCELLATION_NOT_ALLOWED = "Error: Cancellation is no longer allowed.";

	/**  */
	public static final String ERROR_ONLY_ONE_BOOKING_ALLOWED = "Only one booking per phone # is allowed per show.";

	/**  */
	public static final String ERROR_SHOW_NOT_EXISTS = "Error: Show does not exist.";
	//~ Constructors -----------------------------
	/** Creates a new BookingConstant object. */
	private BookingConstant( ) { }
}
