package com.jpmc.booking.bookingapp.controller;

import com.jpmc.booking.bookingapp.util.exception.BookingException;


/** @version  $Revision$, $Date$ */
public interface ICommandProcessor
{
	//~ Methods ----------------------------------
	/**
	 * @param   command
	 * @throws  BookingException
	 */
	void process(String command) throws BookingException;
}
