package com.bean;

import org.hibernate.validator.constraints.NotBlank;

public class ForgotUserBean {
	
	@NotBlank(message = "Please enter your valid password!")
	String password;
	
	@NotBlank(message = "Please enter your valid confirm password!")
	String cpassword;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	@Override
	public String toString() {
		return "ForgotUserBean [password=" + password + ", cpassword=" + cpassword + "]";
	}
}
