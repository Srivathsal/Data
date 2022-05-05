package com.intuit.accountant.services.dcm.notifications.oinp;

import com.intuit.accountant.services.common.httpclient.ApiResponse;
import com.intuit.accountant.services.common.httpclient.HttpUtilities;
import com.intuit.accountant.services.common.offlineticket.SystemOfflineTicket;
import com.intuit.accountant.services.common.util.ProxyUtil;
import com.intuit.accountant.services.dcm.model.OINPEmailRequest;
import com.intuit.accountant.services.dcm.model.OINPEventData;
import com.intuit.accountant.services.dcm.model.OINPEventMetaData;
import com.intuit.accountant.services.dcm.notifications.INotificationProvider;
import com.intuit.accountant.services.dcm.notifications.NotificationRequest;
import com.intuit.accountant.services.dcm.notifications.NotificationResponse;
import com.intuit.accountant.services.dcm.notifications.exceptions.NotificationProviderException;
import com.intuit.accountant.services.dcm.resources.helpers.JSONHelper;
import com.intuit.accountant.services.dcm.util.DCMUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Collections.singletonMap;

/**
 * Created by sshashidhar on 17/12/18.
 */
@Component
public class OINPNotificationProvider implements INotificationProvider {

    private Logger logger = LoggerFactory.getLogger(OINPNotificationProvider.class);

    private static String AUTH_HEADER_HEALTH = "Intuit_IAM_Authentication intuit_appid=%s,intuit_app_secret=%s,intuit_version=1.0";

    @Autowired
    public JSONHelper jsonHelper;

    @Autowired
    public SystemOfflineTicket systemOfflineTicket;

    @Autowired
    public HttpUtilities httpUtilities;

    public void setHttpUtilities(HttpUtilities httpUtilities) {
        this.httpUtilities = httpUtilities;
    }

    @Autowired
    ProxyUtil proxyUtil;

    @Value("${oinp.service.url}")
    public String baseURL;

    @Value("${oinp.notification.enabled}")
    public boolean notificationEnabled;

    @Value("${ius.service.appid}")
    private String appId;

    @Value("${ius.service.secret}")
    private String appSecret;

    @Value("${oinp.service.notification.retry.count:3}")
    public int RETRY_COUNT;

    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        logger.info("Processing notification request");
        if (notificationRequest == null)
            throw new NotificationProviderException("Unable to send OINP notification. Notification request is null.");

        if(!notificationEnabled)
            throw new NotificationProviderException("Notifications are disabled in the properties");

        OINPEmailRequest oinpEmailRequest = new OINPEmailRequest();
        NotificationResponse notificationResponse = new NotificationResponse();
        String tid = null;

        if(notificationRequest.getTemplateVariables().get("intuit_tid")!=null)
                tid = notificationRequest.getTemplateVariables().get("intuit_tid");
        else
                tid = UUID.randomUUID().toString();

        Random rand = new Random();
        String randomSourceObjectId = String.valueOf(rand.nextInt(9000000) + 1000000);

        StringBuilder stringBuilder = new StringBuilder();
        oinpEmailRequest.setName("ConversionStatus");
        oinpEmailRequest.setSourceServiceName("DCM");
        oinpEmailRequest.setSourceObjectId(randomSourceObjectId);
        stringBuilder.append("Sending email with sourceObjectId=" + randomSourceObjectId);

        oinpEmailRequest.setSourceObjectType("ConversionStatus");
        OINPEventData eventData = new OINPEventData();
        eventData.setJobId(notificationRequest.getTemplateVariables().get("jobId"));
        eventData.setFullName(notificationRequest.getTemplateVariables().get("fullName"));
        eventData.setNotifyEmailAddress(notificationRequest.getTemplateVariables().get("notifyEmailAddress"));
        eventData.setSourceProduct(notificationRequest.getTemplateVariables().get("sourceProduct"));
        eventData.setDestinationProduct(notificationRequest.getTemplateVariables().get("destinationProduct"));
        eventData.setJobStatus(notificationRequest.getTemplateVariables().get("jobStatus"));
        if(notificationRequest.getTemplateVariables().get("customMessage")!=null)
            eventData.setCustomMessage(notificationRequest.getTemplateVariables().get("customMessage"));
        oinpEmailRequest.setOinpEventData(eventData);

