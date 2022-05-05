package com.intuit.accountant.services.dcm.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * TODO There is scope for improvement. Caller should not typecast
 */
public class EnumCodeUtil {

    public static <T extends EnumCode> EnumCode searchEnum(Class<? extends EnumCode> enumeration,
                                                           String search) {
        if (StringUtils.isBlank(search)) {
            return null;
        }
        EnumCode[] enumConstants = enumeration.getEnumConstants();
        for (EnumCode each : enumConstants) {
            Enum<?> enumValue = (Enum<?>) each;
            if (enumValue.toString().equalsIgnoreCase(search) || each.getCode().equalsIgnoreCase(search)) {
                return each;
            }
        }
        return null;
    }
}
