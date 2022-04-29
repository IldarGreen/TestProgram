package com.greenone;

import com.greenone.model.User;

import java.sql.*;
import java.util.*;

public class DBConnection {
	public List<User> dbRequestLastName(String str1) {
		List<User> userList = new ArrayList<>();

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/shop_db",
					"postgres", "postgres");

			String query = "SELECT * FROM consumers WHERE last_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, str1);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setConsumer_id(rs.getInt("consumer_id"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				userList.add(user);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public List<User> dbRequestProductName(String productName, Integer minTimes) {
		List<User> userList = new ArrayList<>();

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/shop_db", "postgres", "postgres");

			String query = "SELECT COUNT(*), consumers.consumer_id, consumers.first_name, consumers.last_name " +
					"FROM purchases " +
					"INNER JOIN products ON products.product_id = purchases.product_id " +
					"INNER JOIN consumers ON consumers.consumer_id = purchases.consumer_id " +
					"WHERE products.product_name = ?" +
					"GROUP BY consumers.consumer_id having COUNT(*) >= ? " +
					"ORDER BY consumers.consumer_id";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, productName);
			stmt.setInt(2, minTimes);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setConsumer_id(rs.getInt("consumer_id"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				userList.add(user);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public List<User> dbRequestMinMaxExpenses(Integer minExpenses, Integer maxExpenses) {
		List<User> userList = new ArrayList<>();

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/shop_db", "postgres", "postgres");

			String query = "SELECT SUM(products.price), " +
					"consumers.consumer_id, consumers.first_name, consumers.last_name " +
					"FROM purchases " +
					"INNER JOIN products ON products.product_id = purchases.product_id " +
					"INNER JOIN consumers ON purchases.consumer_id = consumers.consumer_id " +
					"GROUP BY consumers.consumer_id HAVING SUM(products.price) BETWEEN ? and ?" +
					"ORDER BY consumers.consumer_id";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, minExpenses);
			stmt.setInt(2, maxExpenses);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setConsumer_id(rs.getInt("consumer_id"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				userList.add(user);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public List<User> dbRequestBadCustomers(Integer badCustomers) {
		List<User> userList = new ArrayList<>();

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/shop_db", "postgres", "postgres");

			String query = "SELECT COUNT(*) as num_of_purchases, " +
					"consumers.consumer_id, consumers.first_name, consumers.last_name " +
					"FROM purchases " +
					"INNER JOIN products ON products.product_id = purchases.product_id " +
					"INNER JOIN consumers ON consumers.consumer_id = purchases.consumer_id " +
					"GROUP BY consumers.consumer_id " +
					"ORDER BY num_of_purchases LIMIT ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, badCustomers);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setConsumer_id(rs.getInt("consumer_id"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				userList.add(user);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
}
