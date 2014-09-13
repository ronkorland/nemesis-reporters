package com.nemesis.reporter;

import java.io.File;

import com.nemesis.reporter.data.SuiteData;
import com.nemesis.reporter.data.TestData;

public interface MongoDbRestClient {

	public SuiteData createSuite(SuiteData suite, String token);

	public SuiteData updateSuite(SuiteData suite, String token);

	public TestData createTest(TestData test, String token);

	public TestData updateTest(TestData test, String token);

	public String uploadTestAttach(String testId, File file, String token);

	public String getToken();
}
