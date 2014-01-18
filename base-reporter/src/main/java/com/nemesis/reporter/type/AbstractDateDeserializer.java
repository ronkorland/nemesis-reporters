package com.nemesis.reporter.type;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public abstract class AbstractDateDeserializer extends
		JsonDeserializer<ReadablePartial> {

	@Override
	public ReadablePartial deserialize(JsonParser jsonParser,
			DeserializationContext arg1) throws IOException,
			JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);
		String text = node.getTextValue();

		DateTimeFormatter dft = DateTimeFormat.forPattern(getPattern());

		ReadablePartial rp = doParse(text, dft);

		return rp;
	}

	protected abstract ReadablePartial doParse(String text,
			DateTimeFormatter dft);

	protected abstract String getPattern();

}
