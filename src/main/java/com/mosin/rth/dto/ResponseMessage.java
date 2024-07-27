package com.mosin.rth.dto;

public class ResponseMessage {

	private String message;

	public ResponseMessage(String message) {
		super();
		this.message = message;
	}

	public ResponseMessage() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResponseMessage [message=" + message + "]";
	}
}
