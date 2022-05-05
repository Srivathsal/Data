package com.intuit.accountant.services.dcm.notifications.exceptions;

import com.intuit.accountant.services.dcm.exceptions.DCMServiceException;

/**
 * Default exception for the notification provider factory
 * @author Intuit PTG Services
 */
public class NotificationProviderFactoryException extends DCMServiceException {
	private static final long serialVersionUID = -6949995090414662799L;

	public NotificationProviderFactoryException() {
		super();
	}

	public NotificationProviderFactoryException(String message) {
		super(message);
	}

	public NotificationProviderFactoryException(Throwable cause) {
		super(cause);
	}

	public NotificationProviderFactoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotificationProviderFactoryException(String message,	Throwable cause, boolean enableSuppression,	boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
