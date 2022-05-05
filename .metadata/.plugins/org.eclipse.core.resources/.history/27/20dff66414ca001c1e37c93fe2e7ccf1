package com.intuit.accountant.services.dcm.notifications;

import com.intuit.accountant.services.dcm.model.enums.EnumCode;
import com.intuit.accountant.services.dcm.model.enums.EnumCodeUtil;

public enum NotificationType implements EnumCode {
	UNKNOWN("unknown"),
	EMAIL("email"),
	SMS("sms"),
	PUSH("push"),
	INVITE("invite");

	private final String code;

	private NotificationType(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static NotificationType fromValue(String code) {
		return (NotificationType) EnumCodeUtil.searchEnum(NotificationType.class, code);
	}
}
