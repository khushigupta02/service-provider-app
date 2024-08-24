package com.example.myApplication.Enum;

public enum Message {
	SERVICE_BOOKING_REQUEST_SENT("Service booking request sent"),
	SERVICE_BOOKING_CANCELLED("Service booking request cancel by Customer"),
	SERVICE_REBOOK("Service Rebook Request sent by Customer"),
	SERVICE_REVIEW_DONE("Service Review Done by Customer");


	private final String message;

	Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}