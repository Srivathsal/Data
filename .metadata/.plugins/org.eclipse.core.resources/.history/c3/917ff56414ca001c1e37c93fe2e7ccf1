package com.intuit.accountant.services.dcm.model;

/**
 * Created by sshashidhar on 26/12/18.
 */
public class OINPEventData {
    private String jobId;
    private String fullName;
    private String notifyEmailAddress;
    private ConversionStatus conversionStatus;
    private String sourceProduct;
    private String destinationProduct;
    private String jobStatus;
    private String customMessage;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNotifyEmailAddress() {
        return notifyEmailAddress;
    }

    public void setNotifyEmailAddress(String notifyEmailAddress) {
        this.notifyEmailAddress = notifyEmailAddress;
    }

    public ConversionStatus getConversionStatus() {
        return conversionStatus;
    }

    public void setConversionStatus(ConversionStatus conversionStatus) {
        this.conversionStatus = conversionStatus;
    }

    public String getSourceProduct() {
        return sourceProduct;
    }

    public void setSourceProduct(String sourceProduct) {
        this.sourceProduct = sourceProduct;
    }

    public String getDestinationProduct() {
        return destinationProduct;
    }

    public void setDestinationProduct(String destinationProduct) {
        this.destinationProduct = destinationProduct;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return "OINPEventData{" +
                "jobId='" + jobId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", notifyEmailAddress='" + notifyEmailAddress.replaceAll("\\b[\\w\\.-]+@","****@") + '\'' +
                ", conversionStatus=" + conversionStatus +
                ", sourceProduct='" + sourceProduct + '\'' +
                ", destinationProduct='" + destinationProduct + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", customMessage='" + customMessage + '\'' +
                '}';
    }
}
