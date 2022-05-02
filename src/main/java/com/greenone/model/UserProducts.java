package com.greenone.model;

import java.util.List;

public class UserProducts {
	private Integer consumer_id;
	private String user_neme;
	private List<Purchase> purchases;
	private Integer total_expenses;

	public Integer getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(Integer consumer_id) {
		this.consumer_id = consumer_id;
	}

	public String getUser_neme() {
		return user_neme;
	}

	public void setUser_neme(String user_neme) {
		this.user_neme = user_neme;
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
		return "UserProductList{" +
				"consumer_id=" + consumer_id +
				", user_neme='" + user_neme + '\'' +
				", purchases=" + purchases +
				", total_expenses=" + total_expenses +
				'}';
	}
}
