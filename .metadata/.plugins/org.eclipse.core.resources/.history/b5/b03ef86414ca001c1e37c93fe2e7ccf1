package com.intuit.accountant.services.dcm.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.accountant.services.common.DependentServiceException;
import com.intuit.accountant.services.common.httpclient.ApiResponse;
import com.intuit.accountant.services.common.httpclient.HttpUtilities;
import com.intuit.accountant.services.common.offlineticket.SystemOfflineTicket;
import com.intuit.accountant.services.common.util.ProxyUtil;
import com.intuit.accountant.services.dcm.model.CommonAttributes;
import com.intuit.accountant.services.dcm.model.DocumentResponse;
import com.intuit.accountant.services.dcm.model.FDPBaseRequest;
import com.intuit.accountant.services.dcm.resources.helpers.JSONHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.singletonMap;

/**
 * Created by sshashidhar on 16/05/19.
 */
@Component
public class DocumentServiceImpl implements IDocumentService {

    private Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private static String AUTH_HEADER_HEALTH = "Intuit_IAM_Authentication intuit_appid=%s,intuit_app_secret=%s,intuit_version=1.0";

    @Autowired
    public JSONHelper jsonHelper;

    @Autowired
    public SystemOfflineTicket systemOfflineTicket;

    @Autowired
    public HttpUtilities httpUtilities;

    @Autowired
    ProxyUtil proxyUtil;

    @Value("${fdp.service.url}")
    public String baseURL;

    @Value("${ius.service.appid}")
    private String appId;

    @Value("${ius.service.secret}")
    private String appSecret;

    @Override
    public DocumentResponse uploadDocument(String realmId, String tid) {
        FDPBaseRequest fdpBaseRequest = constructFDPBasePayload(realmId, tid);
        ObjectMapper objectMapper = new ObjectMapper();
        DocumentResponse documentResponse = new DocumentResponse();
        ApiResponse apiResponse;
        try{
            logger.info("Initiating Upload Request to Document Service with realmId={} tid={}", realmId, tid);
            apiResponse = postUploadDocument(fdpBaseRequest, realmId, tid, null);

            if(apiResponse.getStatusCode()==201){
                String location = apiResponse.getHeaderString("Location");
                logger.info("Response from Document Service with statusCode={} docId={} location={} tid={}", apiResponse.getStatusCode(), extractUUIDFromLocation(location), location, tid);
                documentResponse = objectMapper.readValue(apiResponse.getResponseText(), DocumentResponse.class);
                documentResponse.setStatusCode(apiResponse.getStatusCode());
                documentResponse.setDocId(extractUUIDFromLocation(location));
            } else {
                logger.error("Failed to request Document Service for document upload for statusCode={} errorMessage={} realmId={} tid={}",
                        apiResponse.getStatusCode(), apiResponse.getResponseText(), realmId, tid);
                throw new DependentServiceException("Failed to request Document Service for document upload for realmId=" + realmId +" tid=" + tid);
            }
        }catch (Exception e){
            logger.error("Failed to request Document Service for document upload for realmId={} tid={}", realmId, tid);
            throw new DependentServiceException("Failed to call FDP for Document Upload Request");
        }
        return documentResponse;
    }

    @Override
    public DocumentResponse downloadDocument(String realmId, String tid, String docId) {
        ObjectMapper objectMapper = new ObjectMapper();
        DocumentResponse documentResponse = new DocumentResponse();
        ApiResponse apiResponse;

        try{
            logger.info("Initiating Download Request to Document Service with docId={} realmId={} tid={}", docId, realmId, tid);
            apiResponse = postDownloadDocument(realmId, docId, tid, null);
            //documentResponse = objectMapper.readValue(apiResponse.getResponseText(), DocumentResponse.class);

            int retryCounter = 0;
            while (apiResponse.getResponseText().contains("PENDING_DOWNLOAD") && retryCounter < 16) {
                apiResponse = postDownloadDocument(realmId, docId, tid, null);
                //documentResponse = objectMapper.readValue(apiResponse.getResponseText(), DocumentResponse.class);
                retryCounter++;
                Thread.sleep(1000);
            }

            if (apiResponse.getResponseText().contains("PENDING_DOWNLOAD")) {
                throw new DependentServiceException("Call to FDP for document download request still in PENDING_DOWNLOAD state.");
            }

            if(apiResponse.getStatusCode() == 202 && apiResponse.getResponseText().contains("AVAILABLE_FOR_DOWNLOAD")) {
                logger.info("Response from Document Service for Download with statusCode={} for docId={} tid={}", apiResponse.getStatusCode(), docId, tid);
                documentResponse = objectMapper.readValue(apiResponse.getResponseText(), DocumentResponse.class);
                documentResponse.setStatusCode(apiResponse.getStatusCode());
                logger.info("Document status={} from Document Service for docId={} realmId={} tid={}",
                        documentResponse.getAsyncStatus(), docId, realmId, tid);
            }else {
                logger.error("Failed to request Document Service for document download for docId={} statusCode={} errorMessage={} realmId={} tid={}",
                        docId, apiResponse.getStatusCode(), apiResponse.getResponseText(), realmId, tid);
                throw new DependentServiceException("Failed to request Document Service for document download for realmId=" + realmId +" tid=" + tid);
            }

        }catch (Exception e){
            logger.error("Failed to request Document Service for download for docId={} realmId={} tid={} exception={}", docId, realmId, tid, e.toString());
            throw new DependentServiceException("Failed to call FDP for Document Download Request", e);
        }

        return documentResponse;
    }

