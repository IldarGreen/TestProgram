package com.greenone;

public class Main {

	public static void main(String[] args) {
		String typeOfRequest =  args[0];
		String inputFileName =  args[1];
		String outputFileName =  args[2];

		JsonSimpleParser parser = new JsonSimpleParser();
		parser.parseType(typeOfRequest, inputFileName, outputFileName);
	}
}
