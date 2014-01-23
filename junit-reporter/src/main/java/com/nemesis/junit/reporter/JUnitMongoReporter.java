package com.nemesis.junit.reporter;

import java.util.Calendar;

import org.joda.time.LocalDateTime;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.nemesis.reporter.MongoDbRestClient;
import com.nemesis.reporter.MongoDbRestClientImpl;
import com.nemesis.reporter.data.FailureReasonData;
import com.nemesis.reporter.data.Status;
import com.nemesis.reporter.data.SuiteData;
import com.nemesis.reporter.data.TestData;

public class JUnitMongoReporter extends RunListener {

	// private String suiteId = null;
	private SuiteData currentSuite = null;
	private TestData currentTest = null;
	private MongoDbRestClient restClient = new MongoDbRestClientImpl();

	@Override
	public void testRunStarted(Description description) throws Exception {
		System.out.println("testRunStarted");
		if (currentSuite == null) {
			String displayName = null;
			if (description != null) {
				displayName = description.getChildren().get(0).getDisplayName();
			} else {
				displayName = "Default Suite";
			}

			SuiteData suiteData = new SuiteData();
			suiteData.setStartTime(LocalDateTime.now());

			String[] displayNameArry = displayName.split("\\.");
			displayName = displayNameArry[displayNameArry.length - 1];

			suiteData.setSuiteName(displayName);
			currentSuite = restClient.createSuite(suiteData);
		}
	}

	@Override
	public void testRunFinished(Result result) throws Exception {
		System.out.println("testRunFinished");
		if (currentSuite != null) {
			currentSuite.setEndTime(LocalDateTime.now());
			restClient.updateSuite(currentSuite);
		}

		currentSuite = null;
	}

	@Override
	public void testStarted(Description description) throws Exception {
		TestData test = new TestData();
		if (currentSuite != null) {
			test.setSuiteId(currentSuite.getId());
		}
		test.setClassName(description.getClassName());
		test.setMethod(description.getMethodName());
		test.setTestName(description.getDisplayName());
		test.setStartTime(LocalDateTime.now());
		test.setTestStatus(Status.SUCCESS);
		currentTest = test;
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		if (currentTest != null) {
			FailureReasonData failureReason = FailureReasonData
					.buildFailureReason(failure.getException());
			currentTest.setFailureReason(failureReason);
			currentTest.setTestStatus(Status.FAILURE);
		}
	}

	@Override
	public void testFinished(Description description) throws Exception {
		currentTest.setEndTime(LocalDateTime.now());
		restClient.createTest(currentTest);
		currentTest = null;
	}
}
