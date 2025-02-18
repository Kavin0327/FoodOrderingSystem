package com.zoho.action;

import java.sql.SQLException;
import java.util.HashMap;

import com.opensymphony.xwork2.ActionSupport;
import com.zoho.bean.Address;
import com.zoho.bean.User;
import com.zoho.dao.AddressDao;
import com.zoho.dao.UserDao;

public class RegisterAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private User user;
	private Address address;
	private HashMap<String, Object> jsonData = new HashMap<>();

	public String execute() throws ClassNotFoundException, SQLException {
		AddressDao addressDao = new AddressDao();
		UserDao userDao = new UserDao();
		boolean flag = false;
		System.out.println("Address: " + address);
		System.out.println("User : " + user);
		int addId = -1;
//		addId = addressDao.addAddress(address);
		
		if(addId == -1) {
			flag = false;
		}else {
			user.setAddressId(addId);
//			user.setRole("customer");
			flag = userDao.addUser(user);
		}
		if (flag) {
			jsonData.put("status", "success");
			jsonData.put("message", " Created Sucessfully..!");
			return SUCCESS;
		} else {
			jsonData.put("status", "failed");
			jsonData.put("message", " not Created..!");
			return ERROR;
		}
	}

	public void setJsonData(HashMap<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

	public HashMap<String, Object> getJsonData() {
		return jsonData;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
