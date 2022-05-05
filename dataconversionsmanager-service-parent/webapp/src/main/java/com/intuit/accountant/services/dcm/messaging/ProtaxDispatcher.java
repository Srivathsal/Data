package com.intuit.accountant.services.dcm.messaging;

import com.intuit.accountant.services.dcm.util.DCMUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class ProtaxDispatcher {

    private final Logger logger = LoggerFactory.getLogger(ProtaxDispatcher.class);

    @Autowired
    @Qualifier("producerJmsTemplateProtax")
    JmsTemplate producerJmsTemplateProtax;

    @Autowired
    @Qualifier("protaxDispatchDestination")
    Destination protaxDestination;

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
            producerJmsTemplateProtax.send(protaxDestination, new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage = session.createTextMessage(message);
                    textMessage.setStringProperty(INTUIT_APPID, appId);
                    textMessage.setStringProperty(INTUIT_OFFERINGID, appId);
                    textMessage.setStringProperty(INTUIT_COUNTRY, country);
                    textMessage.setStringProperty(INTUIT_LOCALE, locale);
                    textMessage.setStringProperty(DCMUtil.TID, MDC.get(DCMUtil.TID));

                    logger.info("Sending message to Protax Queue: " + textMessage);

                    return textMessage;
                }
            });
        } catch (JmsException e) {
            logger.error("Exception while dispatching message to Canada Protax queue for jobId={} destination={}", jobId, protaxDestination.toString());
            throw e;
        }
    }
}
