package com.bean;

import org.hibernate.validator.constraints.NotBlank;

public class LabelBean {
	private int labelId;
	
	@NotBlank(message = "Please enter valid label name!")
	private String labelName;
	LoginBean user;
	public int getLabelId() {
		return labelId;
	}
	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public LoginBean getUser() {
		return user;
	}
	public void setUser(LoginBean user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "LabelBean [labelId=" + labelId + ", labelName=" + labelName + ", user=" + user + "]";
	}
	
}
