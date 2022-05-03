package com.greenone;

import com.greenone.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;

public class JsonSimpleParser {
	public void parseType(String typeOfRequest, String inputFileName, String outputFileName) {
		Map<JSONObject, List<User>> mapOfJsoAndUserList = new LinkedHashMap<>();
		Object[] arrayOfAll = new Object[4];
		JsonSimpleWriter jsw = new JsonSimpleWriter();

		if (typeOfRequest == "search") {
			mapOfJsoAndUserList = parseForSearch(inputFileName);
			jsw.writeJsonSimpleSearch(mapOfJsoAndUserList, outputFileName);
		}
		if (typeOfRequest == "stat") {
			arrayOfAll = parseForStat(inputFileName);
			jsw.writeJsonSimpleStat(arrayOfAll, outputFileName);
		}
	}

	public Map<JSONObject, List<User>> parseForSearch(String inputFileName) {
		JSONParser parser = new JSONParser();

		List<User> userList = new ArrayList<>();
		Map<JSONObject, List<User>> mapOfJsoAndUserList = new LinkedHashMap<>();

		try(FileReader reader = new FileReader(inputFileName)) {

			JSONObject rootJsonObject = (JSONObject) parser.parse(reader);
			JSONArray criteriasJsonArray = (JSONArray) rootJsonObject.get("criterias");
			JSONObject criteriasJsonObject = null;

			for(Object it:  criteriasJsonArray) {
				criteriasJsonObject = (JSONObject) it;

				if (criteriasJsonObject.get("lastName") != null) {
					DBConnection dbconnection = new DBConnection();
					userList  = dbconnection.dbRequestLastName((String) criteriasJsonObject.get("lastName"));
					mapOfJsoAndUserList.put(criteriasJsonObject, userList);
				}
				if (criteriasJsonObject.get("productName") != null) {
					DBConnection dbconnection = new DBConnection();
					userList = dbconnection.dbRequestProductName((String) criteriasJsonObject.get("productName"),
							Math.toIntExact((Long) criteriasJsonObject.get("minTimes")));
					mapOfJsoAndUserList.put(criteriasJsonObject, userList);
				}
				if (criteriasJsonObject.get("minExpenses") != null) {//
					DBConnection dbconnection = new DBConnection();
					userList = dbconnection.dbRequestMinMaxExpenses(
							Math.toIntExact((Long) criteriasJsonObject.get("minExpenses")),
							Math.toIntExact((Long) criteriasJsonObject.get("maxExpenses")));
					mapOfJsoAndUserList.put(criteriasJsonObject, userList);
				}
				if (criteriasJsonObject.get("badCustomers") != null) {
					DBConnection dbconnection = new DBConnection();
					userList = dbconnection.dbRequestBadCustomers(
							Math.toIntExact((Long) criteriasJsonObject.get("badCustomers")));
					mapOfJsoAndUserList.put(criteriasJsonObject, userList);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapOfJsoAndUserList;
	}

	public Object[] parseForStat(String inputFileName) {
		JSONParser parser = new JSONParser();

		Object[] arrayOfAll = new Object[4];

		try(FileReader reader = new FileReader(inputFileName)) {
			JSONObject rootJsonObject = (JSONObject) parser.parse(reader);

			DBConnection dbconnection = new DBConnection();
			arrayOfAll = dbconnection.dbRequestDate((String) rootJsonObject.get("startDate"),
					(String) rootJsonObject.get("endDate"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayOfAll;
	}
}
