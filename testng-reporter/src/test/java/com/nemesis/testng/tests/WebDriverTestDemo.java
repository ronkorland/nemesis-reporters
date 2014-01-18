package com.nemesis.testng.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.nemesis.testng.reporter.BaseTest;

public class WebDriverTestDemo extends BaseTest {

	@Test(groups = { "test", "ignore" })
	public void testFailure() {
		getDriver().get("http://www.google.com");

		sleep(5000);

		Assert.fail("Failure Reason");
	}

	@Test(groups = { "test", "work" })
	public void testSuccess() {
		Assert.assertTrue(true);
	}

}
