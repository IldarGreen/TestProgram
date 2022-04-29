package com.greenone.model;

public class User {
	private Integer consumer_id;
	private String first_name;
	private String last_name;

	public Integer getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(Integer consumer_id) {
		this.consumer_id = consumer_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	@Override
	public String toString() {
		return "User{" +
				"consumer_id=" + consumer_id +
				", first_name='" + first_name + '\'' +
				", last_name='" + last_name + '\'' +
				'}';
	}
}
