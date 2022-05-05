package com.intuit.accountant.services.dcm.notifications;

import java.util.List;
import java.util.Map;

public class NotificationRequest {

	private String realmId;
	private String authId;
	private String ticket;

	public NotificationRequest() {
	}

	private List<String> recipients;
	public List<String> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	private String locale = "en-US";
	public String getLocale() {
		return locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	private String sender = "dataconversions@intuit.com";
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	private String subject;
	public String getSubject() {
		return subject;
	}	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	private String templateId;
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	private Map<String, String> templateVariables;
	public Map<String, String> getTemplateVariables() {
		return templateVariables;
	}
	public void setTemplateVariables(Map<String, String> templateVariables) {
		this.templateVariables = templateVariables;
	}
	
	private Map<String, String> providerProperties;
	public Map<String, String> getProviderProperties() {
		return providerProperties;
	}
	public void setProviderProperties(Map<String, String> providerProperties) {
		this.providerProperties = providerProperties;
	}

    public String replyTo;
    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String senderName;
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String appName;
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

	private String body;
	private String reminderId;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getReminderId() {
		return reminderId;
	}

	public void setReminderId(String reminderId) {
		this.reminderId = reminderId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}

	public String getRealmId() {
		return realmId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getAuthId() {
		return authId;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTicket() {
		return ticket;
	}
}
