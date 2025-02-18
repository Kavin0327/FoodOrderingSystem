package com.zoho.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zoho.bean.User;
import com.zoho.util.Crypto;
import com.zoho.util.DBUtil;

public class checkUserAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
//	private HttpServletResponse response;
//	private HttpServletRequest request;

	private User user;
	private HashMap<String, Object> jsonData = new HashMap<>();

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Cookie[] cookies = request.getCookies();
		String role = "";
		String name = "";
		jsonData.clear();
		HttpSession session = request.getSession(false);
//		System.out.println("THis inside execute method");
		if (cookies != null && session != null) {
//			System.out.println("Available Cookie and Session");
			jsonData.put("status", "success");
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("name")) {
					name = Crypto.decryption(cookie.getValue());
					jsonData.put("username", name);
				}
				if (cookie.getName().equals("role")) {
					role = Crypto.decryption(cookie.getValue());
					jsonData.put("role", role);
				}
			}
			if (role.equals("admin")) {
				jsonData.put("allowedActions",
						new String[] { "showProfile", "showRestaurant", "addManager", "addRestuarant" });
			} else if (role.equals("manager")) {
				jsonData.put("allowedActions", new String[] { "showProfile", "listRestaurant" });
			} else if (role.equals("customer")) {
				jsonData.put("allowedActions", new String[] { "showProfile", "showRestaurant" });
			}
			return SUCCESS;
		} else {
//			System.out.println("No Cookie..");
			jsonData.put("status", "failed");
			jsonData.put("message", "no user Login");
			return LOGIN;
		}
	}

	public String checkUserName() {
		jsonData.clear();
//		System.out.println("chek User Name() called..\n" + user);
		if (user == null) {
			jsonData.put("status", "error");
			jsonData.put("message", "User object is null");
			return ERROR;
		}
		String Query = "Select * from user where userId = ?";
		Connection con;
		try {
			con = DBUtil.getConnection();
			PreparedStatement psmt = con.prepareStatement(Query);
			psmt.setString(1, user.getUserId());
			ResultSet result = psmt.executeQuery();
			jsonData.clear();
			if (result.next()) {
				// User already exists
				jsonData.put("status", "error");
				jsonData.put("message", "Sorry, User already exists...");
			} else {
				// Valid username
				jsonData.put("status", "success");
				jsonData.put("message", "Valid UserName...");
			}
		} catch (Exception e) {
			System.out.println(e);
			jsonData.put("status", "error");
			jsonData.put("message", "Database error: " + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HashMap<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(HashMap<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

//	@Override
//	public void setServletResponse(HttpServletResponse response) {
//		// TODO Auto-generated method stub
//		this.response = response;
//	}
//
//	@Override
//	public void setServletRequest(HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		this.request = request;
//	}

}
