package com.zoho.bean;

public class FoodItem {
	private int foodId;
	private String foodName;
	private String description;
	private double rating;
	private int price;
	private int restuarantId;
	private String available;

	public FoodItem(int foodId, String foodName, String description, double rating, int price, int restuarantId,
			String available) {
		super();
		this.foodId = foodId;
		this.foodName = foodName;
		this.description = description;
		this.rating = rating;
		this.price = price;
		this.restuarantId = restuarantId;
		this.available = available;
	}

	public FoodItem() {
		// TODO Auto-generated constructor stub
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRestuarantId() {
		return restuarantId;
	}

	public void setRestuarantId(int restuarantId) {
		this.restuarantId = restuarantId;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "FoodItem [foodId=" + foodId + ", foodName=" + foodName + ", description=" + description + ", rating="
				+ rating + ", price=" + price + ", restuarantId=" + restuarantId + ", available=" + available + "]";
	}

}
