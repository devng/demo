package com.devng.spark.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

public class LocalDateConverter implements Converter<LocalDate> {

	@Override
	public void serialize(LocalDate localDate, ObjectWriter objectWriter, Context context) throws Exception {
		if (localDate == null) {
			return;
		}
		objectWriter.writeString(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
	}

	@Override
	public LocalDate deserialize(ObjectReader objectReader, Context context) throws Exception {

		final String dateString = objectReader.valueAsString();
		if (dateString == null || dateString.isEmpty()) {
			return null;
		}
		try {
			return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (DateTimeParseException ex) {
			throw new IllegalArgumentException(ex);
		}

	}
}
