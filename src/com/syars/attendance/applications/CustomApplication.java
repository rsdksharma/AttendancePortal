/**
Copyright: SYARS
2018

File Name: CustomApplication.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/


package com.syars.attendance.applications;

import org.glassfish.jersey.server.ResourceConfig;

import com.syars.attendance.filters.AuthenticationDynamicFeature;

public class CustomApplication extends ResourceConfig {
	public CustomApplication() {
		packages(true, "com.syars.attendance");

		// Register Auth Filter here
		register(AuthenticationDynamicFeature.class);

	}
}