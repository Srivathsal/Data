package com.intuit.accountant.services.dcm.services;

import com.intuit.accountant.services.dcm.model.AutomationCriterion;
import com.intuit.accountant.services.dcm.model.JobPayload;
import com.intuit.accountant.services.dcm.model.wms.Job;
import com.intuit.accountant.services.dcm.util.AutomationCriteriaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sshashidhar on 07/09/18.
 */
@Component
public class AutomationCriteriaService {

    @Autowired(required=true)
    private AutomationCriteriaUtil automationCriteriaUtil;

    @Value("${DCExecutor.automatable.jobs}")
    private String automatableJobs;

    @Value(("${protax.productName}"))
    private String protaxProductName;

    @Value(("${profile.productName}"))
    private String profileProductName;


    public List<AutomationCriterion> getAutomatableSoftwares() {

        return automationCriteriaUtil.getAutomationCriteria();

    }

    public List<AutomationCriterion> getAutomatableSoftwares(String productName) {

        if((protaxProductName.equalsIgnoreCase(productName) || profileProductName.equalsIgnoreCase(productName))) {
            return automationCriteriaUtil.getAutomationCriteriaForCanada(productName);
        }

        return  automationCriteriaUtil.getAutomationCriteria();
    }

    public boolean isJobAutomatable(Job job, JobPayload payload){
        return automationCriteriaUtil.isJobAutomatable(job,payload);
    }



}
