package com.zoho.bean;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zoho.dao.AddressDao;
import com.zoho.dao.UserDao;

@WebServlet("/UpdateProfile")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = new User();
		Address address = new Address();
		user.setName(request.getParameter("fullname"));
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		user.setPhoneNumber(request.getParameter("phoneNumber"));
		user.setUserId(request.getParameter("username"));
		address.setAddressId(Integer.parseInt(request.getParameter("addressId")));
		address.setAddress(request.getParameter("address"));
		address.setLandmark(request.getParameter("landmark"));
		address.setPincode(Integer.parseInt(request.getParameter("pincode")));
		AddressDao addressdao = new AddressDao();
		UserDao userdao = new UserDao();
		int row = 0;
		response.setContentType("text/html");
		try {
			row = addressdao.updateAddress(address);
			row += userdao.updateUser(user);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(row > 1) {
			response.getWriter().write("success");
		}else {
			response.getWriter().write("failure");
		}

	}

}
