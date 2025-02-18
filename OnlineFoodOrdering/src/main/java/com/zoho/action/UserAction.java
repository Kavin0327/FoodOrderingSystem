package com.zoho.action;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zoho.bean.Address;
import com.zoho.bean.User;
import com.zoho.dao.AddressDao;
import com.zoho.dao.UserDao;
import com.zoho.util.Crypto;

public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> jsonData = new HashMap<>();
	private Address address;
	private AddressDao addressdao = new AddressDao();
	private UserDao userdao = new UserDao();
	private User cUser;

	public String getUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			jsonData.put("status", "failure");
			return ERROR;
		} else {
			try {
				String userId = "";
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("username")) {
						userId = Crypto.decryption(cookie.getValue());
					}
				}
				setcUser(userdao.getUser(userId));
				setAddress(addressdao.getAddress(cUser.getAddressId()));
				jsonData.put("status", "success");
				jsonData.put("username", cUser.getUserId());
				jsonData.put("password", cUser.getPassword());
				jsonData.put("fullname", cUser.getName());
				jsonData.put("email", cUser.getEmail());
				jsonData.put("address", address.getAddress() + " - " + address.getPincode());
				jsonData.put("landmark", address.getLandmark());
				jsonData.put("phoneNumber", cUser.getPhoneNumber());
				jsonData.put("role", cUser.getRole());
			} catch (Exception e) {
				System.out.println(e);
				jsonData.put("status", "error");
				jsonData.put("message", "Database error: " + e.getMessage());
				return ERROR;
			}
			return SUCCESS;
		}
	}

	public String updateUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			jsonData.put("status", "failure");
			return ERROR;
		} else {
			try {
				System.out.println("User: " + cUser);
				System.out.println("Address: " + address);
				
				//-----------------
				jsonData.put("status", "success");
			} catch (Exception e) {
				System.out.println(e);
				jsonData.put("status", "error");
				jsonData.put("message", "Database error: " + e.getMessage());
				return ERROR;
			}
			return SUCCESS;
		}
	}

	public HashMap<String, Object> getJsonData() {
		return jsonData;
	}

	public Address getAddress() {
		return address;
	}

	public User getcUser() {
		return cUser;
	}

	public void setAddress(Address address) {
//		System.out.println("address set..");
		this.address = address;
	}

	public void setcUser(User cUser) {
//		System.out.println("user set...");
		this.cUser = cUser;
	}

}
