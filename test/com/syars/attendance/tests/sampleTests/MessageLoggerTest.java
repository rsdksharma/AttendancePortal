package com.syars.attendance.tests.sampleTests;

import java.util.Date;
import java.util.TimeZone;

import com.syars.attendance.utils.MongoDBUtils;

public class MessageLoggerTest {

	public static void main(String[] args) {
		MongoDBUtils dbUtils = new MongoDBUtils();
		//dbUtils.testMessageLogger();
		Date date1 = new Date();
		System.out.println(date1);

		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
		// or pass in a command line arg: -Duser.timezone="UTC"

		Date date2 = new Date();
		System.out.println(date2);
	}

}
