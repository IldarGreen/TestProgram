package com.greenone.model;

import java.util.List;

public class UserProducts {
	private String user_name;
	private List<Purchase> purchases;
	private Integer total_expenses;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public Integer getTotal_expenses() {
		return total_expenses;
	}

	public void setTotal_expenses(Integer total_expenses) {
		this.total_expenses = total_expenses;
	}

	@Override
	public String toString() {
		return "UserProducts{" +
				"user_neme='" + user_name + '\'' +
				", purchases=" + purchases +
				", total_expenses=" + total_expenses +
				'}';
	}
}
