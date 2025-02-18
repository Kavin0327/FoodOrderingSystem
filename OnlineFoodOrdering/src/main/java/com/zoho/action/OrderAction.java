package com.zoho.action;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.zoho.bean.Order;
import com.zoho.bean.User;
import com.zoho.dao.CustomerOrderDao;
import com.zoho.util.Crypto;

public class OrderAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

//	private int restaurantId;
//	private List<FoodDetail> foodDetails;
//	private int totalAmount;
//	private String paymentMethod;
//	private String paymentStatus;

	private Map<String, Object> jsonData = new HashMap<>();

	public String placeOrder() {
		String userID = "";
		try {

			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("curruser");
			Cookie[] cookies = request.getCookies();
			if (cookies == null) {
				jsonData.put("status", "error");
				jsonData.put("message", "Order failed due to Session validation failed");
				return ERROR;
			}
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					userID = Crypto.decryption(cookie.getValue());
				}
			}
			
			if(!userID.equals(user.getUserId())) {
				jsonData.put("status", "error");
				jsonData.put("message", "Order failed due to cookie modification, so session validation failed");
				return ERROR;
			}
			BufferedReader reader = request.getReader();
			StringBuilder jsonBuffer = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonBuffer.append(line);
			}
			String json = jsonBuffer.toString();

			Order order = new Gson().fromJson(json, Order.class);

//			this.restaurantId = order.getRestaurantId();
//			this.foodDetails = order.getFoodDetails();
//			this.totalAmount = order.getTotalAmount();
//			this.paymentMethod = order.getPaymentMethod();
//			this.paymentStatus = order.getPaymentStatus();
//
//			System.out.println("Received RestaurantId = " + order.getRestaurantId());
//			System.out.println("Received FoodDetails = " + order.getFoodDetails());
//			System.out.println("Received TotalAmount = " + order.getTotalAmount());
//			System.out.println("Received PaymentMethod = " + order.getPaymentMethod());
//			System.out.println("Received PaymentStatus = " + order.getPaymentStatus());
			boolean orderstatus = false;
			System.out.println("Received Order: " + order);
			CustomerOrderDao customerorderdao = new CustomerOrderDao();
			orderstatus = customerorderdao.placeOrder(userID, order);
			jsonData.put("status", orderstatus ? "success" : "error");
			jsonData.put("message", orderstatus ? "Order placed successfully!" : "Order could not be placed.");
			return SUCCESS;
		} catch (Exception e) {
			jsonData.put("status", "error");
			jsonData.put("message", "Order failed: " + e.getMessage());
			return ERROR;
		}
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

}
