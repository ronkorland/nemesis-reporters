package com.nemesis.testng.listeners;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.nemesis.testng.WebDriverMap;

public class WebDriverScreenShotOnFailAdapter extends TestListenerAdapter {

	private WebDriverMap webDriverMap = WebDriverMap.getInstance();

	@Override
	public void onTestFailure(ITestResult testResult) {
		WebDriver driver = null;
		driver = webDriverMap.getDriverInstance();

		if (driver != null) {
			try {
				File screenshotFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				String outputFolder = new File(testResult.getTestContext()
						.getOutputDirectory()).getParent();
				File destFile = new File(outputFolder + File.separator
						+ "ScreenShots" + File.separator
						+ screenshotFile.getName());
				FileUtils.moveFile(screenshotFile, destFile);

				Reporter.setCurrentTestResult(testResult);
				Reporter.log("Screenshot saved @ " + destFile);
				Reporter.setCurrentTestResult(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
