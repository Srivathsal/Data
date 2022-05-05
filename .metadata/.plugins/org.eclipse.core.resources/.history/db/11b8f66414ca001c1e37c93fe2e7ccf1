package com.intuit.accountant.services.dcm.notifications;

import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationResponse {

	private List<RecipientResponse> recipientResponses = new ArrayList<RecipientResponse>();

	Map<String, String> recipientStatus = new HashMap<>();

	public NotificationResponse() {
		super();
	}

	public void addSuccessRecipient(String recipient) {
		RecipientResponse response = new RecipientResponse(recipient);
		response.setStatusCode(Status.OK);
		recipientResponses.add(response);
	}

	public void addFailureRecipient(String recipient, String explanation) {
		RecipientResponse response = new RecipientResponse(recipient, explanation);
		recipientResponses.add(response);
	}
	
	public void addFailureRecipient(String recipient, String explanation, Status statusCode) {
		RecipientResponse response = new RecipientResponse(recipient, explanation, statusCode);
		recipientResponses.add(response);
	}

	public List<RecipientResponse> getRecipientResponses() {
		return this.recipientResponses;
	}

	public List<RecipientResponse> getValidRecipientResponses() {
		List<RecipientResponse> allResponses = getRecipientResponses();
		List<RecipientResponse> validList = new ArrayList<RecipientResponse>();
		for (RecipientResponse response : allResponses) {
			if (response.isValid()) {
				validList.add(response);
			}
		}
		return validList;
	}

	public List<RecipientResponse> getFailedRecipientResponses() {
		List<RecipientResponse> allResponses = getRecipientResponses();
		List<RecipientResponse> failedList = new ArrayList<RecipientResponse>();
		for (RecipientResponse response : allResponses) {
			if (response.isValid()) {
				failedList.add(response);
			}
		}
		return failedList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append("(recipientResponses=").append(getRecipientResponses()).append(")");
		return builder.toString();
	}

}