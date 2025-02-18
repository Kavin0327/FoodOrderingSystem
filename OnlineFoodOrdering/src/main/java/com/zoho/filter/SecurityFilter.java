package com.zoho.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
	private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,15}$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
	private static final Pattern MOBILE_PATTERN = Pattern.compile("^\\d{10}$");

	Set<String> validUrls = new HashSet<>();

	public void destroy() {
		validUrls.clear();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		Set<String> staticResources = new HashSet<>();
//		Collections.addAll(staticResources, "/css/login.css", "/js/login.js", "/js/jquery-3.7.1.min.js");
		System.out.println("Security Filter:----\n\n"+validUrls);
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		Boolean isAuthenticated = (Boolean) req.getAttribute("isAuthenticated");
		Boolean isAdmin = (Boolean) req.getAttribute("isAdmin");
		Boolean isManager = (Boolean) req.getAttribute("isManager");

		System.out.println("isAuthentication : " + isAuthenticated);
		System.out.println("isAdmin : " + isAdmin);
		System.out.println("isManager : " + isManager+"\n\n");
		
		Cookie[] cookies = req.getCookies();
		String requestURLString = req.getRequestURI();
		requestURLString = requestURLString.substring(req.getContextPath().length());
		System.out.println("Url : " + requestURLString);
		boolean flag = true;
		
		if(requestURLString.equals("/login.html") && isAuthenticated != null && isAuthenticated) {
			res.sendRedirect("home.html");
		}
		
		if (requestURLString.equals("/") || requestURLString.equals("/login")
				|| requestURLString.equals("/signup") || requestURLString.startsWith("/css/")
				|| requestURLString.startsWith("/js/")) {
			flag = true;
		} 

		if (!validUrls.contains(requestURLString)) {
			System.out.println(requestURLString + " = This Url not found");
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		if((requestURLString.equals("/addRestuarant.html") || requestURLString.equals("/addManager.html")) && !isAdmin) {
			System.out.println("UnAuthoried Access detected");
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		if((requestURLString.equals("/addFoodItem.html") || requestURLString.equals("/editFoodItem.html")) && (!isManager)) {
			System.out.println("UnAuthoried Access detected");
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		

		if (flag) {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("Url_List.txt");

		if (inputStream == null) {
			throw new ServletException("Valid URLs file not found!");
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null) {
				validUrls.add(line.trim());
			}
			reader.close();
		} catch (Exception e) {
			System.err.println(e);
		}

	}

}
