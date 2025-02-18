package com.zoho.action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import com.zoho.bean.FoodItem;
import com.zoho.dao.FoodItemDao;

public class FoodItemAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private List<FoodItem> foodItems;
	private Map<Integer, List<FoodItem>> foodItemList;
	private int restaurantId;
	private int foodId;
	private String userId;
	private FoodItem fooditem;
	private boolean isManager;
	private FoodItemDao fooditemdao = new FoodItemDao();
	private HashMap<String, Object> jsonData = new HashMap<>();
	Gson gson = new Gson();

	public String getAllFoodItems() {
		jsonData.clear();
		isManager = (boolean) ServletActionContext.getRequest().getAttribute("isManager");
		try {
			System.out.println("is This Manager Access : " + isManager);
			RedisCache redis = new RedisCache();
			foodItemList = new HashMap<>();
			foodItems = new ArrayList<>();

//			foodItems = fooditemdao.getAllFoodItem(restaurantId, isManager);

			if (redis.getAllFoodItems(String.valueOf(restaurantId)) == null) {
				System.out.println("Cache Miss\n");
				foodItemList = fooditemdao.getAllFoodItems();
				redis.setAllFoodItems(foodItemList);
				String jsonFoodItems = redis.getAllFoodItems(String.valueOf(restaurantId));
				foodItems = setFoodItemList(jsonFoodItems);

			} else {
				System.out.println("Cache Hit\n");
				String jsonFoodItems = redis.getAllFoodItems(String.valueOf(restaurantId));
				List<FoodItem> finalAnswer = setFoodItemList(jsonFoodItems);
				for (FoodItem food : finalAnswer) {
					if (isManager || food.getAvailable().equals("yes")) {
						foodItems.add(food);
					}
				}
			}
//			System.out.println(restaurantId + " = " + foodItems);
			if (foodItems != null && !foodItems.isEmpty()) {
				jsonData.put("status", "success");
				jsonData.put("foodItems", foodItems);
			} else {
				jsonData.put("status", "failure");
				jsonData.put("message", "No food items found for this restaurant.");
			}
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	private List<FoodItem> setFoodItemList(String jsonString) {
		List<FoodItem> dummy = new ArrayList<>();
		Type listType = new TypeToken<List<FoodItem>>() {}.getType();
		dummy = gson.fromJson(jsonString, listType);
//		System.out.println(" This is  : " + dummy);
		return dummy;
	}

	public String addFoodItem() {
		jsonData.clear();
		try {
//			System.out.println("userId :" + userId);
//			System.out.println("restaurantId :" + restaurantId);
//			System.out.println("Food Item :" + fooditem);
			fooditem.setRestuarantId(restaurantId);
			int rows = fooditemdao.addFoodItem(fooditem);
			if (rows == -1) {
				jsonData.put("status", "error");
				jsonData.put("message", "FoodItem Not Added...!");
			} else {
				jsonData.put("status", "success");
				jsonData.put("message", "New Food Item Added Successfully...");
			}
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String deleteFoodItem() {
		jsonData.clear();
		try {
			System.out.println("FoodId : " + foodId);
			boolean flag = fooditemdao.availabilityFoodItem(foodId, "no");
			RedisCache redis = new RedisCache();
			if (flag) {
				redis.deleteCache(restaurantId);
				jsonData.put("status", "success");
				jsonData.put("foodItems", "FoodItem Deleted Successfully...!");
			} else {
				jsonData.put("status", "error");
				jsonData.put("message", "Failed to delete foodItem...!");
			}
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String enableFoodItem() {
		jsonData.clear();
		try {
			System.out.println("FoodId : " + foodId);
			boolean flag = fooditemdao.availabilityFoodItem(foodId, "yes");
			RedisCache redis = new RedisCache();
			if (flag) {
				redis.deleteCache(restaurantId);
				jsonData.put("status", "success");
				jsonData.put("foodItems", "FoodItem Enabled Successfully...!");
			} else {
				jsonData.put("status", "error");
				jsonData.put("message", "Failed to enable foodItem...!");
			}
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String getFoodItem() {
		jsonData.clear();
		try {
//			System.out.println("FoodId : " + foodId);
			fooditem = fooditemdao.getFoodItem(foodId);
			if (fooditem != null) {
				jsonData.put("status", "success");
				jsonData.put("foodItem", fooditem);
			} else {
				jsonData.put("status", "error");
			}
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String updateFoodItem() {
		jsonData.clear();
		try {
//			System.out.println("FoodItem: " + fooditem);
//			System.out.println("RestuarantID: " + restaurantId);
			fooditem.setRestuarantId(restaurantId);
			boolean flag = false;
			flag = fooditemdao.updateFoodItem(fooditem);
			if (flag) {
				jsonData.put("status", "success");
				jsonData.put("restaurantId", restaurantId);
			} else {
				jsonData.put("status", "error");
				jsonData.put("message", "Updation Failed..");
			}
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public List<FoodItem> getFoodItems() {
		return foodItems;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public HashMap<String, Object> getJsonData() {
		return jsonData;
	}

	public FoodItem getFooditem() {
		return fooditem;
	}

	public void setFooditem(FoodItem fooditem) {
		this.fooditem = fooditem;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
