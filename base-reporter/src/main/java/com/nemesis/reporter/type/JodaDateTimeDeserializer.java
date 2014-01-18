package com.nemesis.reporter.type;

import org.joda.time.LocalDateTime;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormatter;

public class JodaDateTimeDeserializer extends AbstractDateDeserializer {

	public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Override
	protected ReadablePartial doParse(String text, DateTimeFormatter dft) {
		LocalDateTime ldt = dft.parseLocalDateTime(text);
		return ldt;
	}

	protected String getPattern() {
		return FORMAT;
	}
}
