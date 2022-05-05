package com.intuit.accountant.services.dcm.services;

import com.intuit.accountant.services.dcm.model.DropDown;
import com.intuit.accountant.services.dcm.model.ExternalSystemConfigModel;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sshashidhar on 07/09/18.
 */
@Component
public class ApplicationConfigurationService {

    @Value("${dropDownList}")
    private String dropDownList;

    @Value("${authorizationEnvironment}")
    private String authorizationEnvironment;

    @Value("${appToken}")
    private String appToken;

    @Value("${offeringId}")
    private String offeringId;

    @Value("${originatingIp}")
    private String originatingIp;

    @Value("${appId}")
    private String appId;

    @Value("${appSecret}")
    private String appSecret;

    @Value("${wmsOfferingId}")
    private String wmsOfferingId;

    @Value("${lacerteHelp}")
    private String lacerteHelp;

    @Value("${proseriesHelp}")
    private String proseriesHelp;

    @Value("${taxOnlineHelp}")
    private String taxOnlineHelp;

    @Value("${oiiBaseUrl}")
    private String oiiBaseUrl;

    @Value("${oiiLoginUrl}")
    private String oiiLoginUrl;

    @Value("${eDCSBaseUrl}")
    private String eDCSBaseUrl;

    @Value("${acsBaseUrl}")
    private String acsBaseUrl;

    @Value("${acsUploadUrl}")
    private String acsUploadUrl;

    @Value("${acsDownloadUrl}")
    private String acsDownloadUrl;

    @Value("${acsAppId}")
    private String acsAppId;

    @Value("${acsKey}")
    private String acsKey;

    @Value("${wmsBaseUrl}")
    private String wmsBaseUrl;

    @Value("${proFile}")
    private String profileUrl;

    @Value("${proFileFr}")
    private String profileFrUrl;

    @Value(("${protax.productName}"))
    private String protaxProductName;

    @Value("${profile.productName}")
    private String profileProductName;

    private List<DropDown> dropDowns;

    private static Logger logger = Logger.getLogger(ApplicationConfigurationService.class);


    public String getDesktopConfiguration(){
        String configuration = null;
        try {
            configuration  = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+"\n";
            configuration += "<DesktopConfiguration>";
            configuration += "\n"+ IOUtils.toString(this.getClass().getClassLoader()
                    .getResourceAsStream("competitor-software-configuration.xml"),"UTF-8");
            configuration += "\n"+IOUtils.toString(this.getClass().getClassLoader()
                    .getResourceAsStream("intuit-software-configuration.xml"),"UTF-8");
            configuration += "\n"+"</DesktopConfiguration>";

            return configuration;
        }catch (IOException e){
            logger.error("Failed to fetch configuration files from resources: " + e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    public String getDesktopConfiguration(String productName){

        StringBuffer configuration = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+"\n");
        try {
            configuration.append("<DesktopConfiguration>");
            if(StringUtils.isNotBlank(productName) && (protaxProductName.equalsIgnoreCase(productName) || profileProductName.equalsIgnoreCase(productName))) {
                configuration.append("\n" + IOUtils.toString(this.getClass().getClassLoader()
                        .getResourceAsStream("competitor-software-configuration-protax.xml"), "UTF-8"));
            }else{
                configuration.append( "\n"+ IOUtils.toString(this.getClass().getClassLoader()
                        .getResourceAsStream("competitor-software-configuration.xml"),"UTF-8"));
            }
            configuration.append("\n"+IOUtils.toString(this.getClass().getClassLoader()
                    .getResourceAsStream("intuit-software-configuration.xml"),"UTF-8"));
            configuration.append( "\n"+"</DesktopConfiguration>");

            return configuration.toString();
        }catch (IOException e){
            logger.error("Failed to fetch configuration files from resources: " + e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    public ExternalSystemConfigModel getExternalSystemConfig(){

        ExternalSystemConfigModel externalSystemConfigModel = new ExternalSystemConfigModel();
        externalSystemConfigModel.setAuthorizationEnvironment(authorizationEnvironment);
        externalSystemConfigModel.setAppToken(appToken);
        externalSystemConfigModel.setOfferingId(offeringId);
        externalSystemConfigModel.setOriginatingIp(originatingIp);
        externalSystemConfigModel.setAppId(appId);
        externalSystemConfigModel.setAppSecret(appSecret);
        externalSystemConfigModel.setWmsOfferingId(wmsOfferingId);
        externalSystemConfigModel.setLacerteSupportSettings(lacerteHelp);
        externalSystemConfigModel.setProseriesSupportSettings(proseriesHelp);
        externalSystemConfigModel.setTaxOnlineSupportSettings(taxOnlineHelp);
        externalSystemConfigModel.setOiiBaseUrl(oiiBaseUrl);
        externalSystemConfigModel.setOiiLoginUrl(oiiLoginUrl);
        externalSystemConfigModel.seteDCSBaseUrl(eDCSBaseUrl);
        externalSystemConfigModel.setAcsBaseUrl(acsBaseUrl);
        externalSystemConfigModel.setAcsUploadUrl(acsUploadUrl);
        externalSystemConfigModel.setAcsDownloadUrl(acsDownloadUrl);
        externalSystemConfigModel.setAcsAppId(acsAppId);
        externalSystemConfigModel.setAcsKey(acsKey);
        externalSystemConfigModel.setWmsBaseUrl(wmsBaseUrl);
        externalSystemConfigModel.setProfileUrl(profileUrl);
        externalSystemConfigModel.setProfileFrUrl(profileFrUrl);


        return externalSystemConfigModel;
    }

    public List<DropDown> getDesktopDropDowns(){

        if(dropDowns!=null){
            return dropDowns;
        }

        try {
            List<String> wordList = Arrays.asList(dropDownList.split(","));

            dropDowns = new ArrayList<>();
            for (String item : wordList) {
                String[] criteria  = item.split(":");
                dropDowns.add(new DropDown(criteria[0],criteria[1],criteria[2],criteria[3]));

            }
            return dropDowns;
        }catch (Exception e){
            logger.error("Failed to fetch drop down configuration from resources: " + e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    public String getDesktopConfigurationAsJson(String productName, String sourceName){
        try {

            return IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("competitor-intuit-softwares.json"), Charsets.UTF_8);
        }
        catch (IOException e){
            logger.error("Failed to fetch configuration files from resources: " + e.getStackTrace());
            throw new RuntimeException(e);
        }

    }




}