        OINPEventMetaData oinpEventMetaData = new OINPEventMetaData();
        oinpEventMetaData.setAuthId(notificationRequest.getAuthId());
        oinpEventMetaData.setCreatedDate(new Date(System.currentTimeMillis()).toString());
        oinpEventMetaData.setTid(tid);
        oinpEmailRequest.setOinpEventMetaData(oinpEventMetaData);
        stringBuilder.append(" and tid=" + oinpEventMetaData.getTid());

        logger.info(stringBuilder.toString());

        try{
            ApiResponse response = null;
            response = postEmailReminder(oinpEmailRequest, notificationRequest.getRealmId());

            if (response.getStatusCode() == HttpStatus.ACCEPTED.value()) {
                logger.info("Email server returned status={}, tid={} for jobId={} realmId={}", response.getStatusCode(), response.getHeaderString("intuit_tid"), oinpEmailRequest.getOinpEventData().getJobId(), notificationRequest.getRealmId());
                notificationResponse.addSuccessRecipient(oinpEmailRequest.getOinpEventData().getNotifyEmailAddress());
            } else {
                logger.error("Email was not accepted by OINP" + oinpEmailRequest.toString());
                notificationResponse.addFailureRecipient(oinpEmailRequest.getOinpEventData().getNotifyEmailAddress(),"Email was not accepted by OINP statusCode=" + response.getStatusCode());
            }
        }catch (Exception ex){
            logger.error("Failed to send email" + ex.getLocalizedMessage());
        }

        return notificationResponse;
    }

    private ApiResponse postEmailReminder(OINPEmailRequest requestDTO, String realmId) {

        ApiResponse apiResponse = null;
        try {
            apiResponse = postEmailReminder(requestDTO, realmId, proxyUtil.getProxyUrl());
        } catch (Exception e) {
            logger.error("Error posting email. realmId={} ", realmId);
            Response.ResponseBuilder response = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            return new ApiResponse(response.build());
        }
        return apiResponse;

    }

    public ApiResponse postEmailReminder(OINPEmailRequest requestDTO, String realmId,
                                         String proxyUrl) throws Exception {
        String offlineTicket = systemOfflineTicket.getOfflineTicketHeader(requestDTO.getOinpEventMetaData().getTid(), realmId);
        Map<String, String> headers = new HashMap<>();
        //headers.put("Authorization", String.format(AUTH_HEADER_HEALTH, appId, appSecret));
        headers.put("Authorization", offlineTicket);
        headers.put("intuit_tid", requestDTO.getOinpEventMetaData().getTid());
        String url = baseURL + "/v1/events";
        String requestBodyJson = jsonHelper.toJson(requestDTO);
        ApiResponse apiResponse = httpUtilities.post(url, headers, requestBodyJson, proxyUrl, null, 3);
        return apiResponse;
    }

    @Override
    public boolean isComponentUp() {
        String authHeader = String.format(AUTH_HEADER_HEALTH, appId, appSecret);
        Map<String, String> headers = singletonMap("Authorization", authHeader);
        String url = baseURL + "/health/full";
        ApiResponse apiResponse = httpUtilities.get(url, headers, null, proxyUtil.getProxyUrl(), null, false);
        if (apiResponse == null) {
            return false;
        }
        if (apiResponse.getStatusCode() > 200 || apiResponse.getStatusCode() < 200) {
            Response.Status responseStatus = Response.Status.fromStatusCode(apiResponse.getStatusCode());
            switch (responseStatus) {
                case GONE:
                case FORBIDDEN:
                case NOT_MODIFIED:
                case NO_CONTENT:
                case UNSUPPORTED_MEDIA_TYPE:
                case CONFLICT:
                case NOT_FOUND:
                case SEE_OTHER:
                case BAD_REQUEST:
                case UNAUTHORIZED:
                case TEMPORARY_REDIRECT:
                case PRECONDITION_FAILED:
                case NOT_ACCEPTABLE:
                case MOVED_PERMANENTLY:
                case SERVICE_UNAVAILABLE:
                case INTERNAL_SERVER_ERROR:
                    logger.error("Error getting OINP health statusCode={} errorMessage={} ", apiResponse.getStatusCode(),
                            apiResponse.getResponseText());
                    return false;
                default:
                    return true;
            }
        }

        return true;
    }
}
