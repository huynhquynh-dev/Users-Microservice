package com.huynhquynh.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.huynhquynh.photoapp.api.users.shared.UserDto;

//B4: extends UserDetailsService
public interface UsersService extends UserDetailsService {

	UserDto createUser( UserDto userDetails);
	
	UserDto getUserDetailsByEmail( String email);
	
	UserDto getUserById(String userId);
}
