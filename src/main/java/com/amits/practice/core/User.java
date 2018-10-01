package com.amits.practice.core;

/**
 * @author amits1
 *
 * 20-Sep-2018
 */
public class User {

	private String userName;
	private String justName;
	
	public User(String userName) {
		this.userName = userName;
	}
	
	public User(String userName, String justName) {
		this.userName = userName;
		this.justName = justName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getJustName() {
		return justName;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", justName=" + justName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
}
