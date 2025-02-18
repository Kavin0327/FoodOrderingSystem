package com.zoho.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.zoho.bean.Address;
import com.zoho.bean.Restuarant;
import com.zoho.dao.AddressDao;
import com.zoho.dao.RestuarantDao;
import com.zoho.util.Crypto;

public class RestaurantAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private List<Restuarant> restaurantList;
	private HashMap<String, Object> jsonData = new HashMap<>();
	private Restuarant restaurant;
	private Address address;
	private int restaurantId;

	private RestuarantDao restaurantdao = new RestuarantDao();
	private AddressDao addressdao = new AddressDao();

	public String getAllRestaurant() {
		jsonData.clear();
		try {
//			restaurantList = restaurantdao.getAllRestuarant();
			RedisCache redis = new RedisCache();
			restaurantList = new ArrayList<>();
			if (redis.getAllRestaurants().isEmpty()) {
				System.out.println("Cache Miss\n");
				restaurantList = restaurantdao.getAllRestuarant();
				redis.setAllRestaurants(restaurantList);
			} else {
				System.out.println("Cache Hit\n");
				Map<String, String> cachedRestaurants = redis.getAllRestaurants();
				for (String jsonData : cachedRestaurants.values()) {
					Restuarant restaurant = new Gson().fromJson(jsonData, Restuarant.class);
					restaurantList.add(restaurant);
				}
				restaurantList.sort((x, y) -> Double.compare(y.getRating(), x.getRating()));
			}
			System.out.println("Restuarant List: " + restaurantList);
			jsonData.put("status", "success");
			jsonData.put("restaurantList", restaurantList);
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String addRestaurant() {
		jsonData.clear();
		try {
			System.out.println("Restaurant: " + restaurant);
			System.out.println("Restaurant Address : " + address);
			int addId = -1;
			addId = addressdao.addAddress(address);
			int rows = -1;
			if (addId != -1) {
				restaurant.setAddressId(addId);
				rows = restaurantdao.addRestuarant(restaurant);
			}

			if (rows != -1) {
				jsonData.put("status", "success");
				jsonData.put("message", "Restaurant Added Succesfully..");
			} else {
				jsonData.put("status", "error");
				jsonData.put("message", "Restaurant not Added..");
				return INPUT;
			}
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String getAssignedRestaurants() {
		jsonData.clear();
		String userId = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		Cookie[] cookies = request.getCookies();
		HttpSession session = request.getSession(false);
//		System.out.println("THis inside execute method");
		if (cookies != null && session != null) {
//			System.out.println("Available Cookie and Session");
			jsonData.put("status", "success");
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					userId = Crypto.decryption(cookie.getValue());
				}
			}
		} else {
//			System.out.println("No Cookie..");
			jsonData.put("status", "failed");
			jsonData.put("message", "no user Login");
			return LOGIN;
		}
		try {
			restaurantList = restaurantdao.getAllAssignedRestuarant(userId);
//			System.out.println("Manager : "+restaurantList);
			jsonData.put("status", "success");
			jsonData.put("restaurantList", restaurantList);
			if (restaurantList.isEmpty()) {
				jsonData.put("status", "error");
				jsonData.put("message", "No Restaurants assign to you...");
				return ERROR;
			}
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String removeRestaurant() {
		jsonData.clear();
		RedisCache redis = new RedisCache();
		try {
			boolean flag = restaurantdao.removeRestuarant(restaurantId);
			if (flag) {
				redis.deleteRestaurantCache();
				jsonData.put("status", "success");
				jsonData.put("foodItems", "Restaurant Deleted Successfully...!");
			} else {
				jsonData.put("status", "error");
				jsonData.put("message", "Failed to delete Restaurant...!");
			}
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
	
	

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Restuarant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restuarant restaurant) {
		this.restaurant = restaurant;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public HashMap<String, Object> getJsonData() {
		return jsonData;
	}

}
