package com.learn.springbootsecuritylearn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.springbootsecuritylearn.entity.User;
import com.learn.springbootsecuritylearn.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public User getUserById(int id) {
		
		return userRepository.findById(id).orElse(null);
	}

}
