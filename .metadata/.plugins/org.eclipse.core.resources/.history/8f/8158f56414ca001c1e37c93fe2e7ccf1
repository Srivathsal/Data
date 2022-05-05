package com.intuit.accountant.services.dcm.model;

import java.util.List;
import java.util.Map;

public class JobPayload {

	private List<JobDocument> sourceDocs;
	private List<JobDocument> convertedDocs;
	private String can;
	private String realmId;
	private String sourceProduct;
	private String destinationProduct;
	private String taxYear;
	private String toTaxYear; 
	private String notifyEmailAddress;
	private String conversionKey;
    private String firmId;
    private String userName;
	private boolean isAutomatedJob;
	private ConversionStatus conversionStatus;
private String language;

	public List<JobDocument> getSourceDocs() {
		return sourceDocs;
	}
	public void setSourceDocs(List<JobDocument> sourceDocs) {
		this.sourceDocs = sourceDocs;
	}
	public List<JobDocument> getConvertedDocs() {
		return convertedDocs;
	}
	public void setConvertedDocs(List<JobDocument> convertedDocs) {
		this.convertedDocs = convertedDocs;
	}
	public String getCan() {
		return can;
	}
	public void setCan(String can) {
		this.can = can;
	}
	public String getRealmId() {
		return realmId;
	}
	public void setRealmId(String realmId) {
		this.realmId = realmId;
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
	public String getTaxYear() {
		return taxYear;
	}
	public void setTaxYear(String taxYear) {
		this.taxYear = taxYear;
	}
	public String getNotifyEmailAddress() {
		return notifyEmailAddress;
	}
	public void setNotifyEmailAddress(String notifyEmailAddress) {
		this.notifyEmailAddress = notifyEmailAddress;
	}
	public String getConversionKey() {
		return conversionKey;
	}
	public void setConversionKey(String conversionKey) {
		this.conversionKey = conversionKey;
	}

    public String getFirmId() {
        return firmId;
    }

    public void setFirmId(String firmId) {
        this.firmId = firmId;
    }
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean getIsAutomatedJob() {
		return isAutomatedJob;
	}
	public void setIsAutomatedJob(boolean isAutomatedJob) {
		this.isAutomatedJob = isAutomatedJob;
	}
	public String getToTaxYear() {
		return toTaxYear;
	}
	public void setToTaxYear(String toTaxYear) {
		this.toTaxYear = toTaxYear;
	}

	public ConversionStatus getConversionStatus() {
		return conversionStatus;
	}

	public void setConversionStatus(ConversionStatus conversionStatus) {
		this.conversionStatus = conversionStatus;
	}

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

	public boolean isCanadaJob(Map<String,String> canadaAutomatableJobs){
		boolean isCanadaJob=false;
		for (String key: canadaAutomatableJobs.keySet()
			 ) {
			if(canadaAutomatableJobs.get(key).toLowerCase().indexOf(this.sourceProduct.toLowerCase())!=-1) {
				isCanadaJob = true;
				break;
			}

		}
		return isCanadaJob;
	}

	public boolean isProtaxJob(JobPayload jobPayload){

		return jobPayload.destinationProduct.toLowerCase().equalsIgnoreCase("protax") ? true: false;

	}

}
