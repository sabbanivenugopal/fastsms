package com.fastsms.pojo;

import java.util.List;

public class TestSms {
	private String ErrorCode;
	private String ErrorMessage;
	private String JobId;
	private List<MsgData>  MessageData;
	
	
	public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	public String getErrorMessage() {
		return ErrorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}
	public String getJobId() {
		return JobId;
	}
	public void setJobId(String jobId) {
		JobId = jobId;
	}
	public List<MsgData> getMessageData() {
		return MessageData;
	}
	public void setMessageData(List<MsgData> messageData) {
		MessageData = messageData;
	}
	
	

}
