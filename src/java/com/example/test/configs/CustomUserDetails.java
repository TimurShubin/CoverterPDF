package com.example.test.configs;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.test.entities.UserEntity;

public class CustomUserDetails implements UserDetails {

	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> grantedAuthorities;
	
	public static CustomUserDetails fromUserModelToCustomUserDetails(UserEntity user) {
		CustomUserDetails c = new CustomUserDetails();
		c.username = user.getUsername();
		c.password = user.getPassword();
		c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRoleId().getName()));
		return c;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
