package com.nemesis.testng.reporter;

import org.openqa.selenium.WebDriver;

public class WebDriverMap {

	private static final ThreadLocal<WebDriver> context = new ThreadLocal<WebDriver>();

	private static WebDriverMap instance = null;

	private WebDriverMap() {
	}

	public static WebDriverMap getInstance() {
		if (instance == null) {
			instance = new WebDriverMap();
		}
		return instance;
	}

	public WebDriver getDriverInstance() {
		return context.get();
	}

	public void setDriver(WebDriver driver) {
		context.set(driver);
	}

	public void closeDriver() {
		context.get().quit();
		removeThread();
	}

	public void removeThread() {
		context.remove();
	}

}
