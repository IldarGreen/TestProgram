package com.greenone;

public class Main {
	public static void main(String[] args) {
//		System.out.println("Argument count: " + args.length);
//		for (int i = 0; i < args.length; i++) {
//			System.out.println("Argument " + i + ": " + args[i]);
//		}


		String typeOfRequest = "search";
		String inputFileName = "input.json";

//		String typeOfRequest = "stat";
//		String inputFileName = "inputDate.json";

		String outputFileName = "output.json";

		JsonSimpleParser parser = new JsonSimpleParser();
		parser.parseType(typeOfRequest, inputFileName, outputFileName);
	}
}
