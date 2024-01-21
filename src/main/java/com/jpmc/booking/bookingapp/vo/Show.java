package com.jpmc.booking.bookingapp.vo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class Show
{
	//~ Instance fields --------------------------
	/**  */
	private int cancelWindow;

	/**  */
	private ConcurrentHashMap<String, Seat> seats = new ConcurrentHashMap<>();

	/**  */
	private String showNumber;
	//~ Constructors -----------------------------
	/**
	 * Creates a new Show object.
	 *
	 * @param  showNumber
	 * @param  numOfRows
	 * @param  numOfSeatsPerRow
	 * @param  cancelWindow
	 */
	public Show(String showNumber, int numOfRows, int numOfSeatsPerRow, int cancelWindow)
	{
		this.showNumber = showNumber;
		this.cancelWindow = cancelWindow;

		for (int rowCount = 65; rowCount <= (65 + numOfRows); rowCount++)
		{
			for (int seatCount = 0; seatCount < numOfSeatsPerRow; seatCount++)
			{
				String seatNumber = ((char) rowCount) + String.valueOf(seatCount + 1);
				seats.put(seatNumber, new Seat(this, seatNumber));
			}
		}
	}
	//~ Methods ----------------------------------
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public int getCancelWindow()
	{
		return cancelWindow;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public ConcurrentHashMap<String, Seat> getSeats()
	{
		return seats;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public String getShowNumber()
	{
		return showNumber;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public List<Seat> getBookedSeats()
	{
		List<Seat> bookedSeats = seats.entrySet().stream().map(Map.Entry::getValue).filter(s -> !s.isAvailable())
				.collect(Collectors.toList());

		bookedSeats.sort((p1, p2) -> p1.getSeatNumber().compareToIgnoreCase(p2.getSeatNumber()));

		return bookedSeats;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public List<Seat> getAvailableSeats()
	{
		List<Seat> availableSeats = seats.entrySet().stream().map(Map.Entry::getValue).filter(s -> s.isAvailable())
				.collect(Collectors.toList());

		availableSeats.sort((p1, p2) -> p1.getSeatNumber().compareToIgnoreCase(p2.getSeatNumber()));

		return availableSeats;
	}
}
