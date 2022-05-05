package com.intuit.accountant.services.dcm.messaging;

import com.intuit.accountant.services.dcm.util.DCMUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.SessionCallback;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class ITODispatcher {

    private final Logger logger = LoggerFactory.getLogger(ITODispatcher.class);

    @Autowired
    @Qualifier("producerJmsTemplate")
    JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("itoDispatchDestination")
    Destination destination;

    @Value("${ius.service.appid:Intuit.accountant.dataconversions.dcmanager}")
    String appId;

    @Value("${CSB.BROKER.Intuit.Locale:en_US}")
    String locale;

    @Value("${CSB.BROKER.Intuit.Country:US}")
    String country;


    final String INTUIT_APPID = "intuit_appid";
    final String INTUIT_OFFERINGID = "intuit_offeringid";
    final String INTUIT_LOCALE = "intuit_locale";
    final String INTUIT_COUNTRY = "intuit_country";

    private DateTime lastDispatchedDateTime = null;
    
    public void send(final String message, String jobId) {
        lastDispatchedDateTime = DateTime.now(DateTimeZone.UTC);
        try {
            jmsTemplate.send(destination, new ManagerMessageCreator(jobId, message));
        } catch (JmsException e) {
            logger.error("Exception while dispatching message to ITO queue for jobId={} destination={}", jobId, destination.toString());
            throw e;
        }
    }

    public boolean isComponentUp() {
        try {
            Message message = (Message)jmsTemplate.execute(
                    new SessionCallback<Object>() {
                        public Object doInJms(Session session) throws JMSException {
                        	DateTime now = DateTime.now(DateTimeZone.UTC);
                            ManagerMessageCreator rmc = new ManagerMessageCreator("health-check-549cf10f-563e-4551-8622-b9167969c4e0", "health-check");
                            Message message = rmc.createMessage(session);
                            session.close();
                            return message;
                        }
                    }
            );
            return true;
        } catch (Throwable e) {
            if (lastDispatchedDateTime != null) {
                logger.error("Last message dispatched at " + lastDispatchedDateTime.toString(ISODateTimeFormat.dateTime()));
            }
            logger.error("Unable to get Queue status", e);
            return false;
        }
    }

    private class ManagerMessageCreator implements MessageCreator {
        private final String jobId;
        private final String message;

        public ManagerMessageCreator(String jobId, String message) {
            this.jobId = jobId;
            this.message = message;
        }

        public Message createMessage(Session session) throws JMSException {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText(message);
            setProperties(textMessage);
            return textMessage;
        }

        public void setProperties(TextMessage textMessage) throws JMSException {
            textMessage.setStringProperty(INTUIT_APPID, appId);
            textMessage.setStringProperty(INTUIT_OFFERINGID, appId);
            textMessage.setStringProperty(INTUIT_COUNTRY, country);
            textMessage.setStringProperty(INTUIT_LOCALE, locale);
            textMessage.setStringProperty(DCMUtil.TID, MDC.get(DCMUtil.TID));
        }
    }


}
