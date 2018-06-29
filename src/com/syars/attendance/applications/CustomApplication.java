package com.syars.attendance.applications;

import org.glassfish.jersey.server.ResourceConfig;

import com.syars.attendance.filters.AuthenticationFilter;

public class CustomApplication extends ResourceConfig {
	public CustomApplication() {
		packages("com.syars.attendance");
		// register(LoggingFilter.class);
		// register(GsonMessageBodyHandler.class);

		// Register Auth Filter here
		register(AuthenticationFilter.class);
	}
}