package com.syars.attendance.exceptions;

public class DatabaseException extends Exception{
	private static final long serialVersionUID = 1L;

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
