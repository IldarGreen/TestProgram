package com.greenone.model;

import java.util.List;
import java.util.Map;

public class Criteria {
	private List<String> lastName;
	private Map<String , Integer> productNameMinTimes;
	private Map<Integer, Integer> minExpensesMaxExpenses;
	public List<Integer> badCustomers;

	public Criteria(List<String> lastName, Map<String, Integer> productNameMinTimes,
					Map<Integer, Integer> minExpensesMaxExpenses, List<Integer> badCustomers) {
		this.lastName = lastName;
		this.productNameMinTimes = productNameMinTimes;
		this.minExpensesMaxExpenses = minExpensesMaxExpenses;
		this.badCustomers = badCustomers;
	}

	public List<String> getLastName() {
		return lastName;
	}

	public Map<String, Integer> getProductNameMinTimes() {
		return productNameMinTimes;
	}

	public Map<Integer, Integer> getMinExpensesMaxExpenses() {
		return minExpensesMaxExpenses;
	}

	public List<Integer> getBadCustomers() {
		return badCustomers;
	}

	@Override
	public String toString() {
		return "Criteria{" +
				"lastName=" + lastName +
				", productNameMinTimes=" + productNameMinTimes +
				", minExpensesMaxExpenses=" + minExpensesMaxExpenses +
				", badCustomers=" + badCustomers +
				'}';
	}
}
