package com.blog.payLoads;

public class ApiResponse {

	public String message;
	public Boolean success;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ApiResponse(String message, Boolean success) {
		super();
		this.message = message;
		this.success = success;
	}
	public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
