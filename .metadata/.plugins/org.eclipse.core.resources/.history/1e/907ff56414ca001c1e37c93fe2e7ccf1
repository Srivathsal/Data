package com.intuit.accountant.services.dcm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sshashidhar on 26/12/18.
 */
public class OINPEmailRequest {
    private String name;
    private String sourceServiceName;
    private String sourceObjectId;
    private String sourceObjectType;

    @JsonProperty("eventData")
    private OINPEventData oinpEventData;

    @JsonProperty("eventMetaData")
    private OINPEventMetaData oinpEventMetaData;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceServiceName() {
        return sourceServiceName;
    }

    public void setSourceServiceName(String sourceServiceName) {
        this.sourceServiceName = sourceServiceName;
    }

    public String getSourceObjectId() {
        return sourceObjectId;
    }

    public void setSourceObjectId(String sourceObjectId) {
        this.sourceObjectId = sourceObjectId;
    }

    public String getSourceObjectType() {
        return sourceObjectType;
    }

    public void setSourceObjectType(String sourceObjectType) {
        this.sourceObjectType = sourceObjectType;
    }

    public OINPEventData getOinpEventData() {
        return oinpEventData;
    }

    public void setOinpEventData(OINPEventData oinpEventData) {
        this.oinpEventData = oinpEventData;
    }

    public OINPEventMetaData getOinpEventMetaData() {
        return oinpEventMetaData;
    }

    public void setOinpEventMetaData(OINPEventMetaData oinpEventMetaData) {
        this.oinpEventMetaData = oinpEventMetaData;
    }

    @Override
    public String toString() {
        return "OINPEmailRequest{" +
                "name='" + name + '\'' +
                ", sourceServiceName='" + sourceServiceName + '\'' +
                ", sourceObjectId='" + sourceObjectId + '\'' +
                ", sourceObjectType='" + sourceObjectType + '\'' +
                ", oinpEventData=" + oinpEventData +
                ", oinpEventMetaData=" + oinpEventMetaData +
                '}';
    }
}
