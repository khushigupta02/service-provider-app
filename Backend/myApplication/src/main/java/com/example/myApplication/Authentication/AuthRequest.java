package com.example.myApplication.Authentication;

import com.example.myApplication.Entity.Role;

public class AuthRequest {
	private String userName;

	private String password;
	private Role userType;

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

	public Role getUserType() {
		return userType;
	}

	public void setUserType(Role userType) {
		this.userType = userType;
	}
}