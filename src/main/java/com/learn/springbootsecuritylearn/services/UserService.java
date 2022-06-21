package com.learn.springbootsecuritylearn.services;

import java.util.List;

import com.learn.springbootsecuritylearn.entity.User;

public interface UserService {

	public List<User> getAllUsers();
	public User getUserById(int id);
}
