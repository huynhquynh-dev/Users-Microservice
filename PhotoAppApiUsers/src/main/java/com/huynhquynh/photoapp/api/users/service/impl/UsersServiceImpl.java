package com.huynhquynh.photoapp.api.users.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.huynhquynh.photoapp.api.users.data.UserEntity;
import com.huynhquynh.photoapp.api.users.repository.UsersRepository;
import com.huynhquynh.photoapp.api.users.service.UsersService;
import com.huynhquynh.photoapp.api.users.shared.UserDto;
import com.huynhquynh.photoapp.api.users.ui.model.AlbumResponseModel;

@Service
public class UsersServiceImpl implements UsersService {

	UsersRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	RestTemplate restTemplate;
	Environment environment;

	@Autowired
	public UsersServiceImpl(UsersRepository userRepository, 
			BCryptPasswordEncoder bCryptPasswordEncoder,
			RestTemplate restTemplate,
			Environment environment) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.restTemplate = restTemplate;
		this.environment = environment;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {

		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

		userEntity = userRepository.save(userEntity);

		UserDto userDto = modelMapper.map(userEntity, UserDto.class);

		return userDto;
	}

//	B5: Triển khai phương thức security 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(username);

		if (userEntity == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

//	B6
	@Override
	public UserDto getUserDetailsByEmail(String email) {

		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserByUserId(String userId) {

		UserEntity entity = userRepository.findByUserId(userId);

		if (entity == null)
			throw new UsernameNotFoundException("User not found");

		UserDto userDto = new ModelMapper().map(entity, UserDto.class);

		String albumsUrl = String.format(environment.getProperty("albums.url"), userId);
//		String albumsUrl = String.format("http://ALBUMS-WS/users/%s/albums", userId);

		ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
				});
		List<AlbumResponseModel> albumsList = albumsListResponse.getBody();
		
		userDto.setAlbums(albumsList);

		return userDto;
	}

}
