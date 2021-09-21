package com.bean;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class Expense {
	private int expenseId;
	
	@NotNull(message= "Please enter your valid contact number!")
	private long amount;
	
//	@DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
//	@NotBlank(message = "Please select appropriate date and time!")
	private Date expDateTime;
	private int years;
	private int months;
	
	@NotBlank(message = "Please choose a valid status!")
	private String status;
	
	@NotBlank(message = "Please write correct description!")
	private String description;
	
	private String paymentMethod;
	
	private String accName;
	
//	@NotBlank(message = "Please enter the valid sub category!")
//	String input_subCategory;
	
	LabelBean label;
	CategoryBean category;
	SubcategoryBean subCategory;
	PayeeBean payee;
	LoginBean user;
	
	public int getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	public Date getExpDateTime() {
		return expDateTime;
	}
	public void setExpDateTime(Date expDateTime) {
		this.expDateTime = expDateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public LabelBean getLabel() {
		return label;
	}
	public void setLabel(LabelBean label) {
		this.label = label;
	}
	public CategoryBean getCategory() {
		return category;
	}
	public void setCategory(CategoryBean category) {
		this.category = category;
	}
	public PayeeBean getPayee() {
		return payee;
	}
	public void setPayee(PayeeBean payee) {
		this.payee = payee;
	}
	public LoginBean getUser() {
		return user;
	}
	public void setUser(LoginBean user) {
		this.user = user;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public int getYears() {
		return years;
	}
	public void setYears(int years) {
		this.years = years;
	}
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public SubcategoryBean getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(SubcategoryBean subCategory) {
		this.subCategory = subCategory;
	}
	@Override
	public String toString() {
		return "Expense [expenseId=" + expenseId + ", amount=" + amount + ", expDateTime=" + expDateTime + ", years="
				+ years + ", months=" + months + ", status=" + status + ", description=" + description
				+ ", paymentMethod=" + paymentMethod + ", accName=" + accName + ", label=" + label + ", category="
				+ category + ", subCategory=" + subCategory + ", payee=" + payee + ", user=" + user + "]";
	}
}
