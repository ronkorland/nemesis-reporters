package com.nemesis.testng.tests;

import org.testng.annotations.Test;

import com.nemesis.testng.dataproviders.CsvData;
import com.nemesis.testng.dataproviders.CsvFile;

public class CsvTest {

	@Test(dataProviderClass = com.nemesis.testng.dataproviders.CsvDataProvider.class, dataProvider = "csvDataProviderAnno")
	@CsvFile("CsvFile.csv")
	public void testProviderAnno(CsvData data) {
		System.out.println(data.get("file"));
	}

}
