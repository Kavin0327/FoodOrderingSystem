package com.zoho.bean;

public class User {
	private String userId;
	private String name;
	private String password;
	private int addressId;
	private String email;
	private String phoneNumber;
	private String role;

	public User(String userId, String name, String password, int addressId, String email, String phoneNumber,
			String role) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.addressId = addressId;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [ userId=" + userId + ", name=" + name + ", password=" + password + ", addressId=" + addressId
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", role=" + role + " ]";
	}

}
