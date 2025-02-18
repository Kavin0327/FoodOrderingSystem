package com.zoho.action;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.zoho.bean.User;
import com.zoho.dao.UserDao;
import com.zoho.util.Crypto;

public class LoginAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private User user;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private HashMap<String, Object> jsonData = new HashMap<>();

	public String execute() throws ClassNotFoundException, SQLException {
		UserDao userdao = new UserDao();
		user = userdao.validateUser(user.getUserId(), user.getPassword());
//		System.out.println("Curruser :" + user);
		if (user != null) { // if user is valid
//			System.out.println("Curruser :" + user);

			HttpSession oldSession = request.getSession(false);
			if (oldSession != null) {
				oldSession.invalidate();
			}
			System.out.println("Hello Login Action");
			HttpSession session = request.getSession();
			response.setContentType("application/type");
			session.setAttribute("curruser", user);
			addCookie(response, "username", Crypto.encryption(user.getUserId()));
			addCookie(response, "password", Crypto.encryption(user.getPassword()));
			addCookie(response, "name", Crypto.encryption(user.getName()));
			addCookie(response, "role", Crypto.encryption(user.getRole()));
			jsonData.put("status", "success");
			jsonData.put("message", "Login Successfull..!");
			return SUCCESS;
		} else {
			jsonData.put("status", "failed");
			jsonData.put("message", "Invalid Username or Password..!");
			return NONE; // if user not in DB then user is null
		}
	}

	private void addCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
//		cookie.setMaxAge(7 * 24 * 60 * 60); 
//		cookie.setHttpOnly(true); // Prevent client-side script access
		cookie.setPath("/"); // Available across the entire application
		response.addCookie(cookie);
	}

	@Override
	public void validate() {
		System.out.println("Validation Calling..");
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

	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

//	private boolean loginValidate(String username, String password) throws ClassNotFoundException, SQLException {
//		Connection con = DBUtil.getConnection();
//		if (con == null)
//			return false;
//		String query = "SELECT * FROM User WHERE userId=? AND password=?";
//		PreparedStatement psmt = con.prepareStatement(query);
//		psmt.setString(1, username);
//		psmt.setString(2, password);
//		ResultSet result = psmt.executeQuery();
//		if (result.next()) {
//			return true;
//		}
//		return false;
//	}

}
