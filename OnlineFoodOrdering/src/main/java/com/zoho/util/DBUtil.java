package com.zoho.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static String URL = "jdbc:mysql://localhost:3306/foodOrderingSystem";
	private static String USER = "root";
	private static String PASSWORD = "Karpagam123";
	private static Connection conn = null;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conn != null) {
			return conn;
		}
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		return conn;
	}
}
