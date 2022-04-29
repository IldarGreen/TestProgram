package com.greenone;

import com.greenone.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonSimpleParser {

	public void parse(String fileName) {
		JSONParser parser = new JSONParser();

		List<User> userList = new ArrayList<>();
		Map<JSONObject, List<User>> mapOfJsoAndUserList = new LinkedHashMap<>();

		try(FileReader reader = new FileReader(fileName)) {

			JSONObject rootJsonObject = (JSONObject) parser.parse(reader);
			JSONArray criteriasJsonArray = (JSONArray) rootJsonObject.get("criterias");
			JSONObject criteriasJsonObject = null;

			for(Object it:  criteriasJsonArray) {
				criteriasJsonObject = (JSONObject) it;

				if (criteriasJsonObject.get("lastName") != null) {
					DBConnection dbconnection = new DBConnection();
					userList  = dbconnection.dbRequestlastName((String) criteriasJsonObject.get("lastName"));
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

			JsonSimpleWriter jsw = new JsonSimpleWriter();
			jsw.writeJsonSimpleDemo(mapOfJsoAndUserList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
