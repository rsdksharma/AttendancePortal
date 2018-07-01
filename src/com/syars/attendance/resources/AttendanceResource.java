package com.syars.attendance.resources;

import java.util.Date;
import java.util.Map;
import java.util.Set;

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

import com.syars.attendance.constants.AttendanceConstants;
import com.syars.attendance.constants.Roles;
import com.syars.attendance.service.PresenceService;
import com.syars.attendance.vo.PresenceVO;

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
		Set<Date> presenceMap = presenceService.retrievePresenceByMemberId(memberId);
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
		
		Map<String, Set<Date>> presenceMap = presenceService.retrievePresenceForAll();
		
		Response response = Response.noContent().entity("No Member found").build();
		if(presenceMap.containsKey(AttendanceConstants.DB_EXCEPTION)) {
			response = Response.status(503).entity("DB Exception occured").build();
		}
		else if (presenceMap != null && !presenceMap.isEmpty()) {
			response = Response.ok().entity(presenceMap).build();
		}
		return response;
	}

	@POST
	@RolesAllowed({ Roles.ADMIN, Roles.VOLUNTEER })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response insertPresence(PresenceVO presenceVo) {
		int result = presenceService.insertPresence(presenceVo);
		Response response = Response.status(500).entity("Presence Could not be Created.").build();
		if(result == 503) {
			response = Response.status(503).entity("DB Exception Occured").build();
		}
		else if (result > 0 && result != 503) {
			response = Response.status(201)
					.entity("Presence Created for member:" + presenceVo.getMemberId() + " and date:" + new Date())
					.build();
		}
		return response;
	}

}
