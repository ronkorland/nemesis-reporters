package com.nemesis.testng.dataproviders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import au.com.bytecode.opencsv.CSVReader;

public class CsvDataProvider {

	@DataProvider(name = "csvDataProviderAnno")
	public static Iterator<Object[]> getCsvDataProviderAnno(Method method,
			ITestContext testContext) {
		String fileName = null;
		try {
			fileName = DataProviderUtils.resolveDataProviderCsvFile(method);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getCsvIterator(fileName);
	}

	@DataProvider(name = "csvDataProviderXml")
	public static Iterator<Object[]> getCsvDataProviderXml(Method method,
			ITestContext testContext) {
		String fileName = null;
		try {
			fileName = testContext.getCurrentXmlTest().getParameter("csvFile");
			if (fileName == null) {
				throw new IllegalArgumentException(
						"The csv file name not defined in suite xml (parameter = 'csvFile')");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getCsvIterator(fileName);
	}

	public static Iterator<Object[]> getCsvIterator(String fileName) {
		List<Object[]> list = new ArrayList<Object[]>();
		CSVReader reader = null;
		try {
			URL url = Thread.currentThread().getContextClassLoader()
					.getResource(fileName);
			if (url == null) {
				Assert.fail("Failed to load csv file");
				return null;
			}
			FileReader fileReader = new FileReader(url.getFile());
			reader = new CSVReader(fileReader);

			List<String[]> readAll = reader.readAll();
			for (int i = 1; i < readAll.size(); i++) {
				TreeMap<String, String> map = new TreeMap<String, String>(
						String.CASE_INSENSITIVE_ORDER);
				String[] line = readAll.get(i);
				for (int j = 0; j < line.length; j++) {
					map.put(readAll.get(0)[j], line[j]);
				}
				CsvData data = new CsvData(map);
				list.add(new Object[] { data });
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			Assert.fail("Failed to load csv file", e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list.iterator();
	}
}
