package com.intuit.accountant.services.dcm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class AutomationCriterion {

	private String competitorProduct;
	private String intuitProduct;
	private String taxYearFrom;
	private String taxYearTo;
	private int statusPollIntervalInSeconds;
	private int conversionPollIntervalInSeconds;
	
	public AutomationCriterion(String competitorProduct,
                               String intuitProduct, String taxYearFrom, String taxYearTo) {
		this.competitorProduct = competitorProduct;
		this.intuitProduct = intuitProduct;
		this.taxYearFrom = taxYearFrom;
		this.taxYearTo = taxYearTo;
	}
	
	public AutomationCriterion(String competitorProduct, String intuitProduct, String taxYearFrom,
                               String taxYearTo, int statusPollTimeInSeconds, int conversionPollTimeInSeconds) {
		this.competitorProduct = competitorProduct;
		this.intuitProduct = intuitProduct;
		this.taxYearFrom = taxYearFrom;
		this.taxYearTo = taxYearTo;
		this.statusPollIntervalInSeconds= statusPollTimeInSeconds;
		this.conversionPollIntervalInSeconds= conversionPollTimeInSeconds;
	}

	public String getCompetitorProduct() {
		return competitorProduct;
	}
	public void setCompetitorProduct(String competitorProduct) {
		this.competitorProduct = competitorProduct;
	}

	public String getIntuitProduct() {
		return intuitProduct;
	}

	public void setIntuitProduct(String intuitProduct) {
		this.intuitProduct = intuitProduct;
	}

	public String getTaxYearFrom() {
		return taxYearFrom;
	}

	public void setTaxYearFrom(String taxYearFrom) {
		this.taxYearFrom = taxYearFrom;
	}

	public String getTaxYearTo() {
		return taxYearTo;
	}

	public void setTaxYearTo(String taxYearTo) {
		this.taxYearTo = taxYearTo;
	}

	public int getStatusPollIntervalInSeconds() {
		return statusPollIntervalInSeconds;
	}

	public void setStatusPollIntervalInSeconds(int statusPollIntervalInSeconds) {
		this.statusPollIntervalInSeconds = statusPollIntervalInSeconds;
	}

	public int getConversionPollIntervalInSeconds() {
		return conversionPollIntervalInSeconds;
	}

	public void setConversionPollIntervalInSeconds(
			int conversionPollIntervalInSeconds) {
		this.conversionPollIntervalInSeconds = conversionPollIntervalInSeconds;
	}

}
