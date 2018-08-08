/**
Copyright: SYARS
2018

File Name: ResourcePathConstants.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.constants;

public class ResourcePathConstants {
	
	public static final String _ID = "id";
	
	//PATH RELATED TO USERS - START
	public static final String _USERS = "/users";
	public static final String _USERS_ID = "/{id}";
	
	//PATH RELATED TO USERS - END
	
	//PATH RELATED TO MEMBERS - START
	public static final String _MEMBERS = "/members";
	public static final String _MEMBERS_ID = "/{id}";
		
	//PATH RELATED TO MEMBERS - END
	
	//PATH RELATED TO ATTENDANCE - START
	public static final String _ATTENDANCE = "/attendance";
	public static final String _ATTENDANCE_ID = "/{id}";

	public static final String _COUNT = "/count";

	public static final String _COUNT_DATE = "/count/{date}";
		
	//PATH RELATED TO ATTENDANCE - END
	

}
