package com.ssn.aso.common;

public class Result {
	private int statusCode;
	private String message;

	public Result() {
	}

	public Result(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getstatusCode() {
		return statusCode;
	}

	public void setstatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}