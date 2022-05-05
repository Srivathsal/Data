package com.intuit.accountant.services.dcm.services;

import com.intuit.accountant.services.dcm.model.ConversionBlackout;
import com.intuit.accountant.services.dcm.resources.JmsContainerResource;
import com.intuit.accountant.services.dcm.util.VersionCompareUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.ConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by sshashidhar on 3/27/18.
 */
@Component
public class ConversionBlackoutService {

    private static final Logger logger = LoggerFactory.getLogger(ConversionBlackoutService.class);

    @Value("${isBlackoutEnabled}")
    private boolean isBlackoutEnabled;

    @Value("${isFullBlackoutEnabled}")
    private boolean isFullBlackoutEnabled;

    @Value("${productTaxYearAllowed}")
    private int productTaxYearAllowed;

    @Value("${isCANFilteringEnabled}")
    private boolean isCANFilteringEnabled;

    @Value("${canAllowed}")
    private String canAllowed;

    @Value("${dcAppVersionSupported}")
    private String dcAppVersionSupported;

    @Value(("${protax.productName}"))
    private String protaxProductName;

    @Value(("${blackoutEnabledProducts}"))
    private String blackoutEnabledProducts;

    @Value("#{${product.blackout.messages}}")
    private Map<String, String> productBlackoutMessages;

    @Value(("${profile.productName}"))
    private String profileProductName;

   public ConversionBlackout validateBlackout(String productName,String customerAccountNumber, String productTaxYear, String dcAppVersion) {

       logger.info("Validating blackout for ProductName={} CAN={} ProductTaxYear={} DCAppVersion={} isBlackoutEnableForProduct={}", productName,customerAccountNumber, productTaxYear, dcAppVersion,blackoutEnabledProducts);

        if(isFullBlackoutEnabled && !profileProductName.equalsIgnoreCase(productName)) {
            return new ConversionBlackout(true, "The Data Conversion service is currently unavailable due to scheduled maintenance and will reopen for submissions on November 12.", -1);
        }else if(StringUtils.isNotBlank(productName) && blackoutEnabledProducts.indexOf(productName.toLowerCase())!=-1){
            String blackoutMessage = productBlackoutMessages.get(productName.toLowerCase())!=null ?productBlackoutMessages.get(productName.toLowerCase()):
                    "The Data Conversion service is currently unavailable for "+productName.toLowerCase()+" due to scheduled maintenance and will reopen for submissions on July 20";
            return new ConversionBlackout(true,blackoutMessage,-1);
        }
        if(!(StringUtils.isNotBlank(productName) && (isProtaxProduct(productName.toLowerCase())
         || isProfileProduct(productName.toLowerCase()))) && isBlackoutEnabled){
            if(VersionCompareUtil.versionCompare(dcAppVersionSupported, dcAppVersion) == 1){
                //This version is not supported to do Data Conversions. Please update the application and try again.
                return new ConversionBlackout(true,"The Data Conversion service for this season is now closed. To submit tax files or download converted files, please use the Import > Client Data Conversion menu in the "+productTaxYearAllowed+" tax software.", productTaxYearAllowed);
            }

            if(StringUtils.isNotEmpty(productTaxYear)){
                if((productTaxYearAllowed > Integer.parseInt(productTaxYear))){
                    return new ConversionBlackout(true,"The Data Conversion service for this season is now closed. To submit tax files or download converted files, please use the Import > Client Data Conversion menu in the "+productTaxYearAllowed+" tax software.",productTaxYearAllowed);
                }
            }

            if(StringUtils.isNotEmpty(customerAccountNumber)){
                if(isCANFilteringEnabled){
                    if(!isCANInAllowedList(customerAccountNumber)){
                        return new ConversionBlackout(true,"Your CAN is not allowed to use Data Conversions",-1);
                    }
                }
            }
        }

        return new ConversionBlackout(false,null,-1);
    }

    public boolean isProtaxProduct(String productName){
        return  protaxProductName.equals(productName);
    }

    public boolean isProfileProduct(String productName){
        return  profileProductName.equals(productName);
    }

    private boolean isCANInAllowedList(String customerAccountNumber){
        List<String> canList = Arrays.asList(canAllowed.split(","));
        return canList.contains(customerAccountNumber);
    }

}
