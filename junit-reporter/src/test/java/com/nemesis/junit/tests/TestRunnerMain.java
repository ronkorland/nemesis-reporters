package com.nemesis.junit.tests;

import org.junit.runner.JUnitCore;

import com.nemesis.junit.reporter.JUnitMongoReporter;

public class TestRunnerMain {

	public static void main(String[] args) { 
		  JUnitCore core = new JUnitCore();
		  core.addListener(new JUnitMongoReporter());
		  core.run(DemoTestOne.class); 
		}
	
}
