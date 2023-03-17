package com.utility;

public class User {
	
	private Integer UserID;
	private String Username, Email, Password, Role;
	
	public User(Integer userID, String username, String email, String password, String role) {
		UserID = userID;
		Username = username;
		Email = email;
		Password = password;
		Role = role;
	}

	public Integer getUserID() {
		return UserID;
	}

	public void setUserID(Integer userID) {
		UserID = userID;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}
}
