package com.zoho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zoho.bean.CustomerOrder;
import com.zoho.bean.FoodDetail;
import com.zoho.bean.Order;
import com.zoho.util.DBUtil;

public class CustomerOrderDao {
	Connection conn = null;

	public int addCustomerOrder(CustomerOrder customer) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "INSERT INTO CustomerOrder(userId,status,paymentId) VALUES(?,?,?);";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, customer.getUserId());
		psmt.setString(2, customer.getStatus());
		psmt.setInt(3, customer.getPaymentId());
		int rows = psmt.executeUpdate();
		if (rows == 0) {
			System.out.println("Problem While Inserting CustomerOrder..");
			return -1;
		}
		return rows;
	}

	public CustomerOrder getCustomerOrder(int orderId) throws ClassNotFoundException, SQLException {
		CustomerOrder customer = new CustomerOrder();
		conn = DBUtil.getConnection();
		String query = "SELECT * FROM CustomerOrder WHERE orderId=?;";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setInt(1, orderId);
		ResultSet result = psmt.executeQuery();
		if (result.next()) {
			customer.setOrderId(result.getInt(1));
			customer.setOrderDate(result.getDate(2));
			customer.setUserId(result.getString(3));
			customer.setStatus(result.getString(4));
			customer.setPaymentId(result.getInt(5));
			return customer;
		}
		return null;
	}

	public List<CustomerOrder> getAllCustomerOrder() throws ClassNotFoundException, SQLException {
		List<CustomerOrder> list = new ArrayList<>();
		conn = DBUtil.getConnection();
		String query = "SELECT * FROM CustomerOrder;";
		PreparedStatement psmt = conn.prepareStatement(query);
		ResultSet result = psmt.executeQuery();
		while (result.next()) {
			CustomerOrder customer = new CustomerOrder();
			customer.setOrderId(result.getInt(1));
			customer.setOrderDate(result.getDate(2));
			customer.setUserId(result.getString(3));
			customer.setStatus(result.getString(4));
			customer.setPaymentId(result.getInt(5));
			list.add(customer);
		}
		return list;
	}

	public boolean placeOrder(String userId, Order order) throws ClassNotFoundException, SQLException {
//		int restuarantId = requestData.get("restaurantId").getAsInt();
//		int totalAmount = requestData.get("totalAmount").getAsInt();
//		String paymentMethod = requestData.get("paymentMethod").getAsString();
//		String paymentStatus = requestData.get("paymentStatus").getAsString();
//		JsonArray foodItems = requestData.getAsJsonArray("foodItems");
		int restuarantId = order.getRestaurantId();
		int totalAmount = order.getTotalAmount();
		String paymentMethod = order.getPaymentMethod();
		String paymentStatus = order.getPaymentStatus();
		List<FoodDetail> foodItems = order.getFoodDetails();

		conn = DBUtil.getConnection();
		if (paymentStatus.equalsIgnoreCase("Pending")) {
			System.out.println("Payment Pending...");
			return false;
		}
		// Payment Details inserting Payment(paymentId,paymentStatus,paymentMethod)
		String query = "INSERT INTO Payment(paymentStatus,paymentmethod) VALUES(?,?)";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, paymentStatus);
		psmt.setString(2, paymentMethod);
		int rows = psmt.executeUpdate();
		if (rows == 0) {
			System.out.println("Problem While inserting Payment Details,..");
			return false;
		}
		// to get paymentId from payment
		query = "SELECT LAST_INSERT_ID();";
		psmt = conn.prepareStatement(query);
		ResultSet result = psmt.executeQuery();
		if (!result.next()) {
			System.out.println("Problem While getting PaymentId,..");
			return false;
		}
		int paymentId = result.getInt(1);

		// Cart Details (totalPrice)
		query = "INSERT INTO Cart(totalPrice) VALUES(?);";
		psmt = conn.prepareStatement(query);
		psmt.setInt(1, totalAmount);
		rows = psmt.executeUpdate();
		if (rows == 0) {
			System.out.println("Problem While inserting Cart Details,..");
			return false;
		}
		// to get cartId from cart
		query = "SELECT LAST_INSERT_ID();";
		psmt = conn.prepareStatement(query);
		result = psmt.executeQuery();
		if (!result.next()) {
			System.out.println("Problem While getting CartId,..");
			return false;
		}
		int cartId = result.getInt(1);

		// now insert cartFoodItemsDetails(cartId,foodId,quantity);
		for (FoodDetail element : foodItems) {
			int foodid = element.getFoodId();
			int quantity = element.getQuantity();
			// insert cartfooditems
			query = "INSERT INTO cartfooditems VALUES(?,?,?);";
			psmt = conn.prepareStatement(query);
			psmt.setInt(1, cartId);
			psmt.setInt(2, foodid);
			psmt.setInt(3, quantity);
			rows = psmt.executeUpdate();
			if (rows == 0) {
				System.out.println("Problem While inserting cartfoodItems Details,..");
				return false;
			}
		}

		// now inserting user_cart (userId,cartId)
		query = "INSERT INTO user_cart VALUES(?,?);";
		psmt = conn.prepareStatement(query);
		psmt.setString(1, userId);
		psmt.setInt(2, cartId);
		rows = psmt.executeUpdate();
		if (rows == 0) {
			System.out.println("Problem While inserting usercart Details,..");
			return false;
		}

		// now inserting CustomerOrder (orderId,orderDate,userId,status,paymentId)
		query = "INSERT INTO CustomerOrder(userId,status,paymentId) VALUES(?,?,?);";
		psmt = conn.prepareStatement(query);
		psmt.setString(1, userId);
		psmt.setString(2, "Order Placed");
		psmt.setInt(3, paymentId);
		rows = psmt.executeUpdate();
		if (rows == 0) {
			System.out.println("Problem While inserting CustomerOrder Details,..");
			return false;
		}
		// Order Placed Successfully...
		return true;
	}
}
