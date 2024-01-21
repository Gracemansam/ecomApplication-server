package com.sam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.exception.UserException;
import com.sam.model.User;
import com.sam.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {
	

	private UserService userService;

	public AdminUserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String jwt) throws UserException{

		System.out.println("/api/users/profile");
		List<User> user=userService.findAllUsers();
		return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
	}


}
