package com.greenone;

import com.greenone.model.Purchase;
import com.greenone.model.User;
import com.greenone.model.UserProducts;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DBConnection {
	private void getDriverPrepare() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<User> dbRequestLastName(String str1) {
		List<User> userList = new ArrayList<>();
		getDriverPrepare();

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
		getDriverPrepare();

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
		getDriverPrepare();

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
		getDriverPrepare();

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

	public Object[] dbRequestDate(String startDate, String endDate) {
		Map<Integer, UserProducts> userProductsMap = new LinkedHashMap<>();
		Object[] arrayOfAll = new Object[4];
		int total_days = 0;
		int total_expenses = 0;
		float avg_expenses = 0;

		getDriverPrepare();

		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/shop_db", "postgres", "postgres");

			String queryNumOfDay = "SELECT COUNT(purchases.date) as num_of_day " +
					"FROM purchases " +
					"WHERE date_part('dow', purchases.date) in (1, 2, 3, 4, 5)" +
					"AND purchases.date BETWEEN ? AND ?";
			PreparedStatement stmtNumOfDay = conn.prepareStatement(queryNumOfDay);
			stmtNumOfDay.setDate(1, Date.valueOf(startDate));
			stmtNumOfDay.setDate(2, Date.valueOf(endDate));
			ResultSet rsNumOfDay = stmtNumOfDay.executeQuery();
			rsNumOfDay.next();
			total_days =  rsNumOfDay.getInt("num_of_day");

			String query = "SELECT COUNT(products.product_id) as num_of_product, " +
					"consumers.consumer_id, consumers.first_name, consumers.last_name, " +
					"products.product_name, SUM(products.price) as products_sum " +
					"FROM purchases " +
					"INNER JOIN products ON products.product_id = purchases.product_id " +
					"INNER JOIN consumers ON consumers.consumer_id = purchases.consumer_id " +
					"WHERE date_part('dow', purchases.date) in (1, 2, 3, 4, 5) " +
					"AND purchases.date BETWEEN ? AND ? " +
					"GROUP BY consumers.consumer_id, products.product_id " +
					"ORDER BY consumers.consumer_id, products_sum DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setDate(1, Date.valueOf(startDate));
			stmt.setDate(2, Date.valueOf(endDate));
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int key =  rs.getInt("consumer_id");

				if (userProductsMap.containsKey(key)) {
					Purchase purchase = new Purchase();
					purchase.setPurchase_name(rs.getString("product_name"));
					purchase.setProducts_sum(rs.getInt("products_sum"));

					UserProducts userProducts = userProductsMap.get(key);
					List<Purchase> purchases = userProducts.getPurchases();
					purchases.add(purchase);
					userProducts.setPurchases(purchases);

					userProducts.setTotal_expenses(userProducts.getTotal_expenses() +
							rs.getInt("products_sum"));

					userProductsMap.put(key, userProducts);
				} else {
					UserProducts userProducts = new UserProducts();
					userProducts.setUser_name(rs.getString("last_name") +
							" " + rs.getString("first_name"));
					userProducts.setTotal_expenses(rs.getInt("products_sum"));

					Purchase purchase = new Purchase();
					purchase.setPurchase_name(rs.getString("product_name"));
					purchase.setProducts_sum(rs.getInt("products_sum"));

					List<Purchase> purchases = new ArrayList<>();
					purchases.add(purchase);
					userProducts.setPurchases(purchases);

					userProductsMap.put(key, userProducts);
				}
				total_expenses += rs.getInt("products_sum");
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		avg_expenses = (float) total_expenses / userProductsMap.size();

		arrayOfAll[0] = total_days;
		arrayOfAll[1] = total_expenses;
		arrayOfAll[2] = avg_expenses;
		arrayOfAll[3] = userProductsMap;

		return arrayOfAll;
	}
}
