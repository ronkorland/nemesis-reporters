package com.nemesis.reporter.type;

import org.joda.time.LocalDate;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormatter;

public class JodaDateDeserializer extends AbstractDateDeserializer {

	@Override
	protected ReadablePartial doParse(String text, DateTimeFormatter dft) {
		LocalDate ldt = dft.parseLocalDate(text);
		return ldt;
	}

	@Override
	protected String getPattern() {
		return JodaDateSerializer.FORMAT; // use same format for serialize and
											// deserialize
	}

}
