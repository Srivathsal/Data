package com.intuit.accountant.services.dcm.notifications;

import com.intuit.accountant.services.dcm.model.enums.EnumCode;
import com.intuit.accountant.services.dcm.model.enums.EnumCodeUtil;

public enum NotificationProvider implements EnumCode {
	UNKNOWN("unknown"),
	IUS("ius"),
	TXE("txe"),
	OINP("oinp");

	private final String code;

	NotificationProvider(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static NotificationProvider fromValue(String code) {
		return (NotificationProvider) EnumCodeUtil.searchEnum(NotificationProvider.class, code);
	}
}
