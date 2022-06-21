package com.learn.springbootsecuritylearn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springbootsecuritylearn.entity.User;
import com.learn.springbootsecuritylearn.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable int id) {
		return userService.getUserById(id);
	}
}
