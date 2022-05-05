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
public class ConversionsBlackoutServiceTest {

    @Autowired
    private ConversionBlackoutService conversionBlackoutService;

    @Test
    public void testBlackoutNotNull(){
        Assert.assertTrue(conversionBlackoutService.validateBlackout(null,"35434880","2020", "1.0") !=null);
    }

    @Test
    public void testBlackoutOldProductYear(){
        System.out.println(conversionBlackoutService.validateBlackout(null,"35434880","2020","2.0"));
        Assert.assertEquals("Version test mismatch", "ConversionBlackout{isBlackoutEnabled=true, messageToDisplay='The Data Conversion service for this season is now closed. To submit tax files or download converted files, please use the Import > Client Data Conversion menu in the 2020 tax software.', supportedYear=2020}",
                conversionBlackoutService.validateBlackout(null,"35434880","2020","2.0").toString());
    }

    @Test
    public void testOldVersion(){
        System.out.println(conversionBlackoutService.validateBlackout(null,"35434880","2020","0.9"));
        Assert.assertEquals("Version test mismatch", "ConversionBlackout{isBlackoutEnabled=true, messageToDisplay='The Data Conversion service for this season is now closed. To submit tax files or download converted files, please use the Import > Client Data Conversion menu in the 2020 tax software.', supportedYear=2020}",
                conversionBlackoutService.validateBlackout(null,"35434880","2020","0.9").toString());
    }

    @Test
    public void testSupportedVersion(){
        System.out.println(conversionBlackoutService.validateBlackout(null,"35434880","2020","3.0"));
        Assert.assertEquals("Version test mismatch", "ConversionBlackout{isBlackoutEnabled=false, messageToDisplay='null', supportedYear=-1}",
                conversionBlackoutService.validateBlackout(null,"35434880","2020","3.0").toString());
    }

    @Test
    public void testSupportedNewVersion(){
        System.out.println(conversionBlackoutService.validateBlackout(null,"35434880","2020","3.1"));
        Assert.assertEquals("Version test mismatch", "ConversionBlackout{isBlackoutEnabled=false, messageToDisplay='null', supportedYear=-1}",
                conversionBlackoutService.validateBlackout(null,"35434880","2020","3.1").toString());
    }

    @Test
    public void testProtaxBlocked(){
        System.out.println(conversionBlackoutService.validateBlackout("Protax",null,null,null));
        Assert.assertEquals("Version test mismatch", "ConversionBlackout{isBlackoutEnabled=true, messageToDisplay='The Data Conversion service is currently unavailable for protax due to scheduled maintenance and will reopen for submissions on July 20', supportedYear=-1}",
                conversionBlackoutService.validateBlackout("protax",null,null,null).toString());
    }

    @Test
    public void testProfileUnBlocked(){
        System.out.println(conversionBlackoutService.validateBlackout("Profile",null,null,"3.0"));
        Assert.assertEquals("Version test mismatch", "ConversionBlackout{isBlackoutEnabled=false, messageToDisplay='null', supportedYear=-1}",
                conversionBlackoutService.validateBlackout("Profile",null,null,"2.0").toString());
    }

    @Test
    public void testLacerteNotBlockedVersion(){
        System.out.println(conversionBlackoutService.validateBlackout("Lacerte","35434880","2020","3.0"));
        Assert.assertEquals("Version test mismatch", "ConversionBlackout{isBlackoutEnabled=false, messageToDisplay='null', supportedYear=-1}",
                conversionBlackoutService.validateBlackout(null,"35434880","2020","3.0").toString());
    }

}
