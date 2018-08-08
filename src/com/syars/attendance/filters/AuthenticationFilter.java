/**
Copyright: SYARS
2018

File Name: AuthenticationFilter.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.filters;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import com.syars.attendance.constants.AttendanceConstants;
import com.syars.attendance.constants.Roles;
import com.syars.attendance.service.UserService;

@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter {
	
	public AuthenticationFilter(ResourceInfo resourceInfo) {
	    super();
	    this.resourceInfo = resourceInfo;
	}

	@Context
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
			.entity(AttendanceConstants.ACCESS_DENIED).build();
	private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
			.entity("Access blocked for all users !!").build();

	@Override
	public void filter(ContainerRequestContext requestContext) {
		Method method = resourceInfo.getResourceMethod();
		// Access allowed for all
		if (!method.isAnnotationPresent(PermitAll.class)) {
			// Access denied for all
			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(ACCESS_FORBIDDEN);
				return;
			}

			// Get request headers
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();

			// Fetch authorization header
			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

			// If no authorization information present; block access
			if (authorization == null || authorization.isEmpty()) {
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}

			// Get encoded username and password
			final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

			// Decode username and password
			// TODO give empty check before decoding
			String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

			// Split username and password tokens
			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();

			// Verify user access
			if (method.isAnnotationPresent(RolesAllowed.class)) {
				RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
				Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

				// Is user valid?
				if (!isUserAllowed(username, password, rolesSet)) {
					requestContext.abortWith(ACCESS_DENIED);
					return;
				}
			}
		}
	}

	private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
		System.out.println(">>>>inside isUserAllowedMethod, userName:" + username + ", password:" + password
				+ ", rolesAllowed:" + rolesSet);

		boolean isAuthorized = true;
		UserService userService = new UserService();
		String authorizationResult = userService.checkUserAuthorization(username, password, rolesSet);
		System.out.println(">>>> authorizationResult:"+authorizationResult);
		System.out.println(">>>> rolesSet:"+rolesSet);
		// DB exception occurred - internal server error - 503
		if (AttendanceConstants.DATABASE_EXCEPTION.equals(authorizationResult)) {
			ACCESS_DENIED = Response.status(503).entity(AttendanceConstants.DATABASE_EXCEPTION).build();
			isAuthorized = false;
		}
		// Authentication was successful, the user is not authorised to view this
		// content - 403 Forbidden
		else if (AttendanceConstants.ROLE_NOT_ALLOWED.equals(authorizationResult)) {
			String message = AttendanceConstants.ACCESS_DENIED + authorizationResult;
			ACCESS_DENIED = Response.status(403).entity(message).build();
			isAuthorized = false;
		}
		// Authentication was successful, user is first time logging - 400
		// if Roles.FIRST_TIME_USER present in the roleSet then authorization = true.
		else if (AttendanceConstants.MULTIPLE_USERS.equals(authorizationResult)
				&& rolesSet.contains(Roles.FIRST_TIME_USER)) {
			isAuthorized = true;

		}
		// Authentication of the user failed(wrong id, password) - 401 UnAuthorized
		else if (!AttendanceConstants.PASSED.equals(authorizationResult)) {
			String message = AttendanceConstants.ACCESS_DENIED + authorizationResult;
			ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
			isAuthorized = false;
		}
		return isAuthorized;
	}
}
