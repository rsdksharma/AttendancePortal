/**
Copyright: SYARS
2018

File Name: UserResource.java
************************************************
Change Date		Name		Description
01/07/2018		Deepak S.	Initial Creation

************************************************

*/

package com.syars.attendance.resources;

import java.util.Map;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.wink.common.model.atom.AtomLink;

import com.syars.attendance.constants.ResourcePathConstants;
import com.syars.attendance.constants.Roles;
import com.syars.attendance.service.UserService;
import com.syars.attendance.vo.UserVO;

@DenyAll
@Path(ResourcePathConstants._USERS)
public class UserResource {
	@Context
	UriInfo uriInfo;

	UserService userService = new UserService();

	@GET
	@RolesAllowed({ Roles.ADMIN, Roles.VOLUNTEER })
	@Path(ResourcePathConstants._USERS_ID)
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response getUserById(@PathParam("id") String userId) {
		UserVO UserVo = userService.getUser(userId);
		Response response = Response.noContent().entity("Could not find specified User").build();
		if (UserVo != null) {
			response = Response.ok().entity(UserVo).build();
		}
		return response;
	}

	@GET
	@RolesAllowed({ Roles.ADMIN })
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response getAllUsers() {
		Map<String, UserVO> userMap = userService.getAllUsers();
		Response response = Response.noContent().entity("No User found").build();
		if (userMap != null && !userMap.isEmpty()) {
			response = Response.ok().entity(userMap).build();
		}
		return response;
	}

	@POST
	@RolesAllowed({ Roles.ADMIN })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response registerMemberAsUser(UserVO userVo) {
		String createdUserId = userService.registerMemberAsUser(userVo);
		Response response = Response.status(500).entity("User Could not be Registered.").build();
		if (createdUserId != null) {
			AtomLink selfLink = new AtomLink();
			selfLink.setRel("self");
			selfLink.setHref(uriInfo.getAbsolutePathBuilder().path(createdUserId).build().toString());
			response = Response.status(201).entity("User Registered. Resource path is:\n" + selfLink.getHref()).build();
		}
		return response;
	}

	@PUT
	@RolesAllowed({ Roles.ADMIN, Roles.VOLUNTEER, Roles.FIRST_TIME_USER })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response updateUser(UserVO userVo) {
		
		Response response = Response.status(500).entity("User Could not be Updated.").build();
		if(StringUtils.isNotBlank(userVo.getMemberId()) && StringUtils.isNotBlank(userVo.getUserId()) &&
				StringUtils.isNotBlank(userVo.getCustomizedUserId())) {
			return Response.status(400).entity("Please enter defaultUserId/cusomizedUserId/memberId").build();
		}
		String result = userService.updateUser(userVo);
		if (result != null) {
			AtomLink selfLink = new AtomLink();
			selfLink.setRel("self");
			selfLink.setHref(uriInfo.getAbsolutePathBuilder().path(result).build().toString());
			response = Response.status(Response.Status.ACCEPTED)
					.entity("User Updated successfully. Resource path is:\n" + selfLink.getHref()).build();
		}
		return response;
	}

	@DELETE
	@DenyAll
	@Path(ResourcePathConstants._USERS_ID)
	@RolesAllowed({ Roles.ADMIN })
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response deleteUser(@PathParam("id") String userId) {
		int result = userService.deleteMember(userId);
		Response response = Response.status(500).entity("Member Could not be Deleted.").build();
		if (result > 0) {
			AtomLink selfLink = new AtomLink();
			selfLink.setRel("self");
			selfLink.setHref(uriInfo.getAbsolutePathBuilder().build().toString());
			response = Response.status(202).entity("User deleted from Resource path:\n" + selfLink.getHref()).build();
		}
		return response;
	}

}
