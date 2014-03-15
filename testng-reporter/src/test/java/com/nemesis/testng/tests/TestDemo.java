package com.nemesis.testng.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(value = { com.nemesis.testng.reporters.TestNGMongoReporter.class })
public class TestDemo {

	@Test(invocationCount = 23)
	public void testSuccess() {
		Assert.assertTrue(true);
	}

	@Test(invocationCount = 3)
	public void testFailed() {
		Assert.assertTrue(false, "Failed");
	}

}
