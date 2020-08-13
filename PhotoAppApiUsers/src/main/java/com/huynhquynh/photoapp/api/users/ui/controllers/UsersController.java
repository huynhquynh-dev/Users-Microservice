package com.huynhquynh.photoapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huynhquynh.photoapp.api.users.service.UsersService;
import com.huynhquynh.photoapp.api.users.shared.UserDto;
import com.huynhquynh.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.huynhquynh.photoapp.api.users.ui.model.CreateUserResponseModel;
import com.huynhquynh.photoapp.api.users.ui.model.UserResponseModel;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	UsersService usersService;
 
//    @Value("${server.port}")
//    private String port;

	@RequestMapping("/status/check")
	public String status() {
		return "Working on port " + env.getProperty("server.port") + " with token " + env.getProperty("token.secret");
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
			)
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		userDto = usersService.createUser(userDto);
		
		CreateUserResponseModel responseModel = modelMapper.map(userDto, CreateUserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
		
		UserDto userDto = usersService.getUserById(userId);
		
		UserResponseModel responseModel = new ModelMapper().map(userDto, UserResponseModel.class);
		
		return new ResponseEntity<UserResponseModel>(responseModel, HttpStatus.OK);
	}
}
