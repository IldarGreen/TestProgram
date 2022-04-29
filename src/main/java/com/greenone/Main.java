package com.greenone;

public class Main {
	public static void main(String[] args) {

		String fileName = "test.json";

		JsonSimpleParser parser = new JsonSimpleParser();
		parser.parse(fileName);
	}
}
