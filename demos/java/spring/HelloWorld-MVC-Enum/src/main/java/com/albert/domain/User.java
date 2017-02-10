package com.albert.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private int usrid;
	private String userName;
	private String password;
	private int credits;
	private String lastip;
	private Date lastVisit;
	public int getUsrid() {
		return usrid;
	}
	public void setUsrid(int usrid) {
		this.usrid = usrid;
	}
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
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public String getLastip() {
		return lastip;
	}
	public void setLastip(String lastip) {
		this.lastip = lastip;
	}
	public Date getLastVisit() {
		return lastVisit;
	}
	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}
	
}
