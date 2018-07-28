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