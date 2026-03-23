package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Parent {
	
	
	@Id
	private String busId;
	private String mobileNumber;
	private String password;
	
	public Parent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Parent(String busId, String mobileNumber, String password) {
		super();
		this.busId = busId;
		this.mobileNumber = mobileNumber;
		this.password = password;
	}
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	


}
