/*package com.syars.attendance.applications.clients;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.syars.attendance.constants.Roles;
import com.syars.attendance.enums.MemberEnum;
import com.syars.attendance.vo.Employee;
import com.syars.attendance.vo.Employees;
import com.syars.attendance.vo.UserVO;

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
		
		httpPOSTMethodExample();
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/AttendanceSystem").build();
	}

	private static void httpGETCollectionExample() {
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

	private static void httpGETEntityExample() {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target("http://localhost:8080/AttendanceSystem/rest").path("members")
				.path("1038001");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		// Employee employee = response.readEntity(Employee.class);

		System.out.println("Status:" + response.getStatus());
		System.out.println("response:" + response);
		// System.out.println(employee);
	}

	private static void httpPOSTMethodExample() {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target("http://localhost:8080/AttendancePortal/v1/rest").path("users");

		UserVO user = new UserVO();
		UserVO user1 = new UserVO(null,"SEC1234", "Adarsh", Roles.VOLUNTEER);
		user.setFirstName("Deepak");
		user.setRole(Roles.VOLUNTEER);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_HTML);
		Response response = invocationBuilder.post(Entity.entity(user1, MediaType.APPLICATION_JSON));

		//System.out.println(response.getStatus());
		//System.out.println(response.readEntity(String.class));
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
	}
}
*/