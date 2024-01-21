package com.jpmc.booking.bookingapp;

import com.jpmc.booking.bookingapp.controller.CommandProcessorTest;
import com.jpmc.booking.bookingapp.util.CommandValidatorTest;

import org.junit.runner.RunWith;

import org.junit.runners.Suite.SuiteClasses;


/**
 * Test Suite for all BookingAppApplication unit tests
 *
 * @version  $Revision$, $Date$
 */
@RunWith(value = org.junit.runners.Suite.class)
@SuiteClasses(value = { CommandValidatorTest.class, CommandProcessorTest.class })
public class BookingAppApplicationTestSuite { }
