/**
Copyright: SYARS
2018

File Name: DateFormatter.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ulok.inf.logger.MessageLogger;

public class DateFormatter {
	
	public static String formatDate(Date date) {
		String formattedDate = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			formattedDate = dateFormat.format(date);
			MessageLogger.logInfo(DateFormatter.class, "formatDate", null, "formatted Date:"+formattedDate);
		}catch(IllegalArgumentException | NullPointerException e) {
			System.out.println("Date formatting exception");
		}
		return formattedDate;
	}
	
	public static Date formatStringAsDate(String stringDate) {
		Date formattedDate = null;
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			formattedDate = formatter.parse(stringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return formattedDate;
		
	}

}
