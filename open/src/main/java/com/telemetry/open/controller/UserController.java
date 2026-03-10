package com.telemetry.open.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.telemetry.open.entity.User;
import com.telemetry.open.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final RestTemplate restTemplate;
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	public UserController(UserService userService, RestTemplate restTemplate) {
		this.userService = userService;
		this.restTemplate = restTemplate;
	}

	@GetMapping("/fetchUser")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();

		String greetResponse = restTemplate.getForObject("http://localhost:8081/", String.class);

		log.info("Users fetched");
		log.info("Response from greet service: {}", greetResponse);

		return ResponseEntity.ok(users);
	}

	@GetMapping("/fetchUser/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		log.info("Fetching user by id {}", id);
		return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}