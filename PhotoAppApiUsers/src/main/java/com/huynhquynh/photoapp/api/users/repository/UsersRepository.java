package com.huynhquynh.photoapp.api.users.repository;

import org.springframework.data.repository.CrudRepository;

import com.huynhquynh.photoapp.api.users.data.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
}
