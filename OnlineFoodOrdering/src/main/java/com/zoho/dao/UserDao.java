package com.zoho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zoho.bean.User;
import com.zoho.util.DBUtil;

public class UserDao {
	Connection conn;

	public User validateUser(String username, String password) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "SELECT * FROM User WHERE userId=? AND password=?;";
//		Statement smt = conn.createStatement();
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, username);
		psmt.setString(2, password);
		ResultSet result = psmt.executeQuery();
		User user = new User();
		if (result.next()) {
			user.setUserId(result.getString(1));
			user.setName(result.getString(2));
			user.setPassword(result.getString(3));
			user.setAddressId(result.getInt(4));
			user.setEmail(result.getString(5));
			user.setPhoneNumber(result.getString(6));
			user.setRole(result.getString(7));
			return user;
		}
		return null;
	}

	public User getUser(String username) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "SELECT * FROM User WHERE userId=?;";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, username);
		ResultSet result = psmt.executeQuery();
		User user = new User();
		if (result.next()) {
			user.setUserId(result.getString(1));
			user.setName(result.getString(2));
			user.setPassword(result.getString(3));
			user.setAddressId(result.getInt(4));
			user.setEmail(result.getString(5));
			user.setPhoneNumber(result.getString(6));
			user.setRole(result.getString(7));
			return user;
		}
		return null;
	}

	public boolean addUser(User user) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "INSERT INTO User VALUES(?,?,?,?,?,?,?);";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, user.getUserId());
		psmt.setString(2, user.getName());
		psmt.setString(3, user.getPassword());
		psmt.setInt(4, user.getAddressId());
		psmt.setString(5, user.getEmail());
		psmt.setString(6, user.getPhoneNumber());
		psmt.setString(7, user.getRole());
		int rows = psmt.executeUpdate();
		if (rows == 0) {
			System.out.println("Problem while inserting User Details");
			return false;
		}
		return true;
	}

	public int updateUser(User user) throws SQLException, ClassNotFoundException {
		conn = DBUtil.getConnection();
		String query = "UPDATE INTO User SET name=?,password=?,phoneNumber=?,email=? WHERE ";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, user.getName());
		psmt.setString(2, user.getPassword());
		psmt.setString(3, user.getPhoneNumber());
		psmt.setString(4, user.getEmail());
		psmt.setString(5, user.getUserId());
		int rows = psmt.executeUpdate();
		return rows;
	}

	public boolean isValidManager(String mID) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "SELECT * FROM User Where userId=? AND role=?;";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, mID);
		psmt.setString(2, "manager");
		ResultSet resultSet = psmt.executeQuery();
		if (resultSet.next()) {
			return false;
		}
		return true;
	}
}
