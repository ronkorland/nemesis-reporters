package com.nemesis.reporter.data;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.LocalDateTime;

import com.nemesis.reporter.type.JodaDateTimeDeserializer;
import com.nemesis.reporter.type.JodaDateTimeSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestData extends BaseData {

	private String suiteId;

	private String testName;

	private Status testStatus;

	@JsonSerialize(using = JodaDateTimeSerializer.class)
	@JsonDeserialize(using = JodaDateTimeDeserializer.class)
	private LocalDateTime startTime;

	@JsonSerialize(using = JodaDateTimeSerializer.class)
	@JsonDeserialize(using = JodaDateTimeDeserializer.class)
	private LocalDateTime endTime;

	private List<TestParameterData> parameters;

	private List<String> testAttachments;

	private List<String> logs;

	private FailureReasonData failureReason;

	private String runningTime;

	private String className;

	private String method;

	private List<String> testGroups;

	public TestData() {
		super();
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Status getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(Status testStatus) {
		this.testStatus = testStatus;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		if (endTime != null) {
			this.endTime = endTime;
		}
	}

	public List<String> getTestAttachments() {
		return testAttachments;
	}

	public void setTestAttachments(List<String> testAttachments) {
		this.testAttachments = testAttachments;
	}

	public FailureReasonData getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(FailureReasonData failureReason) {
		this.failureReason = failureReason;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<TestParameterData> getParameters() {
		return parameters;
	}

	public void setParameters(List<TestParameterData> parameters) {
		this.parameters = parameters;
	}

	public String getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<String> getTestGroups() {
		return testGroups;
	}

	public void setTestGroups(List<String> testGroups) {
		this.testGroups = testGroups;
	}

	public String getSuiteId() {
		return suiteId;
	}

	public void setSuiteId(String suiteId) {
		this.suiteId = suiteId;
	}

	public List<String> getLogs() {
		return logs;
	}

	public void setLogs(List<String> logs) {
		this.logs = logs;
	}
}
