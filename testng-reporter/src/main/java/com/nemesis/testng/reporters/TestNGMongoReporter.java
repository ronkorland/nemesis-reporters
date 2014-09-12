package com.nemesis.testng.reporters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDateTime;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Sets;
import org.testng.xml.XmlSuite;

import com.nemesis.reporter.MongoDbRestClient;
import com.nemesis.reporter.MongoDbRestClientImpl;
import com.nemesis.reporter.data.FailureReasonData;
import com.nemesis.reporter.data.IDataProvider;
import com.nemesis.reporter.data.Status;
import com.nemesis.reporter.data.SuiteData;
import com.nemesis.reporter.data.TestAttachmentData;
import com.nemesis.reporter.data.TestData;
import com.nemesis.reporter.data.TestParameterData;

public class TestNGMongoReporter implements IReporter, ISuiteListener {

	private MongoDbRestClient restClient = null;

	private static final String XML_NAME = "mongodb.xml";

	private List<TestAttachmentData> attachments = null;

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		attachments = new ArrayList<TestAttachmentData>();
		try {
			if (!FileUtils.directoryContains(new File(outputDirectory), new File(outputDirectory + File.separator
					+ XML_NAME))) {
				restClient = new MongoDbRestClientImpl();
				if (suites != null && suites.size() > 0) {
					for (ISuite iSuite : suites) {
						SuiteData suiteData = buildSuite(iSuite);
						suiteData = restClient.createSuite(suiteData);

						List<TestData> testDatas = buildTests(suiteData.getId(), iSuite);
						if (testDatas != null && testDatas.size() > 0) {
							for (TestData testData : testDatas) {
								restClient.createTest(testData);
							}
						}
					}
				}

				if (attachments != null && attachments.size() > 0) {
					for (TestAttachmentData attachment : attachments) {
						restClient.uploadTestAttach(attachment.getTestId(), attachment.getFile());
					}
				}

				FileUtils.write(new File(outputDirectory + File.separator + XML_NAME), "");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<TestParameterData> buildTestParameters(ITestResult testResult) {
		List<TestParameterData> testParameter = new ArrayList<TestParameterData>();

		Object[] parameters = testResult.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i] instanceof IDataProvider) {
				IDataProvider a = (IDataProvider) parameters[i];
				Map<String, String> map = a.getMap();
				if (map != null && map.size() > 0) {
					for (Entry<String, String> entry : map.entrySet()) {
						TestParameterData parameter = new TestParameterData();
						parameter.setParamName(entry.getKey());
						parameter.setParamValue(entry.getValue());
						parameter.setParamSource("");
						testParameter.add(parameter);
					}
				}
			}
		}

		return testParameter;
	}

	public void addAttach(String testId, ITestResult result) {
		List<String> outputs = Reporter.getOutput(result);
		if (outputs != null && outputs.size() > 0) {
			for (String output : outputs) {
				if (output.contains("Screenshot")) {
					String[] fileString = output.split("@");
					System.out.println(fileString);
					File file = new File(fileString[1].trim());
					TestAttachmentData attach = new TestAttachmentData(testId, file);
					attachments.add(attach);
				}
			}
		}
	}

	private Set<ITestResult> buildTestResults(ISuite suite) {
		Set<ITestResult> testResults = Sets.newHashSet();
		for (Entry<String, ISuiteResult> entry : suite.getResults().entrySet()) {

			ITestContext testContext = entry.getValue().getTestContext();
			addAllTestResults(testResults, testContext.getPassedTests());
			addAllTestResults(testResults, testContext.getFailedTests());
			addAllTestResults(testResults, testContext.getSkippedTests());
			addAllTestResults(testResults, testContext.getFailedButWithinSuccessPercentageTests());
		}
		return testResults;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addAllTestResults(Set<ITestResult> testResults, IResultMap resultMap) {
		if (resultMap != null) {
			// Sort the results chronologically before adding them
			List<ITestResult> allResults = new ArrayList<ITestResult>();
			allResults.addAll(resultMap.getAllResults());

			Collections.sort(new ArrayList(allResults), new Comparator<ITestResult>() {
				@Override
				public int compare(ITestResult o1, ITestResult o2) {
					return (int) (o1.getStartMillis() - o2.getStartMillis());
				}
			});

			testResults.addAll(allResults);
		}
	}

	private List<TestData> buildTests(String suiteId, ISuite suite) {
		Set<ITestResult> testResults = buildTestResults(suite);

		List<TestData> tests = new ArrayList<TestData>();
		for (ITestResult testResult : testResults) {
			TestData test = new TestData();
			test.setSuiteId(suiteId);
			test.setClassName(testResult.getInstanceName());
			test.setMethod(getMethodName(testResult));
			test.setTestName(testResult.getTestContext().getName());
			test.setEndTime(new LocalDateTime(testResult.getEndMillis()));
			test.setStartTime(new LocalDateTime(testResult.getStartMillis()));
			test.setTestStatus(Status.fromInt(testResult.getStatus()));
			test.setTestGroups(Arrays.asList(testResult.getMethod().getGroups()));
			test.setParameters(buildTestParameters(testResult));
			if (testResult.getThrowable() != null) {
				test.setFailureReason(FailureReasonData.buildFailureReason(testResult.getThrowable()));
			}
			addAttach(test.getId(), testResult);

			tests.add(test);
		}
		return tests;
	}

	protected String getMethodName(ITestResult tr) {
		StringBuffer stringBuffer = new StringBuffer();
		String name = tr.getName();
		stringBuffer.append(name);

		Object[] parameters = tr.getParameters();
		if (parameters != null && parameters.length > 0) {
			stringBuffer.append("(");
			for (int i = 0; i < parameters.length; i++) {
				String string = "";
				if (parameters[i] instanceof IDataProvider) {
					IDataProvider a = (IDataProvider) parameters[i];
					string = a.getDisplayName();
				} else {
					string = parameters[i].toString();
				}

				stringBuffer.append(string);
				if (i != parameters.length - 1) {
					stringBuffer.append(",");
				}
			}
			stringBuffer.append(")");
		}
		return stringBuffer.toString();
	}

	private SuiteData buildSuite(ISuite suite) {
		SuiteData suiteData = new SuiteData();
		Map<String, ISuiteResult> results = suite.getResults();
		LocalDateTime minStartDate = new LocalDateTime();
		LocalDateTime maxEndDate = null;

		for (Map.Entry<String, ISuiteResult> result : results.entrySet()) {
			ITestContext testContext = result.getValue().getTestContext();
			LocalDateTime startDate = new LocalDateTime(testContext.getStartDate());
			LocalDateTime endDate = new LocalDateTime(testContext.getEndDate());
			if (minStartDate.isAfter(startDate)) {
				minStartDate = startDate;
			}
			if (maxEndDate == null || maxEndDate.isBefore(endDate)) {
				maxEndDate = endDate != null ? endDate : startDate;
			}
		}

		// The suite could be completely empty
		if (maxEndDate == null) {
			maxEndDate = minStartDate;
		}

		suiteData.setStartTime(minStartDate);
		suiteData.setEndTime(maxEndDate);
		suiteData.setSuiteName(suite.getName());

		return suiteData;
	}

	@Override
	public void onStart(ISuite suite) {
		File directory = new File(suite.getOutputDirectory());
		FileUtils.deleteQuietly(new File(directory.getParent() + File.separator + XML_NAME));

	}

	@Override
	public void onFinish(ISuite suite) {
	}
}
