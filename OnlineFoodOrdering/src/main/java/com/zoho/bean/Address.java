package com.zoho.bean;

public class Address {
	private int addressId;
	private String address;
	private int pincode;
	private String landmark;

	public Address(int addressId, String address, int pincode, String landmark) {
		super();
		this.addressId = addressId;
		this.address = address;
		this.pincode = pincode;
		this.landmark = landmark;
	}

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", address=" + address + ", pincode=" + pincode + ", landmark="
				+ landmark + "]";
	}

}
