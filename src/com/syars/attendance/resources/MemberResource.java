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

import org.apache.wink.common.model.atom.AtomLink;

import com.syars.attendance.constants.Roles;
import com.syars.attendance.service.MemberService;
import com.syars.attendance.vo.MemberVO;

@DenyAll
@Path(ResourcePathConstants._MEMBERS)
public class MemberResource {
	
	@Context
	UriInfo uriInfo;

	MemberService memberService = new MemberService();

	@GET
	@Path(ResourcePathConstants._MEMBERS_ID)
	@RolesAllowed({ Roles.ADMIN, Roles.VOLUNTEER })
	@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
	public Response getMemberById(@PathParam("id") String memberId) {
		MemberVO memberVo = memberService.getMember(memberId);
		Response response = Response.noContent().entity("Could not find specified member").build();
		if(memberVo != null) {
			response = Response.ok().entity(memberVo).build();
		}
		return response;
	}
	
	@GET
	@RolesAllowed({ Roles.ADMIN })
	@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
	public Response getAllMembers() {
		Map<String, MemberVO> memberMap = memberService.getAllMembers();
		Response response = Response.noContent().entity("No Member found").build();
		if(memberMap != null && !memberMap.isEmpty()) {
			response = Response.ok().entity(memberMap).build();
		}
		return response;
	}

	@POST
	@RolesAllowed({ Roles.ADMIN, Roles.VOLUNTEER })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response createMember(MemberVO memberVo) {
		int result = memberService.createMember(memberVo);
		Response response = Response.status(500).entity("Member Could not be Created.").build();
		if(result>0) {
			AtomLink selfLink = new AtomLink();
			selfLink.setRel("self");
			selfLink.setHref(uriInfo.getAbsolutePathBuilder().path(memberVo.getMobileNumber())
					.build().toString());
			response = Response.status(201).entity("Member Created. Resource path is:\n" + selfLink.getHref()).build();
		}
		return response;
	}

	@PUT
	@RolesAllowed({ Roles.ADMIN, Roles.VOLUNTEER })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response updateMember(MemberVO memberVo) {
		int result = memberService.updateMember(memberVo);
		Response response = Response.status(500).entity("Member Could not be Updated.").build();
		if(result>0) {
			AtomLink selfLink = new AtomLink();
			selfLink.setRel("self");
			selfLink.setHref(uriInfo.getAbsolutePathBuilder().path(memberVo.getMobileNumber())
					.build().toString());
			response = Response.status(Response.Status.ACCEPTED).entity("Member Updated successfully. Resource path is:\n" + selfLink.getHref()).build();
		}
		return response;
	}

	@DELETE
	@Path(ResourcePathConstants._MEMBERS_ID)
	@RolesAllowed({Roles.ADMIN})
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response deleteMember(@PathParam("id") String memberId) {
		int result = memberService.deleteMember(memberId);
		Response response = Response.status(500).entity("Member Could not be Deleted.").build();
		if(result>0) {
			AtomLink selfLink = new AtomLink();
			selfLink.setRel("self");
			selfLink.setHref(uriInfo.getAbsolutePathBuilder()
					.build().toString());
			response = Response.status(Response.Status.ACCEPTED).entity("Member deleted from Resource path:\n" + selfLink.getHref()).build();
		}
		return response;
	}

}
