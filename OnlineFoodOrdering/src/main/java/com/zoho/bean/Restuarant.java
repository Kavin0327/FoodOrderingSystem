package com.zoho.bean;

public class Restuarant {
	private int restuarantId;
	private String restuarantName;
	private String description;
	private int addressId;
	private String address;
	private String landmark;
	private int pincode;
	private double rating;
	private String ownerName;
	private String managerId;
	private String available;

	public Restuarant(int restuarantId, String restuarantName, String description, int addressId, double rating,
			String ownerName, String managerId, String available) {
		super();
		this.restuarantId = restuarantId;
		this.restuarantName = restuarantName;
		this.description = description;
		this.addressId = addressId;
		this.rating = rating;
		this.ownerName = ownerName;
		this.managerId = managerId;
		this.available = available;
	}

	public Restuarant() {
		// TODO Auto-generated constructor stub
	}

	public int getRestuarantId() {
		return restuarantId;
	}

	public void setRestuarantId(int restuarantId) {
		this.restuarantId = restuarantId;
	}

	public String getRestuarantName() {
		return restuarantName;
	}

	public void setRestuarantName(String restuarantName) {
		this.restuarantName = restuarantName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getManagerId() {
		return managerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String lankmark) {
		this.landmark = lankmark;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Restuarant [restuarantId=" + restuarantId + ", restuarantName=" + restuarantName + ", description="
				+ description + ", addressId=" + addressId + ", Restuarant rating=" + rating + ", ownerName="
				+ ownerName + ", managerId=" + managerId + ", available=" + available + "]";
	}

}
