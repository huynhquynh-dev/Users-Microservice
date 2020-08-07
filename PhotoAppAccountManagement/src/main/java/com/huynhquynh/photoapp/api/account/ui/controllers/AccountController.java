package com.huynhquynh.photoapp.api.account.ui.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

	@RequestMapping("/status/check")
	public String status() {
		return "Working";
	}
}
