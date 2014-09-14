package com.nemesis.reporter.data;

import java.util.List;

public class TestParameterData {

	private String paramName;

	private String paramValue;

	private String paramSource;

	public TestParameterData() {
		super();
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamSource() {
		return paramSource;
	}

	public void setParamSource(String paramSource) {
		this.paramSource = paramSource;
	}

	public String getParamName() {
		return paramName;
	}

	public static boolean paramExists(List<TestParameterData> l, String paramName) {
		if (l != null) {
			for (TestParameterData t : l) {
				if (t.getParamName().equalsIgnoreCase(paramName)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "TestParameterData [paramName=" + paramName + ", paramValue=" + paramValue + ", paramSource="
				+ paramSource + "]";
	}
}
