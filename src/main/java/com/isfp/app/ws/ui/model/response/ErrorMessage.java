package com.isfp.app.ws.ui.model.response;

import java.util.Date;

public class ErrorMessage {

	private Date timeStamp;
	private String Message;
	

	public ErrorMessage() {
		
	}

	public ErrorMessage(Date timeStamp, String message) {
		super();
		this.timeStamp = timeStamp;
		Message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
