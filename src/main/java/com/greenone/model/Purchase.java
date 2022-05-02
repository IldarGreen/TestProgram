package com.greenone.model;

public class Purchase {
	private String purchase_name;
	private Integer products_sum;

	public String getPurchase_name() {
		return purchase_name;
	}

	public void setPurchase_name(String purchase_name) {
		this.purchase_name = purchase_name;
	}

	public Integer getProducts_sum() {
		return products_sum;
	}

	public void setProducts_sum(Integer products_sum) {
		this.products_sum = products_sum;
	}

	@Override
	public String toString() {
		return "Purchase{" +
				"purchase_name='" + purchase_name + '\'' +
				", products_sum=" + products_sum +
				'}';
	}
}
