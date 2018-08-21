/**
Copyright: SYARS
2018

File Name: AttendanceResource.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.resources;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.syars.attendance.constants.ResourcePathConstants;
import com.syars.attendance.constants.Roles;
import com.syars.attendance.service.PresenceService;
import com.syars.attendance.utils.DateFormatter;
import com.syars.attendance.vo.PresenceVO;
import com.syars.attendance.vo.ResponseVO;

@DenyAll
@Path(ResourcePathConstants._ATTENDANCE)
public class AttendanceResource {
	@Context
	UriInfo uriInfo;

	PresenceService presenceService = new PresenceService();

	@GET
	@Path(ResourcePathConstants._ATTENDANCE_ID)
	@RolesAllowed({ Roles.ADMIN })
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response fetchAttendanceByMemberId(@PathParam("id") String memberId) {
		Set<String> presenceMap = presenceService.retrievePresenceByMemberId(memberId);
		Response response = Response.noContent().entity("No Member found").build();
		if (presenceMap != null && !presenceMap.isEmpty()) {
			response = Response.ok().entity(presenceMap).build();
		}
		return response;
	}

	@GET
	@RolesAllowed({ Roles.ADMIN })
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response retrieveAllAtendance() {
		
		List<ResponseVO> presenceResponse = presenceService.retrievePresenceForAll();
		
		Response response = Response.noContent().entity("No Member found").build();
		/*if(presenceMap.containsKey(AttendanceConstants.DB_EXCEPTION)) {
			response = Response.status(503).entity("DB Exception occured").build();
		}*/
		if (presenceResponse != null && !presenceResponse.isEmpty()) {
			response = Response.ok().entity(presenceResponse).build();
		}
		return response;
	}
	
	@GET
	@Path(ResourcePathConstants._MEMBERS)
	@RolesAllowed({ Roles.ADMIN })
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response retrieveAttendanceByDate() {
		
		List<ResponseVO> presenceList = presenceService.retrieveAttendanceByDate(DateFormatter.formatDate(new Date()));
		
		Response response = Response.noContent().entity("No Member found").build();
		if(presenceList == null) {
			response = Response.status(503).entity("No Member found").build();
		}
		else if (presenceList != null && !presenceList.isEmpty()) { 
			response = Response.ok().entity(presenceList).build();
		}
		return response;
	}

	@POST
	@RolesAllowed({ Roles.ADMIN, Roles.VOLUNTEER })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response insertPresence(PresenceVO presenceVo) {
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
		int result = presenceService.insertPresence(presenceVo);
		Response response = Response.status(500).entity("Presence Could not be Created.").build();
		if(result == 503) {
			response = Response.status(503).entity("DB Exception Occured").build();
		}
		else if(result == -1) {
			response = Response.status(500).entity("Presence could not be created. This member is not registered.").build();
		}
		else if (result > 0) {
			response = Response.status(201)
					.entity("Presence Created for member:" + presenceVo.getMemberId() + " and date:" + DateFormatter.formatDate(new Date()))
					.build();
		}
		return response;
	}
	
	@GET
	@Path(ResourcePathConstants._COUNT)
	@RolesAllowed({ Roles.ADMIN })
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response getCountForToday() {
		int count = presenceService.getCount(DateFormatter.formatDate(new Date()));
		Response response = Response.ok().entity(count).build();
		
		return response;
	}
	
	@GET
	@Path(ResourcePathConstants._COUNT_DATE)
	@RolesAllowed({ Roles.ADMIN })
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response getCountForDate(@PathParam("date") String date) {
		int count = presenceService.getCount(date);
		Response response = Response.ok().entity(count).build();
		
		return response;
	}
}
