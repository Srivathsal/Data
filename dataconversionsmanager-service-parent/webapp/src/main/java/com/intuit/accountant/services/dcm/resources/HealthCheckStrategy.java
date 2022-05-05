package com.intuit.accountant.services.dcm.resources;

import com.intuit.accountant.services.common.auth.AccessHealthCheckValidator;
import com.intuit.accountant.services.common.health.HealthCheckResponse;
import com.intuit.accountant.services.common.health.IntuitServiceHealthCheckValidator;
import com.intuit.accountant.services.common.health.SupportHealthFullStrategy;
import com.intuit.accountant.services.dcm.messaging.ExecutorDispatcher;
import com.intuit.accountant.services.dcm.messaging.ManagerMessageListener;
import com.intuit.accountant.services.dcm.notifications.oinp.OINPNotificationProvider;
import com.intuit.accountant.services.dcm.services.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckStrategy implements SupportHealthFullStrategy {

    //@Autowired
    //private ManagerDLQMessageListener reminderDLQMessageListener;

    @Autowired
    private ManagerMessageListener managerMessageListener;

    @Autowired
    private ExecutorDispatcher executorDispatcher;

    @Autowired(required = false)
    AccessHealthCheckValidator accessHealthCheckValidator;

    @Autowired
    private OINPNotificationProvider oinpNotificationProvider;

    @Autowired
    private DocumentServiceImpl documentService;

    public HealthCheckResponse getHealthCheckStatus() {
        HealthCheckResponse healthCheckResponse = new HealthCheckResponse();
        healthCheckResponse.addStatus("Access", accessHealthCheckValidator.isComponentUp());
        healthCheckResponse.addStatus("IUS", accessHealthCheckValidator.isComponentUp());
        healthCheckResponse.addStatus("Messaging", managerMessageListener.isComponentUp());
        healthCheckResponse.addStatus("OINP", oinpNotificationProvider.isComponentUp());
        healthCheckResponse.addStatus("FDP", documentService.isComponentUp());

        return healthCheckResponse;
    }
}
