package com.example.Template.Model;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.http.HttpStatus;

public class ResponseBody<T> {

	private String status;
	
	private int statusCode;
	
	private T data;
	
	private Timestamp timestamp;

	public ResponseBody(String status, int statusCode, T data) {
		
		this.status = status;
		this.statusCode = statusCode;
		this.data = data;
		timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("GMT+8")));
	}

	public ResponseBody() {
		
	}

	
	public void setStatus(String status) {
		this.status = status;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	public void setData(T data) {
		this.data = data;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public String getStatus() {
		return status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public T getData() {
		return data;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	
	
	
}
