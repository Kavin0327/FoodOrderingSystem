package com.zoho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zoho.bean.Restuarant;
import com.zoho.util.DBUtil;

public class RestuarantDao {
	Connection conn = null;

	public int addRestuarant(Restuarant restuarant) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "INSERT INTO Restuarant(restuarantName,description,addressId,rating,ownerName,managerId) VALUES(?,?,?,?,?,?);";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, restuarant.getRestuarantName());
		psmt.setString(2, restuarant.getDescription());
		psmt.setInt(3, restuarant.getAddressId());
		psmt.setDouble(4, restuarant.getRating());
		psmt.setString(5, restuarant.getOwnerName());
		psmt.setString(6, restuarant.getManagerId());
		int rows = psmt.executeUpdate();
		if (rows == 0) {
			return -1;
		}
		return 1;
	}

	public Restuarant getRestuarant(int rId) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		Restuarant restuarant = new Restuarant();
		String query = "SELECT * FROM Restuarant WHERE restuarantId=?;";
		PreparedStatement psmt = conn.prepareStatement(query);
		ResultSet result = psmt.executeQuery();
		if (result.next()) {
			restuarant.setRestuarantId(rId);
			restuarant.setRestuarantName(result.getString(2));
			restuarant.setDescription(result.getString(3));
			restuarant.setAddressId(result.getInt(4));
			restuarant.setRating(result.getDouble(5));
			restuarant.setOwnerName(result.getString(6));
			restuarant.setManagerId(result.getString(7));
			restuarant.setAvailable(result.getString(8));
			return restuarant;
		}
		return null;
	}

	public List<Restuarant> getAllRestuarant() throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "SELECT r.restuarantId, r.restuarantName, r.description, r.rating,a.addressId, a.address ,a.pincode,a.landmark FROM "
				+ "Restuarant r JOIN Address a ON a.addressId = r.addressId WHERE available='yes';";

		PreparedStatement psmt = conn.prepareStatement(query);
		ResultSet result = psmt.executeQuery();
		List<Restuarant> list = new ArrayList<>();
		while (result.next()) {
			Restuarant restuarant = new Restuarant();
			restuarant.setRestuarantId(result.getInt(1));
			restuarant.setRestuarantName(result.getString(2));
			restuarant.setDescription(result.getString(3));
			restuarant.setRating(result.getDouble(4));
			restuarant.setAddressId(result.getInt(5));
			restuarant.setAddress(result.getString(6));
			restuarant.setPincode(result.getInt(7));
			restuarant.setLandmark(result.getString(8));
			list.sort((x, y) -> Double.compare(y.getRating(), x.getRating()));
			list.add(restuarant);
		}

		return list;
	}

	public boolean removeRestuarant(int rId) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "UPDATE Restuarant SET available = 'no' WHERE restuarantId=?;";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setInt(1, rId);
		int rows = psmt.executeUpdate();
		if (rows == 0) {
			return false;
		}
		return true;
	}

	public List<Restuarant> getAllAssignedRestuarant(String userId) throws SQLException, ClassNotFoundException {
		conn = DBUtil.getConnection();
		String query = "SELECT r.restuarantId, r.restuarantName, r.description, r.rating,a.addressId, a.address ,a.pincode,a.landmark FROM "
				+ "Restuarant r JOIN Address a ON a.addressId = r.addressId WHERE available='yes' AND r.managerId = ?;";

		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setString(1, userId);
		ResultSet result = psmt.executeQuery();
		List<Restuarant> list = new ArrayList<>();
		while (result.next()) {
			Restuarant restuarant = new Restuarant();
			restuarant.setRestuarantId(result.getInt(1));
			restuarant.setRestuarantName(result.getString(2));
			restuarant.setDescription(result.getString(3));
			restuarant.setRating(result.getDouble(4));
			restuarant.setAddressId(result.getInt(5));
			restuarant.setAddress(result.getString(6));
			restuarant.setPincode(result.getInt(7));
			restuarant.setLandmark(result.getString(8));
			list.add(restuarant);
		}
		return list;
	}

	public boolean isValidManager(int rId, String mId) throws ClassNotFoundException, SQLException {
		conn = DBUtil.getConnection();
		String query = "SELECT * FROM Restuarant WHERE restuarantId = ? AND managerId = ?";
		PreparedStatement psmt = conn.prepareStatement(query);
		psmt.setInt(1, rId);
		psmt.setString(2, mId);
		ResultSet result = psmt.executeQuery();
		if (result.next()) {
			return true;
		}
		return false;
	}
}
