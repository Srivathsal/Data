package com.intuit.accountant.services.common.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProxyUtil {

    @Value("${proxy_host:}")
    private  String proxyHost;

    @Value("${proxy_port:80}")
    private String proxyPort;

    public String getProxyUrl(){
        if (StringUtils.isNotBlank(proxyHost)) {
            String httpScheme = proxyPort.equals("443") ? "https" : "http";
            return String.format("%s://%s:%s", httpScheme, proxyHost, proxyPort);
        } else {
            return null;
        }
    }

    public String getProxyHost(){
        return proxyHost;
    }

    public String getProxyPort(){
        return proxyPort;
    }

}
