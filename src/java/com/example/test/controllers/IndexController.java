package com.example.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.test.entities.UserEntity;
import com.example.test.jwt.JwtProvider;
import com.example.test.models.AuthRequest;
import com.example.test.models.AuthResponse;
import com.example.test.services.UserService;


@Controller
public class IndexController {
	
	@Autowired
	private UserService userService;
	@Autowired
    private JwtProvider jwtProvider;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public AuthResponse signIn(@RequestBody AuthRequest user) {
		UserEntity authUser = userService.findUser(user.getUsername(), user.getPassword());
		String token = jwtProvider.generateToken(authUser.getUsername());
        return new AuthResponse(token);
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public void signUp(@RequestBody UserEntity user) {
		userService.createUser(user);
	}
	
}
