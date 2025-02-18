package com.zoho.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("removed Session .");
			session.invalidate();
		}

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				System.out.println("Cookie deleted " + cookie.getName());
				cookie.setValue(null); // Overwrite value before deletion
				cookie.setMaxAge(0);
				cookie.setPath("/"); // Ensure same path as when set
				response.addCookie(cookie);
			}
		}

		return LOGIN;
	}

}
