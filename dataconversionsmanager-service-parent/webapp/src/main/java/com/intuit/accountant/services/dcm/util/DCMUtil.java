package com.intuit.accountant.services.dcm.util;

import com.intuit.accountant.services.helpers.MdcRequestThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.UUID;

@Component
public class DCMUtil {
    public static final String TID = "tid";
    private final Logger logger = LoggerFactory.getLogger(DCMUtil.class);

    @PostConstruct
    public void init() {

        try {
            ipAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("Unable to get the ipAddress for the machine, populating it to 127.0.0.1");
            ipAddress = "127.0.0.1";
        }
    }

    String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }


    public static String ensureTID() {
        String threadId = MDC.get(TID);
        if (StringUtils.isNotEmpty(threadId)) {
            return threadId;
        }

        if (StringUtils.isEmpty(threadId)) {
            threadId = UUID.randomUUID().toString();
        }
        MdcRequestThreadLocal.setIntuitTid(threadId);
        MDC.put(DCMUtil.TID, threadId);
        return threadId;
    }

    public static String uppercaseAtIndex(String s,int index)
    {
        if (StringUtils.isBlank(s))
        {
            return "";
        }
        char[] a = s.toCharArray();
        a[index] = Character.toUpperCase(a[index]);
        return new String(a);
    }

    public static Map<String,String> getEnvVariable() {
        return System.getenv();
    }
}
