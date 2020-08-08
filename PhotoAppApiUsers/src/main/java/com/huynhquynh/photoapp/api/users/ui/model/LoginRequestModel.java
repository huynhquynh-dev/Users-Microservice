package com.huynhquynh.photoapp.api.users.ui.model;

public class LoginRequestModel {

//	@NotNull(message = "Email name cannot be null")
//	@Email
	private String email;
	
//	@NotNull(message = "First name cannot be null")
//	@Size(min = 8, max = 16, message = "Password must be equals or grater than 8 characters and less than 16 characters")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
