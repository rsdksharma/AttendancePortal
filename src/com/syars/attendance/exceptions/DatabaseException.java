/**
Copyright: SYARS
2018

File Name: DatabaseException.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.exceptions;

public class DatabaseException extends Exception{
	private static final long serialVersionUID = 1L;

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
