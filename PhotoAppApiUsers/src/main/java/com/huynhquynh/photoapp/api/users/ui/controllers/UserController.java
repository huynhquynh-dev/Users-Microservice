package com.huynhquynh.photoapp.api.users.ui.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@RequestMapping("/status/check")
	public String status() {
		return "Status code";
	}
}
