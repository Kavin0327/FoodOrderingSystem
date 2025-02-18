package com.zoho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zoho.bean.Address;
import com.zoho.util.DBUtil;

public class AddressDao {
	Connection conn = null;

	public int addAddress(Address address) throws ClassNotFoundException, SQLException {
		int addId = -1;
		conn = DBUtil.getConnection();
		String query = "INSERT INTO Address(address,pincode,landmark) VALUES(?,?,?);";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, address.getAddress());
		psmt.setInt(2, address.getPincode());
		psmt.setString(3, address.getLandmark());
		int rows = psmt.executeUpdate();
		if (rows == 0) {
			System.out.println("Problem While Inserting Address Details..");
			return addId;
		}
		query = "SELECT LAST_INSERT_ID();";
		psmt = conn.prepareStatement(query);
		ResultSet res = psmt.executeQuery();
		res.next();
		addId = res.getInt(1);
		return addId;
	}

	public Address getAddress(int addressId) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "SELECT * FROM Address WHERE addressId=?;";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setInt(1, addressId);
		ResultSet result = psmt.executeQuery();
		Address address = new Address();
		if (result.next()) {
			address.setAddressId(addressId);
			address.setAddress(result.getString(2));
			address.setPincode(result.getInt(3));
			address.setLandmark(result.getString(4));
			return address;
		}
		return null;
	}

	public int updateAddress(Address address) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "UPDATE INTO Address SET address=?,pincode=?,landmark=? WHERE addressId = ?";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, address.getAddress());
		psmt.setInt(2, address.getPincode());
		psmt.setString(3, address.getLandmark());
		psmt.setInt(4, address.getAddressId());
		int rows = psmt.executeUpdate();
		return rows;
	}
}
