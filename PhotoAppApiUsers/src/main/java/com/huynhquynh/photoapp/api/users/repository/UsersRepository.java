package com.huynhquynh.photoapp.api.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huynhquynh.photoapp.api.users.data.UserEntity;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
	
	UserEntity findByUserId(String userId);
}
