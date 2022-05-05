package com.intuit.accountant.services.dcm.model.enums;

public enum JobStatus implements EnumCode {
	NEW("New"),
    INPROGRESS("InProgress"),
    REJECTED("Rejected"),
    COMPLETED("Completed"),
    IMPORTFAILED("ImportFailed"),
    COMPLETEDWITHDUPLICATES("CompletedWithDuplicates"),
    CANCELLED("Cancelled"),
    DATAAVAILABLE("DataAvailable"),
    FAILURE("Failure"),
    DATAAVAILABLEFORPROTAX("DataAvailableForProtax"),
    DOWNLOADCOMPLETE("DOWNLOAD_COMPLETE");



    private final String code;

    private JobStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static JobStatus fromValue(String code) {
        return (JobStatus) EnumCodeUtil.searchEnum(JobStatus.class, code);
    }

}