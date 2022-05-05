package com.intuit.accountant.services.dcm.notifications;

import javax.ws.rs.core.Response.Status;

public class RecipientResponse {

	private String recipientIdentifier;
	private boolean valid;
	private String explanation;
	
	private Status statusCode;
	public Status getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Status statusCode) {
		this.statusCode = statusCode;
	}

	public RecipientResponse(String id) {
		this(id, null);
	}

	public RecipientResponse(String id, String explanation) {
		super();
		this.recipientIdentifier = id;
		this.valid = (explanation == null);
		this.explanation = explanation;
	}

	public RecipientResponse(String recipient, String explanation, Status statusCode) {
		this(recipient, explanation);
		this.statusCode = statusCode;
	}
	public String getRecipientIdentifier() {
		return recipientIdentifier;
	}

	public boolean isValid() {
		return valid;
	}

	public String getExplanation() {
		return explanation;
	}

	@Override
	public boolean equals(Object o) {
		boolean isEqual = false;
		if (o instanceof RecipientResponse) {
			RecipientResponse that = (RecipientResponse)o;
			isEqual = getRecipientIdentifier().equals(that.getRecipientIdentifier());
		}
		return isEqual;
	}

	@Override
	public int hashCode() {
		return getRecipientIdentifier().hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append("(recipientIdentifier=").append(getRecipientIdentifier());
		builder.append(",isValid=").append(isValid());
		builder.append(",explanation=").append(getExplanation());
		builder.append(")");
		return builder.toString();
	}

}