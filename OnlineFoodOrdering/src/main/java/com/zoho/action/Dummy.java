package com.zoho.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.zoho.bean.FoodItem;
import com.zoho.dao.FoodItemDao;
import com.zoho.dao.RestuarantDao;

public class Dummy {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		RedisCache redis = new RedisCache();
		RestuarantDao restaurantdao = new RestuarantDao();

//		List<Restuarant> restaurantList = new ArrayList<>();
//
//		if (redis.getAllRestaurants().isEmpty()) {
//			System.out.println("Cache Miss");
//			restaurantList = restaurantdao.getAllRestuarant();
//			redis.setAllRestaurants(restaurantList);
//		} else {
//			System.out.println("Cache Hit");
//			Map<String, String> cachedRestaurants = redis.getAllRestaurants();
//			for (String jsonData : cachedRestaurants.values()) {
//				Restuarant restaurant = new Gson().fromJson(jsonData, Restuarant.class);
//				restaurantList.add(restaurant);
//			}
//		}
//
//		System.out.println("Available Restaurants List : \n");
//		System.out.println(restaurantList);
		FoodItemDao fooditemdao = new FoodItemDao();
		List<FoodItem> foodItemLists = new ArrayList<>();
//
//		if (redis.getAllFoodItems(500).isEmpty()) {
//			System.out.println("Cache Miss");
//			foodItemList = fooditemdao.getAllFoodItem(500, false);
//			redis.setAllFoodItems(500, foodItemList);
//		} else {
//			System.out.println("Cache Hit");
//			Map<String, String> cacheFoodItems = redis.getAllFoodItems(500);
//			for (String jsonData : cacheFoodItems.values()) {
//				FoodItem fooditem = new Gson().fromJson(jsonData, FoodItem.class);
//				foodItemList.add(fooditem);
//			}
//		}
//		System.out.println("Available Food Items:\n");
//		System.out.println(foodItemList);
//
//		System.out.println("Deleted Entries: " + redis.deleteCache(500));

//		Map<Integer, Map<Integer, FoodItem>> foodLists = new HashMap<>();
		
		
		
		Map<Integer, List<FoodItem>> foodLists = new HashMap<>();
//		System.out.println(fooditemdao.getAllFoodItems());
		if (redis.getAllFoodItems(500 + "") == null) {
			System.out.println("Cache Miss\n");
			foodLists = fooditemdao.getAllFoodItems();
			redis.setAllFoodItems(foodLists);
		} else {
			System.out.println("Cache Hit\n");
			Map<String, String> cacheFoodItems;
			String jsonData = redis.getAllFoodItems(501 + "");
			foodItemLists = new Gson().fromJson(jsonData, List.class);
			System.out.println(foodItemLists);
		}
		System.out.println("\nAvailable Food Items:\n");
		System.out.println(foodItemLists);

//		System.out.println("Deleted Entries: " + redis.deleteCache(500));

	}

}
