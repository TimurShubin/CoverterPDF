package com.example.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.test.entities.RoleEntity;
import com.example.test.entities.UserEntity;
import com.example.test.repositories.RoleRepository;
import com.example.test.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserEntity createUser(UserEntity user) {
		RoleEntity userRole = roleRepository.findByName("ROLE_USER");
		user.setRoleId(userRole);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public UserEntity findUser(String username, String password) {
		UserEntity user = userRepository.findByUsername(username);
		if (user != null) {
			if (passwordEncoder.matches(password, user.getPassword())) {
				return user;
			}
		}
		return null;
	}
	
	public UserEntity findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
