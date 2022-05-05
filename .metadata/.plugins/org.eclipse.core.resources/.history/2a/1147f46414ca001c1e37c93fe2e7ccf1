package com.intuit.accountant.services.dcm.messaging;

import clover.org.apache.commons.lang3.StringUtils;
import com.intuit.accountant.services.dcm.util.DCMUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.management.JMSConnectionStatsImpl;
import org.apache.activemq.management.JMSStatsImpl;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by sshashidhar on 07/11/18.
 */

@Component("managerMessageListener")
public class ManagerMessageListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
	private ManagerMessageProcessor managerMessageProcessor;

    @Autowired
	@Qualifier("activeMQconnectionFactory")
    private ActiveMQConnectionFactory activeMQConnectionFactory;
    public void setActiveMQConnectionFactory(
			ActiveMQConnectionFactory activeMQConnectionFactory) {
		this.activeMQConnectionFactory = activeMQConnectionFactory;
	}

    private DateTime lastEventDateTime;
	public DateTime getLastEventDateTime() {
		return lastEventDateTime;
	}
	public void setLastEventDateTime(DateTime lastEventDateTime) {
		this.lastEventDateTime = lastEventDateTime;
	}

	public void onMessage(Message message) {
		lastEventDateTime = DateTime.now(DateTimeZone.UTC);
		String threadId = UUID.randomUUID().toString();
        TextMessage textMessage = null;
        if(message instanceof TextMessage){
             textMessage = (TextMessage) message;
        }
        if(textMessage == null){
            logger.info("Received a non TextMessage, discarding it");
        }
        try {

        	logger.info("Received Message with messageId={} and tid={} content={}", message.getJMSMessageID(),threadId,textMessage.getText().replaceAll("\\b[\\w\\.-]+@","****@"));
        	//Only for debugging
        	logger.debug(textMessage.getText());
			managerMessageProcessor.processMessage(textMessage.getText(),threadId);

        } catch (JMSException e) {
            logger.error("JMS Exception hit", e.getMessage(), e);
        } catch (Exception e) {
        	logger.error("Exception hit", e.getMessage(), e);
        }
    }

	public boolean isComponentUp() {
		try {
			Field factoryStatsField = ActiveMQConnectionFactory.class.getDeclaredField("factoryStats");
			factoryStatsField.setAccessible(true);
			JMSStatsImpl factoryStats = (JMSStatsImpl) factoryStatsField.get(activeMQConnectionFactory);
			JMSConnectionStatsImpl[] connectionStats = factoryStats.getConnections();
			return connectionStats != null && connectionStats.length > 0;
		} catch (Throwable e) {
			if (lastEventDateTime != null) {
				logger.error("Last message received at " + lastEventDateTime.toString(ISODateTimeFormat.dateTime()));
			}
			logger.error("Unable to get Queue status", e);
			return false;
		}
	}
}
