package com.syars.attendance.resources;

import java.util.Map;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.syars.attendance.constants.Roles;
import com.syars.attendance.service.MemberService;
import com.syars.attendance.vo.MemberVO;

@DenyAll
@Path(ResourcePathConstants._MEMBERS)
public class MemberResource {

	MemberService memberService = new MemberService();

	@GET
	@Path(ResourcePathConstants._MEMBERS_ID)
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMemberById(@PathParam("id") String memberId) {
		String message = "inside getMemberById";
		MemberVO memberVo = memberService.getMember(memberId);
		//Response.ok().entity(memberVo).build();
		return Response.ok().entity(memberVo).build();
	}
	
	@GET
	@RolesAllowed({ Roles.ADMIN })
	@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
	public Response getAllMembers() {
		Map<String, MemberVO> memberMap = memberService.getAllMembers();
		return Response.ok().entity(memberMap).build();
	}

	@POST
	@RolesAllowed({ Roles.ADMIN, Roles.VOLUNTEER })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response createMember(MemberVO memberVo) {
		System.out.println("=============id:"/* +member.getGlobalUIDNumber() */);
		String message = "inside createMember";
		memberService.createMember(memberVo);
		Response.ok().entity(message).build();
		return Response.ok().entity(message).build();
	}

	@PUT
	@RolesAllowed({ Roles.ADMIN, Roles.VOLUNTEER })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response updateMember() {
		String message = "inside updateMember";
		Response.ok().entity(message).build();
		return Response.ok().entity(message).build();
	}

	@DELETE
	@RolesAllowed({Roles.ADMIN})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response deleteMember() {
		String message = "inside deleteMember";
		Response.ok().entity(message).build();
		return Response.ok().entity(message).build();
	}

}
