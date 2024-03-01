package com.example.demo.models;

public class LoginRequest {
    private String emailOrMobileNumber;
    private String password;

    // Constructors, getters, setters

    public LoginRequest() {
    }

    public LoginRequest(String emailOrMobileNumber, String password) {
        this.emailOrMobileNumber = emailOrMobileNumber;
        this.password = password;
    }

	public String getEmailOrMobileNumber() {
		return emailOrMobileNumber;
	}

	public void setEmailOrMobileNumber(String emailOrMobileNumber) {
		this.emailOrMobileNumber = emailOrMobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    
    
}
