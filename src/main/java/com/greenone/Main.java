package com.greenone;

import com.greenone.model.Root;

public class Main {
	public static void main(String[] args) {

		String fileName = "test.json";

		JsonSimpleParser parser = new JsonSimpleParser();
		Root root = parser.parse(fileName);

		System.out.println("Root " + root.toString());
	}
}
