package com.syars.attendance.applications.clients;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.syars.attendance.constants.ClientConstants;
import com.syars.attendance.vo.MemberVO;
import com.thoughtworks.xstream.XStream;

import sun.misc.BASE64Encoder;

public class AWSClient {

	public static void main(String[] args) {
		invokeGetMehod();

	}

	private static void invokeGetMehod() {

		/*String memberId = "MSEC_1";
		StringBuilder uriBuilder = new StringBuilder();
		uriBuilder.append(ClientConstants.BASE_URI)
				  .append(ClientConstants.CONTEXT_ROOT)
				  .append(memberId);
		
		String url = uriBuilder.toString();
		System.out.println("URL:"+url);*/
		
		
		String url = "http://attendanceportal-env-1.ivuvpmm4gm.ap-south-1.elasticbeanstalk.com/v1/rest/members/MSEC_1";
		String name = "rsdKsharma";
		String password = "Adarsh@5";
		String authString = name + ":" + password;
		String authStringEnc = new BASE64Encoder().encode(authString.getBytes());
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		ClientResponse resp = webResource.accept("application/json").header("Authorization", "Basic " + authStringEnc)
				.get(ClientResponse.class);
		System.out.println(resp);
		if (resp.getStatus() != 200) {
			System.err.println("Unable to connect to the server");
		}
		MemberVO output = resp.getEntity(MemberVO.class);
		System.out.println("response: " + new XStream().toXML(output));
	}

}
