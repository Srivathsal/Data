package com.intuit.accountant.services.dcm.util;

import com.intuit.accountant.services.dcm.model.AutomationCriterion;
import com.intuit.accountant.services.dcm.model.JobPayload;
import com.intuit.accountant.services.dcm.model.wms.Job;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AutomationCriteriaUtil {

    @Value("${DCExecutor.automatable.jobs}")
    private String automatableJobs;

    @Value("${DCExecutor.automatable.statusPollTimeInSeconds}")
    private int statusPollTimeInSeconds;

    @Value("${DCExecutor.automatable.conversionPollTimeInSeconds}")
    private int conversionPollTimeInSeconds;

    @Value("#{${DCExecutor.automatable.canada.jobs}}")
    private Map<String, String> canadaAutomatableJobs;

    @Value("${DCExecutor.automatable.canada.statusPollTimeInSeconds}")
    private int canadaStatusPollTimeInSeconds;

    @Value("${DCExecutor.automatable.canada.conversionPollTimeInSeconds}")
    private int canadaConversionPollTimeInSeconds;

    @Value("${protax.productName}")
    private String protaxProduct;


    private List<AutomationCriterion> automationCriteria;

    private Map<String,List<AutomationCriterion>> automationCriteriaForCanada;

    public List<AutomationCriterion> getAutomationCriteria() {

        if (automationCriteria != null) {
            return automationCriteria;
        }

        return createAutomationCriteria(automationCriteria, automatableJobs, statusPollTimeInSeconds, conversionPollTimeInSeconds);
    }

    public List<AutomationCriterion> getAutomationCriteriaForCanada(String productName) {
        if (automationCriteriaForCanada != null) {
            return automationCriteriaForCanada.get(productName);
        }

        return createAutomationCriteria(automationCriteriaForCanada, canadaAutomatableJobs, canadaStatusPollTimeInSeconds,
                canadaConversionPollTimeInSeconds).get(productName);
    }

    private Map<String,List<AutomationCriterion>> createAutomationCriteria(Map<String,List<AutomationCriterion>> automationCriteriaList,
                                                               Map<String,String> automatableJobsString, int statusPollTime, int conversionPollTime) {
        automationCriteriaList = new HashMap<String,List<AutomationCriterion>>();
        for (String productName: automatableJobsString.keySet()) {
            List<AutomationCriterion> productList = new ArrayList<AutomationCriterion>();
            List<String> wordList = Arrays.asList(automatableJobsString.get(productName).split(","));

            for (String criterion : wordList) {
                String[] criteria = criterion.split(":");
                productList.add(new AutomationCriterion(criteria[0], criteria[1], criteria[2], criteria[3],
                        statusPollTime, conversionPollTime));

            }
            automationCriteriaList.put(productName,productList);
        }

        return automationCriteriaList;
    }

    private List<AutomationCriterion> createAutomationCriteria(List<AutomationCriterion> automationCriteriaList,
                                                               String automatableJobsString, int statusPollTime, int conversionPollTime) {
        List<String> wordList = Arrays.asList(automatableJobsString.split(","));

        automationCriteriaList = new ArrayList<AutomationCriterion>();
        for (String criterion : wordList) {
            String[] criteria = criterion.split(":");
            automationCriteriaList.add(new AutomationCriterion(criteria[0], criteria[1], criteria[2], criteria[3],
                    statusPollTime, conversionPollTime));

        }
        return automationCriteriaList;
    }

    /**
     * Method used to check SourceProduct Of submitted job is from either protax or profile
     *
     * @param payload
     * @return
     */
    public boolean isCanadaJob(JobPayload payload) {
        if (canadaAutomatableJobs!=null || canadaAutomatableJobs.size()>0)
            return (payload.isCanadaJob(canadaAutomatableJobs));
        return false;
    }

    /**
     * Method used to check DestinationProduct Of submitted job is Protax or not
     *
     * @param payload
     * @return
     */
    public boolean isProtaxJob(JobPayload payload) {
        if (canadaAutomatableJobs!=null || canadaAutomatableJobs.size()>0)
            return (payload.isProtaxJob(payload));
        return false;
    }

    public boolean isJobAutomatable(Job job, JobPayload payload) {
        boolean flag = false;
        if (automatableJobs != null) {

            if (!payload.getIsAutomatedJob()) {
                flag = false;
            } else if (isCanadaJob(payload)) {
                List<AutomationCriterion> automationCriteria = getAutomationCriteriaForCanada(payload.getDestinationProduct().toLowerCase());
                if(automationCriteria!=null && automationCriteria.size()>0) {
                    for (AutomationCriterion automationCriterion : automationCriteria) {
                        if (didSourceProductCriteriaMatch(payload,
                                automationCriterion)
                                && (payload.getDestinationProduct() != null &&
                                automationCriterion.getIntuitProduct().toLowerCase().indexOf(payload.getDestinationProduct().toLowerCase()) != -1)) {
                            flag = true;
                            break;
                        }
                    }
                }
            } else{
                List<AutomationCriterion> automationCriteria = getAutomationCriteria();
                for (AutomationCriterion automationCriterion : automationCriteria) {
                    if (payload != null) {
                        if (didSourceProductCriteriaMatch(payload,
                                automationCriterion)
                                && didDestinationProductCriteriaMatch(payload,
                                automationCriterion)
                                && didFromYearCriteriaMatch(payload,
                                automationCriterion)
                                && didFromYearCriteriaMatch(payload,
                                automationCriterion)
                                && didToYearCriteriaMatch(payload,
                                automationCriterion)) {
                            flag = true;
                        }
                    }
                }
            }
        }

        if (flag) {
            if (!job.getStatus().equalsIgnoreCase(JobStatusConstants.NEW)) {
                flag = false;
            }
        }

        return flag;
    }

    private boolean didSourceProductCriteriaMatch(JobPayload payload,
                                                  AutomationCriterion automationCriterion) {
        if (payload.getSourceProduct() != null &&
                automationCriterion.getCompetitorProduct().toLowerCase().equals(payload.getSourceProduct().toLowerCase())) {
            return true;
        }

        return false;
    }

    private boolean didDestinationProductCriteriaMatch(JobPayload payload,
                                                       AutomationCriterion automationCriterion) {
        if (payload.getDestinationProduct() != null &&
                automationCriterion.getIntuitProduct().toLowerCase().equals(payload.getDestinationProduct().toLowerCase())) {
            return true;
        }
        return false;
    }

    private boolean didFromYearCriteriaMatch(JobPayload payload,
                                             AutomationCriterion automationCriterion) {
        if (payload.getTaxYear() != null && automationCriterion.getTaxYearFrom().equals(payload.getTaxYear())) {
            return true;
        }
        return false;
    }

    private boolean didToYearCriteriaMatch(JobPayload payload,
                                           AutomationCriterion automationCriterion) {
        if (didFromYearCriteriaMatch(payload, automationCriterion)) {
            return true;
        }
        return false;
    }

}
