package com.nemesis.reporter.data;

public enum Status {

	SUCCESS, FAILURE, SKIP;

	public static Status fromInt(int value) {
		switch (value) {
		case 1:
			return SUCCESS;
		case 2:
			return FAILURE;
		case 3:
			return SKIP;
		}
		return null;
	}
}
