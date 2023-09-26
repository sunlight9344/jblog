package com.poscodx.jblog.exception;

public class FileUploadServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public FileUploadServiceException(String message) {
		super(message);
	}
	
	public FileUploadServiceException() {
		super("FileUploadServiceException ExceptionThrown");
	}
}
