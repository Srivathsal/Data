package com.intuit.accountant.services.dcm.model;

/**
 * Created by sshashidhar on 17/05/19.
 */
public class DocumentResponse {
    private EndpointInfo endpointInfo;
    private S3Credentials s3Credentials;
    private String asyncStatus;
    private int statusCode;
    private String errorMessage;
    private String docId;

    public EndpointInfo getEndpointInfo() {
        return endpointInfo;
    }

    public void setEndpointInfo(EndpointInfo endpointInfo) {
        this.endpointInfo = endpointInfo;
    }

    public S3Credentials getS3Credentials() {
        return s3Credentials;
    }

    public void setS3Credentials(S3Credentials s3Credentials) {
        this.s3Credentials = s3Credentials;
    }

    public String getAsyncStatus() {
        return asyncStatus;
    }

    public void setAsyncStatus(String asyncStatus) {
        this.asyncStatus = asyncStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
        return "DocumentResponse{" +
                "endpointInfo=" + endpointInfo +
                ", s3Credentials=" + s3Credentials +
                ", asyncStatus='" + asyncStatus + '\'' +
                ", statusCode=" + statusCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", docId='" + docId + '\'' +
                '}';
    }
}
