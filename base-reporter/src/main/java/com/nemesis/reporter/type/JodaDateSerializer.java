package com.nemesis.reporter.type;

public class JodaDateSerializer extends AbstractDateSerializer {

	public static final String FORMAT = "yyyy-MM-dd";

	@Override
	protected String getPattern() {
		return FORMAT;
	}

}
