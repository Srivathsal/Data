package com.intuit.accountant.services.dcm.notifications.exceptions;


public class NotificationProviderNotAvailableException extends NotificationProviderFactoryException {
	private static final long serialVersionUID = 4856067077487879489L;

	public NotificationProviderNotAvailableException() {
	}
	
	public NotificationProviderNotAvailableException(String message) {
		super(message);
	}

}
