package com.intuit.accountant.services.dcm.resources;

import com.intuit.accountant.services.common.annotation.UserRealmTicketFilter;
import com.intuit.accountant.services.common.iam.IamAuthContext;
import com.intuit.accountant.services.dcm.model.JmsStats;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.intuit.accountant.services.common.iam.IamAuthContextFactory.getIamAuth;

/**
 * Created by sshashidhar on 11/02/19.
 */
@Component
@Path("/admin/internal/jms")
@Api(value = "/jms", description = "Service for starting and stopping jms containers")
@UserRealmTicketFilter
public class JmsContainerResource {

    private static final Logger logger = LoggerFactory.getLogger(JmsContainerResource.class);

    private String userName = "Intuit.accountant.services.workflowlite";

    private String password = "preprdW5mRAxV6cKYtvgImWC594oO7XNdaJd0GBP";

    @Value("${CSB.SSL.Broker}")
    private String brokerUrl;

    private String testMessage = "This is a test message";

    @Value("${Intuit.global.manager.notification.Queue}")
    private String managerQueue;

    @Resource(name="managerListenerContainer")
    private DefaultMessageListenerContainer defaultMessageListenerContainer;

    @Value("${Intuit.global.executor.notification.Queue}")
    private String executorQueue;

    @Value("${Intuit.global.import.notification.Queue}")
    private String importQueue;

    @GET
    @Path("/status")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
            @ApiResponse(code = 200, message = "Successfully processed the request.")
    })
    public @ResponseBody JmsStats returnJmsStatus(@Context HttpHeaders headers){

        IamAuthContext authContext = getIamAuth(headers);

        JmsStats jmsStats = new JmsStats();
        jmsStats.setJmsContainerName(defaultMessageListenerContainer.getDestinationName());
        jmsStats.setJmsContainerActive(defaultMessageListenerContainer.isActive());
        jmsStats.setJmsContainerRunning(defaultMessageListenerContainer.isRunning());
        jmsStats.setJmsContainerAutoStartup(defaultMessageListenerContainer.isAutoStartup());
        jmsStats.setJmsContainerActiveConsumerCount(defaultMessageListenerContainer.getActiveConsumerCount());
        logger.info("JmsStats={}: " ,jmsStats.toString());
        return jmsStats;
    }

    @GET
    @Path("/log-queues")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
            @ApiResponse(code = 200, message = "Successfully processed the request.")
    })
    public @ResponseBody void logAllQueues(){
        logger.info("DCM Queues managerQueue={} executorQueue={} importQueue={}" ,managerQueue, executorQueue, importQueue);
    }

    @GET
    @Path("/start")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
            @ApiResponse(code = 200, message = "Successfully processed the request.")
    })
    public @ResponseBody JmsStats startJms(){
        JmsStats jmsStats = new JmsStats();
        if(!defaultMessageListenerContainer.isActive() &&!defaultMessageListenerContainer.isRunning()){
            logger.info("Starting jmsContainer={}", defaultMessageListenerContainer.getDestinationName());
            defaultMessageListenerContainer.start();
            defaultMessageListenerContainer.initialize();
        }
        jmsStats.setJmsContainerName(defaultMessageListenerContainer.getDestinationName());
        jmsStats.setJmsContainerActive(defaultMessageListenerContainer.isActive());
        jmsStats.setJmsContainerRunning(defaultMessageListenerContainer.isRunning());
        jmsStats.setJmsContainerAutoStartup(defaultMessageListenerContainer.isAutoStartup());
        jmsStats.setJmsContainerActiveConsumerCount(defaultMessageListenerContainer.getActiveConsumerCount());
        logger.info("JmsStats={}: " ,jmsStats.toString());
        return jmsStats;
    }

    @GET
    @Path("/stop")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
            @ApiResponse(code = 200, message = "Successfully processed the request.")
    })
    public @ResponseBody JmsStats stopJms(){
        JmsStats jmsStats = new JmsStats();
        if(defaultMessageListenerContainer.isActive() && defaultMessageListenerContainer.isRunning()){
            logger.info("Stopping jmsContainer={}", defaultMessageListenerContainer.getDestinationName());
            defaultMessageListenerContainer.stop();
            defaultMessageListenerContainer.shutdown();
        }
        jmsStats.setJmsContainerName(defaultMessageListenerContainer.getDestinationName());
        jmsStats.setJmsContainerActive(defaultMessageListenerContainer.isActive());
        jmsStats.setJmsContainerRunning(defaultMessageListenerContainer.isRunning());
        jmsStats.setJmsContainerAutoStartup(defaultMessageListenerContainer.isAutoStartup());
        jmsStats.setJmsContainerActiveConsumerCount(defaultMessageListenerContainer.getActiveConsumerCount());
        logger.info("JmsStats={}: " ,jmsStats.toString());
        return jmsStats;
    }

    @GET
    @Path("/message")
    public Response submitTestMessage(){
        logger.info("Submitting test message to DCM Queue=" + managerQueue);
        try {

            String messageId = publishTestMessageToQueue(userName, password, brokerUrl, managerQueue);
            if(messageId!=null)
                return Response.ok().entity("Test Message Ok. MessageId=" + messageId).build();
            else
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            logger.error("Error sending test message");
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String publishTestMessageToQueue(String userName, String password, String brokerUrl, String queueName) throws JMSException {
        Connection connection = null;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    userName,password,
                    brokerUrl);
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(testMessage);
            producer.send(textMessage);
            String messageId = textMessage.getJMSMessageID();
            session.close();
            return messageId;
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

}