    @Override
    public boolean deleteDocument(String realmId, String tid, String docId) {
        ApiResponse apiResponse;

        try{
            logger.info("Initiating Delete Request to Document Service with docId={} realmId={} tid={}", docId, realmId, tid);
            apiResponse = postDeleteDocument(realmId, docId, tid, null);

            if(apiResponse.getStatusCode() == 200){
                logger.info("Response from Document Service for Delete Document with statusCode={} for docId={} tid={}", apiResponse.getStatusCode(), docId, tid);
                return true;
            }else {
                logger.error("Failed to request Document Service for document delete for docId={} statusCode={} statusInfo={} errorMessage={} realmId={} tid={}",
                        docId, apiResponse.getStatusCode(), apiResponse.getStatusInfo().getReasonPhrase(), apiResponse.getResponseText(), realmId, tid);
                throw new DependentServiceException("Failed to request Document Service for document delete for realmId=" + realmId +" tid=" + tid);
            }

        }catch (Exception e){
            logger.error("Failed to request Document Service for delete for docId={} realmId={} tid={}", docId, realmId, tid);
            throw new DependentServiceException("Failed to call FDP for Document Delete Request");
        }
    }

    private FDPBaseRequest constructFDPBasePayload(String realmId, String tid) {
        FDPBaseRequest fdpBaseRequest = new FDPBaseRequest();
        CommonAttributes commonAttributes = new CommonAttributes();
        commonAttributes.setDocumentType("other");
        commonAttributes.setIs7216(false);
        commonAttributes.setName("DCM-FDP-DOC-" + realmId + "-" + tid);
        fdpBaseRequest.setCommonAttributes(commonAttributes);
        return fdpBaseRequest;
    }

    private ApiResponse postUploadDocument(FDPBaseRequest requestDTO, String realmId, String tid,
                                          String proxyUrl) throws Exception {

        Map<String, String> headers = constructAuthHeaders(realmId, tid);

        //Adding additional headers to make sure the owner of the document is the realm
        headers.put("intuit_resourceOwnerId",realmId);
        headers.put("intuit_resourceOwnerType","realm");

        String url = baseURL + "/v2/documents/largeFile?large-file-content-type=application/x-7z-compressed";
        String requestBodyJson = jsonHelper.toJson(requestDTO);
        ApiResponse apiResponse = httpUtilities.post(url, headers, requestBodyJson, proxyUrl, null, 3);
        return apiResponse;
    }

    private ApiResponse postDownloadDocument(String realmId, String docId, String tid,
                                           String proxyUrl) throws Exception {

        Map<String, String> headers = constructAuthHeaders(realmId, tid);

        String url = baseURL + "/v2/documents/" + docId + "/sources/1";
        ApiResponse apiResponse = httpUtilities.get(url, headers, null, proxyUrl, null, false, 3);
        return apiResponse;
    }

    private ApiResponse postDeleteDocument(String realmId, String docId, String tid,
                                             String proxyUrl) throws Exception {

        Map<String, String> headers = constructAuthHeaders(realmId, tid);

        //This header is required if you are not concerned if the document has been modified since you last fetched it, before deleting it, hence the future date
        headers.put("If-Unmodified-Since", "Wed, 01 Jan 2040 00:00:00 GMT");
        String url = baseURL + "/v2/documents/" + docId;
        ApiResponse apiResponse = httpUtilities.delete(url, headers,null,20000,3);
        return apiResponse;
    }

    private static String extractUUIDFromLocation(String url){
        final String FDP_LOCATION_REGX = "\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}";
        Pattern pairRegex = Pattern.compile(FDP_LOCATION_REGX);
        Matcher matcher = pairRegex.matcher(url);

        while (matcher.find()) {
            String a = matcher.group(0);
            return a;
        }
        return null;
    }

    private Map<String, String> constructAuthHeaders(String realmId, String tid) throws Exception{

        Map<String, String> headers = new HashMap<>();
        String offlineTicket = systemOfflineTicket.getOfflineTicketHeader(tid, realmId);
        headers.put("Authorization", offlineTicket);

        headers.put("intuit_tid", tid);

        return headers;
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
                    logger.error("Error getting Document Service health statusCode={} errorMessage={} ", apiResponse.getStatusCode(),
                            apiResponse.getResponseText());
                    return false;
                default:
                    return true;
            }
        }

        return true;
    }
}
