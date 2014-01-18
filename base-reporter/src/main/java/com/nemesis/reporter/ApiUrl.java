package com.nemesis.reporter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum ApiUrl {

	BASE_URL("base.url"), SUITE_RESOURCE("suite.resource"), TEST_RESOURCE(
			"test.resource"), TEST_ATTACHMENT_RESOURCE(
			"test.attachment.resource");

	private String propName;

	private ApiUrl(String propName) {
		this.propName = propName;
	}

	public String getPropValue() {
		try {
			Properties prop = new Properties();
			InputStream input = null;
			String filename = "url.properties";
			input = ApiUrl.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return null;
			}

			prop.load(input);
			return prop.getProperty(propName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}