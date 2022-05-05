package com.intuit.accountant.services.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * Created by prajendran1 on 11/8/17.
 */
public class InetUtil {

    private static String localHostAddress;

    public static String getLocalHostAddress() {
        try {
            if (StringUtils.isEmpty(localHostAddress)) {
                localHostAddress = InetAddress.getLocalHost().getHostAddress();
            }
        } catch (Exception ignore) {
            localHostAddress = "127.0.0.1";
        }
        return localHostAddress;
    }
}
