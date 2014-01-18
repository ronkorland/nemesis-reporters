package com.nemesis.reporter.type;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;

public abstract class AbstractDateSerializer extends
		JsonSerializer<ReadablePartial> {

	@Override
	public void serialize(ReadablePartial date, JsonGenerator gen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		String formattedDate = DateTimeFormat.forPattern(getPattern()).print(
				date);

		gen.writeString(formattedDate);
	}

	protected abstract String getPattern();
}
