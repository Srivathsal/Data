package com.intuit.accountant.services.dcm.model;

/**
 * Created by sshashidhar on 26/12/18.
 */
public class OINPEventMetaData {
    private String authId;
    private String createdDate;
    private String tid;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "OINPEventMetaData{" +
                "authId='" + authId + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", tid='" + tid + '\'' +
                '}';
    }
}
