package com.nemesis.testng;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Listeners(value = {
		com.nemesis.testng.listeners.WebDriverScreenShotOnFailAdapter.class,
		com.nemesis.testng.reporters.TestNGMongoReporter.class })
public class BaseTest {

	private String userName;
	private String password;

	private WebDriverMap webDriverMap = WebDriverMap.getInstance();

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "userName", "password" })
	public void beforeMethod(@Optional("ron.korland") String userName,
			@Optional("123456789") String password) {
		this.password = password;
		this.userName = userName;
		WebDriver driver = new FirefoxDriver();
		webDriverMap.setDriver(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(Method method) {
		webDriverMap.closeDriver();
	}

	public WebDriver getDriver() {
		return webDriverMap.getDriverInstance();
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void sleep(long wait) {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
