package com.intuit.accountant.services.dcm.messaging;

import com.intuit.accountant.services.dcm.model.JobPayload;
import com.intuit.accountant.services.dcm.model.enums.JobStatus;
import com.intuit.accountant.services.dcm.model.wms.Job;
import com.intuit.accountant.services.dcm.model.wms.JobMapper;
import com.intuit.accountant.services.dcm.notifications.*;
import com.intuit.accountant.services.dcm.util.AutomationCriteriaUtil;
import com.intuit.accountant.services.dcm.util.DCMUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sshashidhar on 09/11/18.
 */
@Component
public class ManagerMessageProcessor {

    private final Logger logger = LoggerFactory.getLogger(ManagerMessageProcessor.class);

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private ExecutorDispatcher executorDispatcher;

    @Autowired
    private ProtaxDispatcher protaxDispatcher;

    @Autowired
    private ITODispatcher itoDispatcher;

    @Autowired
    private ProtaxImportDispatcher protaxImportDispatcher;

    @Autowired
    private AutomationCriteriaUtil automationCriteriaUtil;

    @Autowired
    private NotificationProviderFactory notificationProviderFactory;

    public void processMessage(String msg, String threadId){

        Job job;
        JobPayload jobPayload = null;
        try {
            job = jobMapper.mapToJob(msg);
            jobPayload = jobMapper.mapToJobPayload(job.getContent());
            logger.info("Received Languge Type ={}",jobPayload.getLanguage()!=null ? jobPayload.getLanguage(): "English");
        } catch(Exception e) {
            logger.error("Error mapping queue message to Job/JobPayload: " + msg.replaceAll("\\b[\\w\\.-]+@","****@"));
            return;
        }

        logger.info("Processed JobPayload successfully.");
        boolean isCanadaJob=automationCriteriaUtil.isCanadaJob(jobPayload);
        boolean isProtaxJob =automationCriteriaUtil.isProtaxJob(jobPayload);
        logger.info("Received Job from ={}",isCanadaJob?"Canada Region":"Us Region");
        logger.info("job status ={} isProtaxJob ={}",job.getStatus(),isProtaxJob);
        //Send mail as per conditions
        if(job.getStatus().equalsIgnoreCase(JobStatus.COMPLETED.toString()) ||
                job.getStatus().equalsIgnoreCase(JobStatus.CANCELLED.toString()) ||
                job.getStatus().equalsIgnoreCase(JobStatus.COMPLETEDWITHDUPLICATES.toString()) ||
                (job.getStatus().equalsIgnoreCase(JobStatus.NEW.toString()) && isCanadaJob)||
                (job.getStatus().equalsIgnoreCase(JobStatus.REJECTED.toString()) && isProtaxJob)) {

            logger.info("Processing email request for jobId={} status={} realmId={} intuit_tid={}", job.getId().getValue(), job.getStatus(), jobPayload.getRealmId(),threadId);
            try{
                INotificationProvider notificationProvider = notificationProviderFactory.getNotificationProvider(NotificationProvider.OINP, NotificationType.EMAIL);;
                NotificationRequest notificationRequest = createNotificationRequest(job, jobPayload, jobPayload.getRealmId(), threadId);
                NotificationResponse response = notificationProvider.sendNotification(notificationRequest);

            } catch (Exception ex){
                logger.error("Error processing email request for jobId={} status={} realmId={}", job.getId().getValue(), job.getStatus(), jobPayload.getRealmId());
                throw new RuntimeException(ex);
            }
            //logger.info(response.toString());
        }

        //Dispatcher functionality
        //Dispatch to Executor Queue if jobStatus=New and job is automatable
        if(job.getStatus().equalsIgnoreCase(JobStatus.NEW.toString())) {
            if (automationCriteriaUtil.isJobAutomatable(job, jobPayload)) {

                logger.info("Message is being dispatched to Executor queue. jobId={} jobStatus={} destination={}", job.getId().getValue(), job.getStatus(),
                        isCanadaJob ? protaxDispatcher.protaxDestination.toString() : executorDispatcher.destination.toString());

                if (isCanadaJob) {
                    protaxDispatcher.send(msg, job.getId().getValue());
                } else {
                    executorDispatcher.send(msg, job.getId().getValue());
                }

                logger.info("Message dispatched to Executor queue. jobId={} jobStatus={} destination={}", job.getId().getValue(), job.getStatus(),
                        isCanadaJob ? protaxDispatcher.protaxDestination.toString() : executorDispatcher.destination.toString());
            } else {
                logger.info("Not automatable job. jobId={} jobStatus={}", job.getId().getValue(), job.getStatus());
            }
        }else{
            logger.info("Received Job, jobId={} jobStatus={}", job.getId().getValue(), job.getStatus());
        }

        //Dispatch to PTO Queue if jobStatus=DataAvailable
        if(job.getStatus().equalsIgnoreCase(JobStatus.DATAAVAILABLE.toString())){
            logger.info("Message is being dispatched to ITO queue. jobId={} jobStatus={} destination={}", job.getId().getValue(), job.getStatus(), itoDispatcher.destination.toString());
            itoDispatcher.send(msg, job.getId().getValue());
            logger.info("Message dispatched to ITO queue. jobId={} jobStatus={} destination={}", job.getId().getValue(), job.getStatus(), itoDispatcher.destination.toString());
        }
        if(job.getStatus().equalsIgnoreCase(JobStatus.DATAAVAILABLEFORPROTAX.toString())){
            logger.info("Message is being dispatched to PROTAX queue. jobId={} jobStatus={} destination={}", job.getId().getValue(), job.getStatus(), protaxImportDispatcher.protaxImportDestination.toString());
            protaxImportDispatcher.send(msg, job.getId().getValue());
            logger.info("Message dispatched to PROTAX queue. jobId={} jobStatus={} destination={}", job.getId().getValue(), job.getStatus(), protaxImportDispatcher.protaxImportDestination.toString());
        }

    }

    private NotificationRequest createNotificationRequest(Job job, JobPayload jobPayload, String realmId, String threadId) {
        boolean isProtaxJob =automationCriteriaUtil.isProtaxJob(jobPayload);
        NotificationRequest notificationRequest = new NotificationRequest();
        Map<String, String> templateVariablesMap = new HashMap<>();
        templateVariablesMap.put("jobId", StringUtils.substring(job.getId().getValue(),0,4));
        templateVariablesMap.put("fullName",StringUtils.defaultIfEmpty(jobPayload.getUserName(),"Data Conversions Customer"));
        templateVariablesMap.put("notifyEmailAddress",jobPayload.getNotifyEmailAddress());
        templateVariablesMap.put("realmId",jobPayload.getRealmId());
        templateVariablesMap.put("sourceProduct", jobPayload.getSourceProduct());
        templateVariablesMap.put("destinationProduct", isProtaxJob? DCMUtil.uppercaseAtIndex(jobPayload.getDestinationProduct(),3):
                jobPayload.getDestinationProduct());
        templateVariablesMap.put("jobStatus", job.getStatus());
        templateVariablesMap.put("intuit_tid", threadId);
        notificationRequest.setAuthId(job.getCreated().getBy().getValue());
        notificationRequest.setRealmId(realmId);
        notificationRequest.setTemplateVariables(templateVariablesMap);
        logger.info("OINP request Destination Product Name={}", templateVariablesMap.get("destinationProduct"));
        return notificationRequest;
    }

}
