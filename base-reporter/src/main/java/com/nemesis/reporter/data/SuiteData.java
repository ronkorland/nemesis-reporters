package com.nemesis.reporter.data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.LocalDateTime;

import com.nemesis.reporter.type.JodaDateTimeDeserializer;
import com.nemesis.reporter.type.JodaDateTimeSerializer;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuiteData extends BaseData {

	private String suiteName;

	@JsonSerialize(using = JodaDateTimeSerializer.class)
	@JsonDeserialize(using = JodaDateTimeDeserializer.class)
	private LocalDateTime startTime;

	@JsonSerialize(using = JodaDateTimeSerializer.class)
	@JsonDeserialize(using = JodaDateTimeDeserializer.class)
	private LocalDateTime endTime;

	public SuiteData() {
		super();
	}

	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
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
		this.endTime = endTime;
	}
}
