package com.bean;

import org.hibernate.validator.constraints.Email;

public class EmailBean {
	
	@Email(regexp = ".+@.+\\..+",message = "Please enter a valid email.")
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmailBean [email=" + email + "]";
	}
}
