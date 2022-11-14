package com.example.Template.Controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Response;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.Template.DTO.UserDTO;
import com.example.Template.Model.ResponseBody;
import com.example.Template.Model.User;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TemplateUserController {
	
	
	
	RestTemplate restTemplate = new RestTemplate();
	
	
	
	private String APIBaseUrl = "http://localhost:8081/UserManagementAPI";
	
	@PostMapping("/user")
	public void createUser(@RequestBody User user) {
		
		URI url = null;
		try {
			url = new URI(APIBaseUrl + "/user");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		ResponseEntity<ResponseBody> responseEntity = 
				restTemplate.postForEntity(url, user, ResponseBody.class);
		
		ResponseBody<String> responseBody = (ResponseBody<String>) responseEntity.getBody();
		
		System.out.println(responseBody.getData());
	}
	
	@GetMapping("/user/{ID}")
	public void getUser(@PathVariable String ID) {
		
		ResponseEntity<ResponseBody<User>> responseEntity = 
				restTemplate.exchange(APIBaseUrl + "/user/" + ID ,
						HttpMethod.GET, null,
						new ParameterizedTypeReference<ResponseBody<User>>() {
						});
						
		
		ResponseBody<User> body = (ResponseBody<User>) responseEntity.getBody();
		
		
		System.out.println(body.getData());
		
	}
	
	@GetMapping("/users")
	public void getUsers() {
		
		URI url = null;
		try {
			 url = new URI(APIBaseUrl + "/users");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseEntity<ResponseBody<List<User>>> responseEntity = 
				restTemplate.exchange(url, HttpMethod.GET, null, 
						new ParameterizedTypeReference<ResponseBody<List<User>>>() {});
		
		List<User> users = responseEntity.getBody().getData();
		
		System.out.println(users); //
	}
	
	
	@GetMapping("/userdto/{ID}")
	public void getUserDTO(@PathVariable String ID) throws RestClientException, URISyntaxException {		
				
				
		ResponseEntity<ResponseBody<UserDTO>> responseEntity = restTemplate.
				exchange(new URI(APIBaseUrl + "/userdto/" + ID), HttpMethod.GET, null,
						new ParameterizedTypeReference<ResponseBody<UserDTO>>() {});
		
		UserDTO userDTO = responseEntity.getBody().getData();
		System.out.println(userDTO);
	}
	
	@GetMapping("/user")
	public void getUser(@RequestParam String name, @RequestParam String surname) {
		String uri = APIBaseUrl + "/user";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri)
				.queryParam("name", name)
				.queryParam("surname", surname);
		
		String urlWithQueryParams = builder.toUriString();
		
		ResponseEntity<ResponseBody<User>> responseEntity = restTemplate
				.exchange(urlWithQueryParams, HttpMethod.GET, null,
						new ParameterizedTypeReference<ResponseBody<User>>() {});
		
		System.out.println(responseEntity.getBody().getData());
	}
	
	@DeleteMapping("/user")
	public void deleteUser(@RequestBody User user) {

		HttpEntity<User> entity = new HttpEntity<User>(user);
		
		System.out.println(user);
		
		ResponseEntity<ResponseBody<String>> responseEntity = 
				restTemplate.exchange(APIBaseUrl + "/user", HttpMethod.DELETE, entity,
						new ParameterizedTypeReference<ResponseBody<String>> () {});
		
		System.out.println(responseEntity.getBody().getData());
	}
	
	@PutMapping("/user")
	public void editUser(@RequestBody User user) {
		
		HttpEntity<User> entity = new HttpEntity<>(user);
		
		ResponseEntity<ResponseBody<String>> responseEntity = 
				restTemplate.exchange(APIBaseUrl + "/user", HttpMethod.PUT, entity, 
						new ParameterizedTypeReference<ResponseBody<String>>() {});
		
		System.out.println(responseEntity.getBody().getData());
	}
	
	@PatchMapping("/user")
	public void editUserPartially(@RequestBody User user) { //fixme
		
		HttpClient client = HttpClients.createDefault();
		
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client)); 
	    
		HttpEntity entity = new HttpEntity<>(user);
		
		ResponseEntity<ResponseBody<User>> responseEntity = 
				restTemplate.exchange(APIBaseUrl + "/user", HttpMethod.PATCH, entity, 
						new ParameterizedTypeReference<ResponseBody<User>>() {});
//		
		System.out.println("Changed: " + responseEntity.getBody().getData().toString());
	}
	

	@GetMapping("/userspage")
	public void getUsersForPage(@RequestParam int usersPerPage, @RequestParam int pageNumber) {

		String uri = APIBaseUrl + "/userspage";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri)
		.queryParam("usersPerPage", usersPerPage)
		.queryParam("pageNumber", pageNumber);
		
		String url = builder.toUriString();
		
		ResponseEntity<ResponseBody<List<User>>> responseEntity = 
				restTemplate.exchange(url, HttpMethod.GET , null,
						new ParameterizedTypeReference<ResponseBody<List<User>>>() {});
		
		System.out.println(responseEntity.getBody().getData());
	}
}
	
	
