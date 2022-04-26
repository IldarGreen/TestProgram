package com.greenone.model;

public class Root {

	private Criteria criteria;

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public String toString() {
		return "Root{" +
				"criteria=" + criteria +
				'}';
	}
}

