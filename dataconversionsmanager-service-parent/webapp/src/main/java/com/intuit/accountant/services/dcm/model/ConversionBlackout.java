package com.intuit.accountant.services.dcm.model;

/**
 * Created by sshashidhar on 3/27/18.
 */
public class ConversionBlackout {
    private boolean isBlackoutEnabled;
    private String messageToDisplay;
    private int supportedYear;

    private ConversionBlackout(){}

    public ConversionBlackout(boolean isBlackoutEnabled, String messageToDisplay, int supportedYear) {
        this.isBlackoutEnabled = isBlackoutEnabled;
        this.messageToDisplay = messageToDisplay;
        this.supportedYear = supportedYear;
    }

    public boolean isBlackoutEnabled() {
        return isBlackoutEnabled;
    }

    public void setBlackoutEnabled(boolean blackoutEnabled) {
        isBlackoutEnabled = blackoutEnabled;
    }

    public String getMessageToDisplay() {
        return messageToDisplay;
    }

    public void setMessageToDisplay(String messageToDisplay) {
        this.messageToDisplay = messageToDisplay;
    }

    public int getSupportedYear() {
        return supportedYear;
    }

    public void setSupportedYear(int supportedYear) {
        this.supportedYear = supportedYear;
    }

    @Override
    public String toString() {
        return "ConversionBlackout{" +
                "isBlackoutEnabled=" + isBlackoutEnabled +
                ", messageToDisplay='" + messageToDisplay + '\'' +
                ", supportedYear=" + supportedYear +
                '}';
    }
}
