package com.intuit.accountant.services.dcm.model;

/**
 * Created by sshashidhar on 19/02/19.
 */
public class JmsStats {

    private String jmsContainerName;
    private boolean jmsContainerRunning;
    private boolean jmsContainerActive;
    private boolean jmsContainerAutoStartup;
    private int jmsContainerActiveConsumerCount;

    public String getJmsContainerName() {
        return jmsContainerName;
    }

    public void setJmsContainerName(String jmsContainerName) {
        this.jmsContainerName = jmsContainerName;
    }

    public boolean isJmsContainerRunning() {
        return jmsContainerRunning;
    }

    public void setJmsContainerRunning(boolean jmsContainerRunning) {
        this.jmsContainerRunning = jmsContainerRunning;
    }

    public boolean isJmsContainerActive() {
        return jmsContainerActive;
    }

    public void setJmsContainerActive(boolean jmsContainerActive) {
        this.jmsContainerActive = jmsContainerActive;
    }

    public boolean isJmsContainerAutoStartup() {
        return jmsContainerAutoStartup;
    }

    public void setJmsContainerAutoStartup(boolean jmsContainerAutoStartup) {
        this.jmsContainerAutoStartup = jmsContainerAutoStartup;
    }

    public int getJmsContainerActiveConsumerCount() {
        return jmsContainerActiveConsumerCount;
    }

    public void setJmsContainerActiveConsumerCount(int jmsContainerActiveConsumerCount) {
        this.jmsContainerActiveConsumerCount = jmsContainerActiveConsumerCount;
    }

    @Override
    public String toString() {
        return "JmsStats{" +
                "jmsContainerName='" + jmsContainerName + '\'' +
                ", jmsContainerRunning=" + jmsContainerRunning +
                ", jmsContainerActive=" + jmsContainerActive +
                ", jmsContainerAutoStartup=" + jmsContainerAutoStartup +
                ", jmsContainerActiveConsumerCount=" + jmsContainerActiveConsumerCount +
                '}';
    }
}
