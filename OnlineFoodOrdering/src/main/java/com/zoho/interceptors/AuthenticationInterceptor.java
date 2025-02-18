package com.zoho.interceptors;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zoho.bean.User;
import com.zoho.dao.UserDao;

public class AuthenticationInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,15}$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
	private static final Pattern MOBILE_PATTERN = Pattern.compile("^\\d{10}$");
	private static final Pattern PINCODE = Pattern.compile("^\\d{6}$");
	private static final Pattern NAME = Pattern.compile("^[a-zA-Z\\s]$");

	private Set<String> validUrls = new HashSet<>();
	private Map<String, String> jsonData = new HashMap<>();

	@Override
	public void init() {
	}

	User user = null;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
//		Map<String, Object> session = invocation.getInvocationContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Cookie cookies[] = request.getCookies();
		String actionName = invocation.getInvocationContext().getActionName();
		String requestURL = request.getRequestURI().substring(request.getContextPath().length());
		Gson gson = new Gson();
		System.out.println("\n\nInterceptor URL : " + requestURL);
		System.out.println("Action Class Called : " + actionName);
		HttpSession session = request.getSession(false);
		Boolean isAuthenticated = (Boolean) request.getAttribute("isAuthenticated");
		Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
		Boolean isManager = (Boolean) request.getAttribute("isManager");
		System.out.println("isAuthentication : " + isAuthenticated);
		System.out.println("isAdmin : " + isAdmin);
		System.out.println("isManager : " + isManager + "\n\n");

		if (actionName.equals("loginAction")) { // login Form
			String uName = request.getParameter("user.userId");
			String password = request.getParameter("user.password");
			jsonData.clear();
			// Validate inputs
			if (uName == null || password == null || uName.isEmpty() || password.isEmpty()) {
				sendResponse(response, "failed", "Please enter valid login credentials and try again.");
				return null;
			}
		}

		if (actionName.equals("RegisterAction")) {
			String uName = request.getParameter("user.userId");
			String name = request.getParameter("user.name");
			String password = request.getParameter("user.password");
			String address = request.getParameter("address.address");
			String pincode = request.getParameter("address.pincode");
			String landmark = request.getParameter("address.landmark");
			String email = request.getParameter("user.email");
			String phoneNumber = request.getParameter("user.phoneNumber");
			String role = request.getParameter("user.role");
			// Register inputs validate
			if (name == null || name.length() == 0 || password == null || password.length() == 0 || address == null
					|| address.length() == 0 || email == null || email.length() == 0 || phoneNumber == null
					|| phoneNumber.length() == 0) {
				sendResponse(response, "failed", "Missing required fields");
				return null;
			}
			if (!isValidPincode(pincode) || !isValidUserName(uName) || !isValidEmail(email)
					|| !isValidMobileNumber(phoneNumber)) {
				System.out.println("Here invalid");
				sendResponse(response, "failed", "Mismatch input given");
				return null;
			}

		}

		if (actionName.equals("RegisterAction")) {
			
		}

		System.out.println("Helloo..");

		return invocation.invoke();
	}

	private boolean isValidName(String name) {

		return name != null && NAME.matcher(name).matches();
	}

	private void sendResponse(HttpServletResponse response, String status, String message) throws IOException {
		response.setContentType("application/json");
		jsonData.put("status", status);
		jsonData.put("message", message);
		response.getWriter().write(new Gson().toJson(jsonData));
		response.flushBuffer();
	}

	private boolean isValidPincode(String pincode) {
		return pincode != null && PINCODE.matcher(pincode).matches();
	}

	private boolean isValidManager(String managerId) throws Exception {
		return new UserDao().isValidManager(managerId);
	}

	private boolean isValidUserName(String userName) {
		return userName != null && USERNAME_PATTERN.matcher(userName).matches();
	}

	private boolean isValidEmail(String email) {
		return email != null && EMAIL_PATTERN.matcher(email).matches();
	}

	private boolean isValidMobileNumber(String mobileNumber) {
		return mobileNumber != null && MOBILE_PATTERN.matcher(mobileNumber).matches();
	}

//	private boolean hasAccess(String username, String password) throws ClassNotFoundException, SQLException {
//		user = new UserDao().validateUser(username, password);
//		return user == null ? false : true;
//	}

	@Override
	public void destroy() {
//		System.out.println("Interceptor Ending ...");
	}

}
