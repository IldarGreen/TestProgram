package com.greenone;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenone.model.Purchase;
import com.greenone.model.User;
import com.greenone.model.UserProducts;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonSimpleWriter {
	public static void writeJsonSimpleSearch(Map<JSONObject, List<User>> mapOfJsoAndUserList, String outputFileName) {
		ObjectMapper mapper = new ObjectMapper();

		try (JsonGenerator jGenerator = mapper.getFactory()
				.createGenerator(new File(outputFileName), JsonEncoding.UTF8)) {

			jGenerator.useDefaultPrettyPrinter();

			jGenerator.writeStartObject();
				jGenerator.writeStringField("type", "search");
				jGenerator.writeFieldName("results");
					jGenerator.writeStartArray();
						for (Map.Entry<JSONObject, List<User>> x : mapOfJsoAndUserList.entrySet()) {
							System.out.println(x.getKey() + "  " + x.getValue());
							jGenerator.writeStartObject();

							jGenerator.writeFieldName("criteria");
								jGenerator.writeStartObject();
										if (x.getKey().containsKey("lastName")) {
											jGenerator.writeStringField("lastName",
													(String) x.getKey().get("lastName"));
										}
										if (x.getKey().containsKey("productName")) {
											jGenerator.writeStringField("productName",
													(String) x.getKey().get("productName"));
											jGenerator.writeNumberField("minTimes",
													(Long) x.getKey().get("minTimes"));
										}
										if (x.getKey().containsKey("minExpenses")) {
											jGenerator.writeNumberField("minExpenses",
													(Long) x.getKey().get("minExpenses"));
											jGenerator.writeNumberField("maxExpenses",
													(Long) x.getKey().get("maxExpenses"));
										}
										if (x.getKey().containsKey("badCustomers")) {
											jGenerator.writeNumberField("badCustomers",
													(Long) x.getKey().get("badCustomers"));
										}
									jGenerator.writeFieldName("results");
									jGenerator.writeStartArray();
										for (User y: x.getValue()) {
											jGenerator.writeStartObject();
											jGenerator.writeStringField("lastName", y.getLast_name());
											jGenerator.writeStringField("firstName", y.getFirst_name());
											jGenerator.writeEndObject();
										}
									jGenerator.writeEndArray();

								jGenerator.writeEndObject();
							jGenerator.writeEndObject();
						}
					jGenerator.writeEndArray();
			jGenerator.writeEndObject();

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeJsonSimpleStat(Object[] arrayOfAll, String outputFileName) {
		int total_days = (int) arrayOfAll[0];
		int total_expenses = (int) arrayOfAll[1];
		float avg_expenses = (float) arrayOfAll[2];
		LinkedHashMap<Integer, UserProducts> userProductsMap = (LinkedHashMap<Integer, UserProducts>) arrayOfAll[3];

		// sort userProductsMap by Total_expenses -----------------------
		Set<Map.Entry<Integer, UserProducts>> upmSet = userProductsMap.entrySet();
		List<Map.Entry<Integer, UserProducts>> upmList = new ArrayList<>(upmSet);
		Collections.sort(upmList, new Comparator<Map.Entry<Integer, UserProducts>>() {
			@Override
			public int compare(Map.Entry<Integer, UserProducts> o1, Map.Entry<Integer, UserProducts> o2) {
				return o2.getValue().getTotal_expenses() - o1.getValue().getTotal_expenses();
			}
		});
		userProductsMap.clear();
		for(Map.Entry<Integer, UserProducts> map : upmList){
			userProductsMap.put(map.getKey(), map.getValue());
		} //-------------------------------------------------------------

		ObjectMapper mapper = new ObjectMapper();

		try (JsonGenerator jGenerator = mapper.getFactory()
				.createGenerator(new File(outputFileName), JsonEncoding.UTF8)) {

			jGenerator.useDefaultPrettyPrinter();

			jGenerator.writeStartObject();
				jGenerator.writeStringField("type", "stat");
				jGenerator.writeNumberField("totalDays", total_days);
				jGenerator.writeFieldName("customers");
					jGenerator.writeStartArray();
						for (Map.Entry<Integer, UserProducts> x: userProductsMap.entrySet()) {
							jGenerator.writeStartObject();
								jGenerator.writeStringField("name", x.getValue().getUser_name());
								jGenerator.writeFieldName("purchases");
								jGenerator.writeStartArray();
									for (Purchase y: x.getValue().getPurchases()) {
										jGenerator.writeStartObject();
											jGenerator.writeStringField("name", y.getPurchase_name());
											jGenerator.writeNumberField("expenses", y.getProducts_sum());
										jGenerator.writeEndObject();
									}
								jGenerator.writeEndArray();
								jGenerator.writeNumberField("totalDays", x.getValue().getTotal_expenses());
							jGenerator.writeEndObject();
						}
					jGenerator.writeEndArray();
					jGenerator.writeNumberField("totalExpenses", total_expenses);
					jGenerator.writeNumberField("avgExpenses", avg_expenses);
			jGenerator.writeEndObject();

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}