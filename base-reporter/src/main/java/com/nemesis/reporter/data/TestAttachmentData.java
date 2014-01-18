package com.nemesis.reporter.data;

import java.io.File;

public class TestAttachmentData {

	private String testId;

	private File file;

	public TestAttachmentData() {
		super();
	}

	public TestAttachmentData(String testId, File file) {
		this();
		this.file = file;
		this.testId = testId;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
