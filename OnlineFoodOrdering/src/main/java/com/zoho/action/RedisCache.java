package com.zoho.action;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.zoho.bean.FoodItem;
import com.zoho.bean.Restuarant;

import redis.clients.jedis.Jedis;

public class RedisCache {
	private Jedis redis;
	private Gson gson;

	public RedisCache() {
		redis = new Jedis("localhost", 6379);
		gson = new Gson();
	}

	// for Restaurants ----------------------

	public void setAllRestaurants(List<Restuarant> restaurantList) {
		for (Restuarant restaurant : restaurantList) {
			String jsonData = gson.toJson(restaurant);
			redis.hset("Restaurants", String.valueOf(restaurant.getRestuarantId()), jsonData);
		}
		redis.expire("Restaurants", 300); // for 5 minutes
	}

	public Map<String, String> getAllRestaurants() {
		return redis.hgetAll("Restaurants");
	}

	public long deleteRestaurantCache() {
		System.out.println("Restaurants Cache Cleared:--");
		return redis.del("Restaurants");
	}

	// for FoodItems --------------------------

//	public void setAllFoodItems(int restaurantId, List<FoodItem> foodItemList) {
//		for (FoodItem fooditem : foodItemList) {
//			String jsonData = gson.toJson(fooditem);
//			redis.hset("Restaurant:" + restaurantId, String.valueOf(fooditem.getFoodId()), jsonData);
//		}
//		redis.expire("Restaurant:" + restaurantId, 120);
//	}
//
//	public Map<String, String> getAllFoodItems(int restaurantId) {
//		return redis.hgetAll("Restaurant:" + restaurantId);
//	}
//
//	public long deleteCache(int restaurantId) {
//		System.out.println("FoodItem List Cache Cleared :" + restaurantId);
//		return redis.del("Restaurant:" + restaurantId);
//	}

	public void close() {
		redis.close();
	}

	// FoodItemLists -----

	public String getAllFoodItems(String rId) {
		return redis.hget("Restaurant_foodItems", rId);
	}

	public void setAllFoodItems(Map<Integer, List<FoodItem>> allFoodItems) {
		for (Map.Entry<Integer, List<FoodItem>> rfoodItem : allFoodItems.entrySet()) {
//			System.out.println("All Food Items : "+rfoodItem.getValue());
			String jsonData = gson.toJson(rfoodItem.getValue(), List.class);
			redis.hset("Restaurant_foodItems", String.valueOf(rfoodItem.getKey()), jsonData);
		}
		redis.expire("Restaurant_foodItems", 300); // for 5 minutes

	}

	public long deleteCache(int restaurantId) {
		System.out.println("FoodItem List Cache Cleared :" + restaurantId);
		return redis.hdel("Restaurant_foodItems", String.valueOf(restaurantId));
	}

}