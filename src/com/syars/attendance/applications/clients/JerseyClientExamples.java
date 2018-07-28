package com.syars.attendance.applications.clients;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.glassfish.jersey.client.ClientConfig;

import com.syars.attendance.constants.Roles;
import com.syars.attendance.vo.UserVO;
import com.thoughtworks.xstream.XStream;

public class JerseyClientExamples {
	public static void main(String[] args) {

		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target(getBaseURI());

		String response = target.path("rest").path("members").path("1038001").request().accept(MediaType.TEXT_HTML)
				.get(Response.class).toString();
		String htmlAnswer = target.path("rest").path("members").path("1038001").request().accept(MediaType.TEXT_HTML)
				.get(String.class);

		
		System.out.println(">>>>html answer:" + htmlAnswer);
		System.out.println(">>>>Response:" + response);
		
		//httpPOSTMethodExample();
		httpGETEntityExample_new();
	}

	private static void httpGETEntityExample_new() {
		HttpClient client = null;

	    try {
	    HttpPost request = new HttpPost("example.com/api/deposit");
	    StringEntity params = null;
	    request.addHeader("Content-Type", "application/json");
	    request.addHeader("Accept-Language", "en-US,en;q=0.8");
	    request.addHeader("Authorization", "Basic somecode&&!!");
	    request.setEntity(params);
	    HttpResponse response = client.execute(request);

	    //handle the response somehow
	    //example : System.out.println (errormessage);

	    } catch (Exception ex) {
	    ex.printStackTrace();
	    ex.getMessage();
	    } finally {
	    client.getConnectionManager().shutdown();

	    }
	}
	private static void httpGETEntityExample() {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target("http://attendanceportal-env.mxvsmew5zp.ap-south-1.elasticbeanstalk.com");
		
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response = invocationBuilder.get();

		// Employee employee = response.readEntity(Employee.class);

		System.out.println("Status:" + response.getStatus());
		System.out.println(new XStream().toXML(response.getEntity()));
		// System.out.println(employee);
		
		
	}
	
	private static void httpPOSTMethodExample() {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target("http://localhost:8080/AttendancePortal/v1/rest").path("users");

		UserVO user = new UserVO();
		//UserVO user1 = new UserVO(null,"SEC1234", "Adarsh", Roles.VOLUNTEER);
		//user.setFirstName("Deepak");
		user.setUserRole(Roles.VOLUNTEER);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_HTML);
		Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));

		//System.out.println(response.getStatus());
		//System.out.println(response.readEntity(String.class));
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/AttendanceSystem").build();
	}

	/*private static void httpGETCollectionExample() {
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().nonPreemptive()
				.credentials("user", "password").build();

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(feature);

		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client.target("http://localhost:8080/JerseyDemos/rest").path("employees");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.get();

		Employees employees = response.readEntity(Employees.class);
		List<Employee> listOfEmployees = employees.getEmployeeList();

		System.out.println(response.getStatus());
		System.out.println(Arrays.toString(listOfEmployees.toArray(new Employee[listOfEmployees.size()])));
	}

	private static void httpPUTMethodExample() {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target("http://localhost:8080/JerseyDemos/rest").path("employees").path("1");

		Employee emp = new Employee();
		emp.setId(1);
		emp.setName("David Feezor");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.put(Entity.entity(emp, MediaType.APPLICATION_XML));

		Employee employee = response.readEntity(Employee.class);

		System.out.println(response.getStatus());
		System.out.println(employee);
	}

	private static void httpDELETEMethodExample() {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target("http://localhost:8080/JerseyDemos/rest").path("employees").path("1");

		Invocation.Builder invocationBuilder = webTarget.request();
		Response response = invocationBuilder.delete();

		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}*/
}
