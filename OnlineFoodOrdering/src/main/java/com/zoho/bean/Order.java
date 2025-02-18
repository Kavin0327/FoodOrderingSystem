package com.zoho.bean;

import java.util.List;

public class Order {
	private int restaurantId;
	private List<FoodDetail> foodDetails;
	private int totalAmount;
	private String paymentMethod;
	private String paymentStatus;

	public Order() {
	}

	// Getters and setters
	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public List<FoodDetail> getFoodDetails() {
		return foodDetails;
	}

	public void setFoodDetails(List<FoodDetail> foodDetails) {
		this.foodDetails = foodDetails;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public String toString() {
		return "Order [restaurantId=" + restaurantId + ", foodDetails=" + foodDetails + ", totalAmount=" + totalAmount
				+ ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + "]";
	}
}