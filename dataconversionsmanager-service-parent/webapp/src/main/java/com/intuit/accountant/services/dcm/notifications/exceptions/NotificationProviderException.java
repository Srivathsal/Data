package com.intuit.accountant.services.dcm.notifications.exceptions;

/**
 * Default exception for Notification Providers
 * @author Intuit PTG Services
 */
public class NotificationProviderException extends NotificationProviderFactoryException {
	private static final long serialVersionUID = 8746385606378030409L;

	public NotificationProviderException() {
		super();
	}

	public NotificationProviderException(String message) {
		super(message);
	}

	public NotificationProviderException(Throwable cause) {
		super(cause);
	}

	public NotificationProviderException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotificationProviderException(String message,	Throwable cause, boolean enableSuppression,	boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
