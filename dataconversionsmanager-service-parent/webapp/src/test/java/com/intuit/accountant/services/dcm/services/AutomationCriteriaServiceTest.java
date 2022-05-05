package com.intuit.accountant.services.dcm.services;

import com.intuit.accountant.services.dcm.model.AutomationCriterion;
import com.intuit.accountant.services.dcm.model.JobPayload;
import com.intuit.accountant.services.dcm.model.wms.Job;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by sshashidhar on 19/09/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class AutomationCriteriaServiceTest {

    @Autowired
    private AutomationCriteriaService automationCriteriaService;

    @Value(("${protax.productName}"))
    private String protaxProductName;

    @Test
    public void testGetAutomationCriteriaList(){
        List<AutomationCriterion> automationCriterionList = automationCriteriaService.getAutomatableSoftwares();
        Assert.assertNotNull(automationCriterionList);
        Assert.assertTrue(automationCriterionList.size()==2);
        Assert.assertEquals("ultrataxcs", automationCriterionList.get(0).getCompetitorProduct());
        Assert.assertEquals("lacerte", automationCriterionList.get(0).getIntuitProduct());
        Assert.assertEquals("2020", automationCriterionList.get(0).getTaxYearFrom());
        Assert.assertEquals("2020", automationCriterionList.get(0).getTaxYearTo());
        Assert.assertEquals(30, automationCriterionList.get(0).getConversionPollIntervalInSeconds());
        Assert.assertEquals(60, automationCriterionList.get(0).getStatusPollIntervalInSeconds());
    }

    @Test
    public void testGetAutomationCriteriaListForProductProtax(){
        List<AutomationCriterion> automationCriterionList = automationCriteriaService.getAutomatableSoftwares(protaxProductName);
        Assert.assertNotNull(automationCriterionList);
        Assert.assertTrue(automationCriterionList.size()==2);
        Assert.assertEquals("Taxcycle_T1", automationCriterionList.get(0).getCompetitorProduct());
        Assert.assertEquals("Protax_T1", automationCriterionList.get(0).getIntuitProduct());
        Assert.assertEquals("2019", automationCriterionList.get(0).getTaxYearFrom());
        Assert.assertEquals("2019", automationCriterionList.get(0).getTaxYearTo());
        Assert.assertEquals(30, automationCriterionList.get(0).getConversionPollIntervalInSeconds());
        Assert.assertEquals(60, automationCriterionList.get(0).getStatusPollIntervalInSeconds());
    }

    @Test
    public void testIsJobAutomatableHappyPath(){
        Job job = new Job();
        job.setStatus("New");
        JobPayload jobPayload = new JobPayload();
        jobPayload.setIsAutomatedJob(true);
        jobPayload.setSourceProduct("ultrataxcs");
        jobPayload.setDestinationProduct("lacerte");
        jobPayload.setTaxYear("2020");
        jobPayload.setToTaxYear("2020");
        Assert.assertTrue("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

    @Test
    public void testIsJobAutomatableHappyPathCanada(){
        Job job = new Job();
        job.setStatus("New");
        JobPayload jobPayload = new JobPayload();
        jobPayload.setIsAutomatedJob(true);
        jobPayload.setSourceProduct("TaxCycle_T2");
        jobPayload.setDestinationProduct("Profile");
        jobPayload.setTaxYear("2018");
        jobPayload.setToTaxYear("2018");
        Assert.assertTrue("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

    @Test
    public void testIsJobAutomatableHappyPathProtax(){
        Job job = new Job();
        job.setStatus("New");
        JobPayload jobPayload = new JobPayload();
        jobPayload.setIsAutomatedJob(true);
        jobPayload.setSourceProduct("TaxCycle_T2");
        jobPayload.setDestinationProduct("protax");
        jobPayload.setTaxYear("2018");
        jobPayload.setToTaxYear("2018");
        Assert.assertTrue("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

    @Test
    public void testIsJobAutomatableShouldBeFalseWhenSourceProductIsWrong(){
        Job job = new Job();
        job.setStatus("New");
        JobPayload jobPayload = new JobPayload();
        jobPayload.setIsAutomatedJob(true);
        jobPayload.setSourceProduct("proseries");
        jobPayload.setDestinationProduct("lacerte");
        jobPayload.setTaxYear("2020");
        Assert.assertFalse("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

    @Test
    public void testIsJobAutomatableShouldBeFalseWhenDestinationProductIsWrong(){
        Job job = new Job();
        job.setStatus("New");
        JobPayload jobPayload = new JobPayload();
        jobPayload.setIsAutomatedJob(true);
        jobPayload.setSourceProduct("ultrataxcs");
        jobPayload.setDestinationProduct("proseries");
        jobPayload.setTaxYear("2020");
        Assert.assertFalse("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

    @Test
    public void testIsJobAutomatableShouldBeFalseWhenAutomatedFlagIsFalse(){
        Job job = new Job();
        job.setStatus("New");
        JobPayload jobPayload = new JobPayload();
        jobPayload.setIsAutomatedJob(false);
        jobPayload.setSourceProduct("ultrataxcs");
        jobPayload.setDestinationProduct("lacerte");
        jobPayload.setTaxYear("2020");
        Assert.assertFalse("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

    @Test
    public void testIsJobAutomatableShouldBeFalseWhenTaxYearIsWrong(){
        Job job = new Job();
        job.setStatus("New");
        JobPayload jobPayload = new JobPayload();
        jobPayload.setIsAutomatedJob(true);
        jobPayload.setSourceProduct("ultrataxcs");
        jobPayload.setDestinationProduct("lacerte");
        jobPayload.setTaxYear("2015");
        Assert.assertFalse("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

    @Test
    public void testIsJobAutomatableShouldBeFalseWhenStatusIsWrong(){
        Job job = new Job();
        job.setStatus("InProgress");
        JobPayload jobPayload = new JobPayload();
        jobPayload.setIsAutomatedJob(true);
        jobPayload.setSourceProduct("ultrataxcs");
        jobPayload.setDestinationProduct("lacerte");
        jobPayload.setTaxYear("2016");
        Assert.assertFalse("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

    @Test(expected = NullPointerException.class)
    public void testIsJobAutomatableNullJob(){
        Job job = null;
        JobPayload jobPayload = new JobPayload();
        jobPayload.setIsAutomatedJob(true);
        jobPayload.setSourceProduct("ultrataxcs");
        jobPayload.setDestinationProduct("lacerte");
        jobPayload.setTaxYear("2020");
        Assert.assertTrue("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

    @Test(expected = NullPointerException.class)
    public void testIsJobAutomatableNullJobPayload(){
        Job job = new Job();
        job.setStatus("New");
        JobPayload jobPayload = null;
        Assert.assertTrue("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

    @Test(expected = NullPointerException.class)
    public void testIsJobAutomatableNullJobAndJobPayload(){
        Job job = null;
        JobPayload jobPayload = null;
        Assert.assertTrue("IsJobAutomatable test failed: ", automationCriteriaService.isJobAutomatable(job,jobPayload));
    }

}
