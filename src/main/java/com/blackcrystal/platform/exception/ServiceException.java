package com.blackcrystal.platform.exception;

import com.blackcrystal.platform.enums.ServiceErrorCode;

/**
 * 
 * @author j
 * 
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2690048443199565223L;

	private ServiceErrorCode errorCode;

	public ServiceException(ServiceErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ServiceException(ServiceErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ServiceException(ServiceErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public ServiceException(ServiceErrorCode errorCode, String message,
			Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = ServiceErrorCode.SYSERROR;
	}

	public ServiceException(String message) {
		super(message);
		this.errorCode = ServiceErrorCode.SYSERROR;
	}

	public ServiceErrorCode getErrorCode() {
		return this.errorCode;
	}

}
