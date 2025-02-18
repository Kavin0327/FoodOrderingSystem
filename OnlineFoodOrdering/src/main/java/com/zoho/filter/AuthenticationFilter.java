package com.zoho.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zoho.bean.User;
import com.zoho.dao.RestuarantDao;
import com.zoho.dao.UserDao;
import com.zoho.util.Crypto;

public class AuthenticationFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
	private String message = "";
	private String username = "";
	private String password = "";
	private String role = "";

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		Cookie[] cookies = req.getCookies();
		boolean flag = true;
		String requestURLString = req.getRequestURI();
		requestURLString = requestURLString.substring(req.getContextPath().length());
		System.out.println("Authentication Filter:--- \n\nAuth URL : " + requestURLString);
		UserDao userDao = new UserDao();

		if (session == null && (requestURLString.equals("/") || requestURLString.equals("/login.html")
				|| requestURLString.equals("/user/loginAction") || requestURLString.equals("/user/RegisterAction")
				|| requestURLString.equals("/signup.html") || requestURLString.startsWith("/css/")
				|| requestURLString.startsWith("/js/"))) {
			System.out.println("new Login");
			chain.doFilter(request, response);
		} else {
			if (cookies != null && session != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("username")) {
						username = Crypto.decryption(cookie.getValue());
					}
					if (cookie.getName().equals("password")) {
						password = Crypto.decryption(cookie.getValue());
					}
					if (cookie.getName().equals("role")) {
						role = Crypto.decryption(cookie.getValue());
					}
				}
			} else {
				res.sendRedirect(req.getContextPath() + "/login.html");
				return;
			}

			User sessionuser = (User) session.getAttribute("curruser");
			User user = null;
			try {
				user = new UserDao().validateUser(username, password);
			} catch (Exception e) {
				System.out.println(e);
				res.sendError(HttpServletResponse.SC_NOT_FOUND, "not found");
				return;
			}
			Boolean Authenticated = user == null ? false : true;
			Boolean Admin = false;
			Boolean Manager = false;
			if (role.equals("admin")) {
				Admin = true;
			}

			if (role.equals("manager")) {
				Manager = true;
			}

			if (requestURLString.equals("/foodList.html")) {
				try {
					if (!isValidManagerForRestaurant(req)) {
						res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
						return;
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			req.setAttribute("isAuthenticated", Authenticated);
			req.setAttribute("isAdmin", Admin);
			req.setAttribute("isManager", Manager);

			chain.doFilter(request, response);
		}
	}

	private boolean isValidManagerForRestaurant(HttpServletRequest request)
			throws ClassNotFoundException, SQLException {
		RestuarantDao restuarantdao = new RestuarantDao();
		int rId = Integer.parseInt(request.getParameter("restaurantId"));
		System.out.println("RestuarantId: " + rId + "\n User Id :" + username);
		return restuarantdao.isValidManager(rId, username);
	}

}
