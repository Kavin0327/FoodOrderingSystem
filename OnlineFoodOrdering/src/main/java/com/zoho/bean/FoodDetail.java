package com.zoho.bean;

public class FoodDetail {
	private int foodId;
	private String foodName;
	private int quantity;
	private int price;

	// No-arg constructor
	public FoodDetail() {
	}

	// Getters and setters
	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "FoodDetail [foodId=" + foodId + ", foodName=" + foodName + ", quantity=" + quantity + ", price=" + price
				+ "]";
	}
}