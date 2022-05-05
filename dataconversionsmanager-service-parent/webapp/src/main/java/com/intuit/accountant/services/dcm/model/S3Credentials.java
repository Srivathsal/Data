package com.intuit.accountant.services.dcm.model;

/**
 * Created by sshashidhar on 17/05/19.
 */
public class S3Credentials {

    private String accessKey;
    private String accessSecret;
    private String accessToken;
    private String expiration;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "S3Credentials{" +
                "accessKey='" + accessKey + '\'' +
                ", accessSecret='" + accessSecret + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expiration='" + expiration + '\'' +
                '}';
    }
}
