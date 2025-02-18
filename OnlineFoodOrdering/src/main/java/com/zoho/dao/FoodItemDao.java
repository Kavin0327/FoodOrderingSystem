package com.zoho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zoho.bean.FoodItem;
import com.zoho.util.DBUtil;

public class FoodItemDao {
	Connection conn = null;

	public int addFoodItem(FoodItem fooditem) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "INSERT INTO foodItem(foodName,description,rating,price,restuarantId) VALUES(?,?,?,?,?);";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, fooditem.getFoodName());
		psmt.setString(2, fooditem.getDescription());
		psmt.setDouble(3, fooditem.getRating());
		psmt.setInt(4, fooditem.getPrice());
		psmt.setInt(5, fooditem.getRestuarantId());
		int rows = psmt.executeUpdate();
		if (rows == 0) {
			return -1;
		}
		return 1;
	}

	public FoodItem getFoodItem(int foodId) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		FoodItem fooditem = new FoodItem();
		String query = "SELECT * FROM FoodItem WHERE foodId=? AND available='yes';";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setInt(1, foodId);
		ResultSet result = psmt.executeQuery();
		if (result.next()) {
			fooditem.setFoodId(result.getInt(1));
			fooditem.setFoodName(result.getString(2));
			fooditem.setDescription(result.getString(3));
			fooditem.setRating(result.getDouble(4));
			fooditem.setPrice(result.getInt(5));
			fooditem.setRestuarantId(result.getInt(6));
			return fooditem;
		}
		return null;
	}

	public boolean updateFoodItem(FoodItem fooditem) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
//		System.out.println("Food name: "+fooditem.getFoodName());
		String query = "UPDATE FoodItem SET foodName=?,description=?,rating=?,price=? WHERE foodId=?;";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, fooditem.getFoodName());
		psmt.setString(2, fooditem.getDescription());
		psmt.setDouble(3, fooditem.getRating());
		psmt.setInt(4, fooditem.getPrice());
		psmt.setInt(5, fooditem.getFoodId());
		int row = psmt.executeUpdate();
		if (row == 0) {
			return false;
		}
		return true;
	}

	public List<FoodItem> getAllFoodItem(int rId, boolean isManager) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
//		String query = "SELECT * FROM FoodItem WHERE restuarantId=? AND available= 'yes';";
		String query = "SELECT * FROM FoodItem WHERE restuarantId=? ;";

//		if (isManager) {
//			query = "SELECT * FROM FoodItem WHERE restuarantId=?;";
//		}

		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setInt(1, rId);
		ResultSet result = psmt.executeQuery();
		List<FoodItem> list = new ArrayList<>();
		while (result.next()) {
			FoodItem fooditem = new FoodItem();
			fooditem.setFoodId(result.getInt(1));
			fooditem.setFoodName(result.getString(2));
			fooditem.setDescription(result.getString(3));
			fooditem.setRating(result.getDouble(4));
			fooditem.setPrice(result.getInt(5));
			fooditem.setRestuarantId(result.getInt(6));
			fooditem.setAvailable(result.getString(7));
			list.add(fooditem);
		}
		return list;
	}

	public Map<Integer, List<FoodItem>> getAllFoodItems() throws ClassNotFoundException, SQLException {
		Map<Integer, List<FoodItem>> foodItems = new HashMap<>();
		conn = DBUtil.getConnection();
		String query = "SELECT * FROM FoodItem;";
		PreparedStatement psmt = conn.prepareStatement(query);
		ResultSet result = psmt.executeQuery();
		while (result.next()) {
			FoodItem fooditem = new FoodItem();
			fooditem.setFoodId(result.getInt(1));
			fooditem.setFoodName(result.getString(2));
			fooditem.setDescription(result.getString(3));
			fooditem.setRating(result.getDouble(4));
			fooditem.setPrice(result.getInt(5));
			fooditem.setRestuarantId(result.getInt(6));
			fooditem.setAvailable(result.getString(7));
			foodItems.putIfAbsent(fooditem.getRestuarantId(), new ArrayList<>());
			foodItems.get(fooditem.getRestuarantId()).add(fooditem);
		}
		return foodItems;
	}

	public boolean availabilityFoodItem(int foodId, String status) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "UPDATE FoodItem SET available=? WHERE foodId=?;";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, status);
		psmt.setInt(2, foodId);
		int rows = psmt.executeUpdate();
		if (rows == 0) {
			return false;
		}
		return true;
	}
}
