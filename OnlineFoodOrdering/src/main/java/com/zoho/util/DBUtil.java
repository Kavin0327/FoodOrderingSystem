package com.zoho.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static String URL = "jdbc:mysql://localhost:3306/YOUR DATABASE NAME";
	private static String USER = "USER_NAME";
	private static String PASSWORD = "YOUR_PASSWORD";
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
