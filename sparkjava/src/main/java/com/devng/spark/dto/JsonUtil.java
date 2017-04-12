package com.devng.spark.dto;

import java.time.LocalDate;

import spark.ResponseTransformer;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.owlike.genson.JsonBindingException;
import com.owlike.genson.stream.JsonStreamException;

public class JsonUtil {

	private static final Genson genson = new GensonBuilder()
			.useIndentation(true)
			.withConverter(new LocalDateConverter(), LocalDate.class)
			.create();

	public static String toJson(Object object) {
		return genson.serialize(object);
	}

	public static <T> T fromJson(String jsonString, Class<T> type) {
		try {
			T e = genson.deserialize(jsonString, type);
			return e;
		} catch (JsonStreamException | JsonBindingException ex) {
			throw new IllegalArgumentException("Could not parse JSON: " + jsonString + ". Reason: " + ex.getMessage());
		}
	}

	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}

}
