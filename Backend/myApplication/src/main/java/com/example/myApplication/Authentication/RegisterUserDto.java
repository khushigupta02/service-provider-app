package com.example.myApplication.Authentication;

import com.example.myApplication.Entity.Role;

public class RegisterUserDto {
	private String firstName;

	private String lastName;

	private String userName;
	private String password;
	private String email;
	private Role userType;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getUserType() {
		return userType;
	}

	public void setUserType(Role userType) {
		this.userType = userType;
	}
}