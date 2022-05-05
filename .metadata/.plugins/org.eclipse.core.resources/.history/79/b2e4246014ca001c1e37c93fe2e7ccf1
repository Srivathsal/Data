package com.intuit.accountant.services.dcm.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by sshashidhar on 19/09/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class DriverEDCServiceTest {

    @Autowired
    private DriverEDCService driverEDCService;

    @Test
    public void testEDCNotNull(){
        Assert.assertTrue(driverEDCService.getEdckey() !=null);
    }

    @Test
    public void testDriverNotNull(){
        String driverXML = driverEDCService.getDriverXml("UT","1234","1000", null
        ,"LAC","test@gmail.com","FN","LN", "55555555","2020","03/08/2020");
        Assert.assertTrue(driverXML !=null);
    }
}
