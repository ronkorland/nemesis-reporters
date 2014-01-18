package com.nemesis.reporter;

import java.io.File;

import com.nemesis.reporter.data.SuiteData;
import com.nemesis.reporter.data.TestData;

public interface MongoDbRestClient {

	public SuiteData createSuite(SuiteData suite);

	public SuiteData updateSuite(SuiteData suite);

	public TestData createTest(TestData test);

	public TestData updateTest(TestData test);

	public String uploadTestAttach(String testId, File file);
}
