package com.bean;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class LoginBean {
	private int userId;
	
	private String firstName;
	
	private String phone;
	
	private String gender;
	
	@Email(regexp = ".+@.+\\..+",message = "Please enter a valid email.")
	private String email;
	@Length(min = 8,max = 16,message = "Length must be between 8 to 16 digit.")
	private String password;
	
	RoleBean role;
	
	boolean flag;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
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
	public RoleBean getRole() {
		return role;
	}
	public void setRole(RoleBean role) {
		this.role = role;
	}
	public boolean getFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "LoginBean [userId=" + userId + ", firstName=" + firstName + ", phone=" + phone + ", gender=" + gender
				+ ", email=" + email + ", password=" + password + ", role=" + role + ", flag=" + flag + "]";
	}
}
