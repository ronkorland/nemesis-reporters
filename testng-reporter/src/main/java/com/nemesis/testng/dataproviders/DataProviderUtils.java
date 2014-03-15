package com.nemesis.testng.dataproviders;

import java.lang.reflect.Method;

public class DataProviderUtils {

	protected static String resolveDataProviderCsvFile(Method testMethod)
			throws Exception {
		if (testMethod == null)
			throw new IllegalArgumentException(
					"Test Method context cannot be null.");

		CsvFile file = testMethod.getAnnotation(CsvFile.class);
		if (file == null)
			throw new IllegalArgumentException(
					"Test Method context has no CsvFile annotation.");
		if (file.value() == null)
			throw new IllegalArgumentException(
					"Test Method context has a malformed CsvFile annotation.");
		return file.value();
	}
}
