package com.intuit.accountant.services.dcm.model;

/**
 * Created by sshashidhar on 16/05/19.
 */
public class FDPBaseRequest {
    private CommonAttributes commonAttributes;

    public CommonAttributes getCommonAttributes() {
        return commonAttributes;
    }

    public void setCommonAttributes(CommonAttributes commonAttributes) {
        this.commonAttributes = commonAttributes;
    }

    @Override
    public String toString() {
        return "FDPBaseRequest{" +
                "commonAttributes=" + commonAttributes +
                '}';
    }
}
