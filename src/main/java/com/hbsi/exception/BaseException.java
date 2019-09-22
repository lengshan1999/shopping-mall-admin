package com.hbsi.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseException extends RuntimeException{
	
	private ExceptionEnum e;
	
	private String msg;
	
	private String code;
	
	public BaseException(ExceptionEnum e) {
		super(e.getMessage());
		this.msg = e.getStatus();
	}
	
	public BaseException(ExceptionEnum e,String msg) {
		super(e.getMessage());
		this.msg = e.getStatus();
	}

}
