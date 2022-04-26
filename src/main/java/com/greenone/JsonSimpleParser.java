package com.greenone;

import com.greenone.model.Criteria;
import com.greenone.model.Root;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonSimpleParser {

	public Root parse(String fileName) {
		Root root = new Root();
		JSONParser parser = new JSONParser();

		try(FileReader reader = new FileReader(fileName)) {

			JSONObject rootJsonObject = (JSONObject) parser.parse(reader);
			JSONArray crecriteriasJsonArray = (JSONArray) rootJsonObject.get("criterias");

			List<String> lastName = new ArrayList<>();
			List<Integer> badCustomers = new ArrayList<>();
			Map<String , Integer> productNameMinTimes = new HashMap();
			Map<Integer , Integer> minExpensesMaxExpenses = new HashMap();

			for(Object it:  crecriteriasJsonArray) {
				JSONObject crecriteriasJsonObject = (JSONObject) it;

				if (crecriteriasJsonObject.get("lastName") != null) {
					lastName.add((String) crecriteriasJsonObject.get("lastName"));
				}
				if (crecriteriasJsonObject.get("badCustomers") != null) {
					badCustomers.add(Math.toIntExact((Long) crecriteriasJsonObject.get("badCustomers")));
				}
				if (crecriteriasJsonObject.get("productName") != null) {
					productNameMinTimes.put((String) crecriteriasJsonObject.get("productName"),
							Math.toIntExact((Long) crecriteriasJsonObject.get("minTimes")));
				}
				if (crecriteriasJsonObject.get("minExpenses") != null) {
					minExpensesMaxExpenses.put(Math.toIntExact((Long) crecriteriasJsonObject.get("minExpenses")),
							Math.toIntExact((Long) crecriteriasJsonObject.get("maxExpenses")));
				}

				Criteria criteria = new Criteria(lastName, productNameMinTimes, minExpensesMaxExpenses, badCustomers);

				root.setCriteria(criteria);

			}

			return root;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
