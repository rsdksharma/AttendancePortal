package com.syars.attendance.resources;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
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

import org.apache.wink.common.model.atom.AtomLink;

import com.syars.attendance.constants.Roles;
import com.syars.attendance.service.UserService;
import com.syars.attendance.vo.UserVO;

@DenyAll
@Path(ResourcePathConstants._USERS)
public class UserResource {
	@Context
	UriInfo uriInfo;
	// @Context
	// Request request;

	UserService userService = new UserService();

	/*
	 * @POST
	 * 
	 * @Produces(MediaType.TEXT_HTML)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON) public String
	 * registerAsNormalUser(@QueryParam("branchUIDNumber") String branchUIDNumber,
	 * 
	 * @QueryParam("password") String password) { UserVO userVo = new
	 * UserVO(branchUIDNumber, password, Roles.ADMIN);
	 * 
	 * return userService.registerAsNormaluser(userVo); }
	 */
	@GET
	//@PermitAll
	@RolesAllowed({ Roles.ADMIN, Roles.VOLUNTEER })
	@Path(ResourcePathConstants._USERS_ID)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
	public Response getUserById(@PathParam("id") String globalUIDNumber) {

		System.out.println(">>>>inside getUserById:" + globalUIDNumber);

		UserVO userVo = new UserVO(null,"DS8981", "Adarsh@5", Roles.ADMIN);
		userVo.setGlobalUIDNumber(globalUIDNumber);
		// return userService.registerAsNormaluser(userVo);
		AtomLink selfLink = new AtomLink();
		selfLink.setRel("self");
		selfLink.setHref(uriInfo.getBaseUriBuilder().path(UserResource.class).path(UserResource.class, "getUserById")
				.build(userVo.getGlobalUIDNumber()).toString());

		userVo.setLink(selfLink);
		return Response.ok().entity(userVo).build();
	}

	@POST
	@RolesAllowed({ Roles.ADMIN })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response createUser(UserVO userVo) {
		String message = "inside createUser";
		System.out.println(">>>>user ID:" + userVo.getUserId() + ", PASSWORD:" + userVo.getPassword()
				+ ", Role:" + userVo.getRole());
		userService.createUser(userVo);
		Response.ok().entity(message).build();
		return Response.ok().entity(message).build();
	}

	@PUT
	@RolesAllowed({ Roles.ADMIN })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response updateUser() {
		String message = "inside updateUser";
		Response.ok().entity(message).build();
		return Response.ok().entity(message).build();
	}

	@DELETE
	@DenyAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response deleteUser() {
		String message = "inside deleteUser";
		Response.ok().entity(message).build();
		return Response.ok().entity(message).build();
	}

}
