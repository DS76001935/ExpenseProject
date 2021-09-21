package com.bean;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class UserBean {
	private int userId;
	
	@NotBlank(message = "Please enter valid first name!")
	private String firstName;
	
	@Size(max = 10,min = 10,message = "Please enter valid mobile number!")
	private String phone;
	
	@NotBlank(message = "Please select your valid sex type!")
	private String gender;
	
	@Email(regexp = ".+@.+\\..+",message = "Please enter a valid email.")
	private String email;
	@Length(min = 8,max = 16,message = "Length must be between 8 to 16 digit.")
	private String password;
	
	private boolean flag;
	
	RoleBean role;
	
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
		return "UserBean [userId=" + userId + ", firstName=" + firstName + ", phone=" + phone + ", gender=" + gender
				+ ", email=" + email + ", password=" + password + ", flag=" + flag + ", role=" + role + "]";
	}
}
