package com.intuit.accountant.services.common.offlineticket;

import com.intuit.platform.integration.hats.client.request.GetAuthHeaderForSystemOfflineTicketRequest;
import com.intuit.platform.integration.hats.client.request.GetAuthHeaderForSystemOfflineTicketRequestBuilder;
import com.intuit.platform.integration.hats.common.OfflineTicketAuthorizationHeader;
import com.intuit.platform.integration.iam.client.ClientConfiguration;
import com.intuit.platform.integration.iam.client.IAMServiceConfiguration;
import com.intuit.platform.integration.iam.client.auth.BasicIAMServiceCredentials;
import com.intuit.platform.integration.iam.client.auth.IAMServiceCredentialsProvider;
import com.intuit.platform.integration.iam.client.auth.StaticIAMServiceCredentialsProvider;
import com.intuit.platform.integration.iamticket.client.IAMOfflineTicketClient;
import com.intuit.platform.integration.iamticket.client.IAMOfflineTicketServiceConfiguration;
import com.intuit.platform.integration.iamticket.client.IOfflineTicketClient;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.Arrays;

@Component
public class SystemOfflineTicket {

    private static final Log logger = LogFactory.getLog(SystemOfflineTicket.class);

    private static final String DUMMY_IP = "123.45.67.89";
    private static final Integer OFFLINE_TICKET_MAX_LIFE_SECONDS = 3 * 24 * 60 * 60; // 3
    // days
    private final long REFRESH_INTERVAL_MILLISECONDS = 5 * 60 * 1000; // 5
    // minutes

    private IOfflineTicketClient ticketClient;
    private String accessServiceTicketsURL;
    private int connectionTimeout;
    private int socketTimeout;
    protected int maxConnections;
    protected int maxConnectionsPerRoute;
    protected int maxErrorRetry;
    private boolean staleCheckingEnabled;
    private String localHostAddress;

    private String offlineAppId;
    private String offlineAppSecret;
    private String offlineAssetId;
    private String offlineRealmId;
    private String offlineUserName;
    private String offlineUserPassword;
    private String realmIdUnderProcessing;
    private long lastRefreshTime;
    private String offlineTicketHeader;

    @Value("${service.httpclient.connectionTimeout:5000}")
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Value("${service.httpclient.socketTimeout:5000}")
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    @Value("${service.httpclient.maxConnections:100}")
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    @Value("${service.httpclient.maxConnectionsPerRoute:10}")
    public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }

    @Value("${service.httpclient.maxErrorRetry:5}")
    public void setMaxErrorRetry(int maxErrorRetry) {
        this.maxErrorRetry = maxErrorRetry;
    }

    @Value("${service.httpclient.staleCheckingEnabled:false}")
    public void setStaleCheckingEnabled(boolean staleCheckingEnabled) {
        this.staleCheckingEnabled = staleCheckingEnabled;
    }

    @Value("${service.offlineticket.access.service.tickets.url}")
    public void setAccessServiceTicketsURL(String accessServiceTicketsURL) {
        this.accessServiceTicketsURL = accessServiceTicketsURL + "/offline_tickets";
    }

    @Value("${access.service.appid}")
    public void setOfflineAppId(String offlineAppId) {
        this.offlineAppId = offlineAppId;
    }

    @Value("${access.service.secret}")
    public void setOfflineAppSecret(String offlineAppSecret) {
        this.offlineAppSecret = offlineAppSecret;
    }

    @Value("${access.service.assetid}")
    public void setOfflineAssetId(String offlineAssetId) {
        this.offlineAssetId = offlineAssetId;
    }

    @Value("${service.offlineticket.offlinerealmid}")
    public void setOfflineRealmId(String offlineRealmId) {
        this.offlineRealmId = offlineRealmId;
    }

    @Value("${service.offlineticket.username}")
    public void setOfflineUserName(String offlineUserName) {
        this.offlineUserName = offlineUserName;
    }

    @Value("${service.offlineticket.password}")
    public void setOfflineUserPassword(String offlineUserPassword) {
        this.offlineUserPassword = offlineUserPassword;
    }

    @PostConstruct
    protected void init() {
        logger.info(String.format(
                "Initializing IUSOfflineTicketClient With accessServiceTicketsURL=%s, appId=%s, realmId=%s, connectionTimeout=%s, socketTimeout=%s, maxConnections=%s, maxConnectionsPerRoute=%s, maxErrorRetry=%s, staleCheckingEnabled=%s",
                accessServiceTicketsURL, offlineAppId, offlineRealmId, connectionTimeout, socketTimeout, maxConnections,
                maxConnectionsPerRoute, maxErrorRetry, staleCheckingEnabled));
        ticketClient = getTicketClient();
        try {
            localHostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            localHostAddress = DUMMY_IP;
        }
        logger.info("Using localHostAddress " + localHostAddress);
        lastRefreshTime = 0L;
    }

    private ClientConfiguration getClientConfig() {
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setConnectionTimeout(connectionTimeout);
        clientConfig.setSocketTimeout(socketTimeout);
        clientConfig.setMaxConnections(maxConnections);
        clientConfig.setMaxConnectionsPerRoute(maxConnectionsPerRoute);
        clientConfig.setMaxErrorRetry(maxErrorRetry);
        clientConfig.setStaleCheckingEnabled(staleCheckingEnabled);
        clientConfig.setOfflineTicketsCachingSeconds(6000);
        clientConfig.setOfflineTicketRenewLifeSeconds(6000);
        return clientConfig;
    }

    private IOfflineTicketClient getTicketClient() {
        IAMServiceConfiguration serviceConfig = getServiceConfig();
        ClientConfiguration clientConfig = getClientConfig();
        return new IAMOfflineTicketClient(serviceConfig, clientConfig);
    }

    private IAMServiceConfiguration getServiceConfig() {
        IAMServiceCredentialsProvider serviceCredentialsProvider = new StaticIAMServiceCredentialsProvider(
                new BasicIAMServiceCredentials(offlineAppId, offlineAppSecret));
        return new IAMOfflineTicketServiceConfiguration(accessServiceTicketsURL, serviceCredentialsProvider);
    }

    public String getOfflineTicketHeader(String tid, String realmId) throws Exception {
        if (StringUtils.equals(realmId, realmIdUnderProcessing) && !shouldRefreshTicket()) {
            return offlineTicketHeader;
        }

        logger.info("Generating new offline ticket for realmId=" + realmId);
        GetAuthHeaderForSystemOfflineTicketRequest request = new GetAuthHeaderForSystemOfflineTicketRequestBuilder()
                .setAppId(offlineAppId).setAppSecret(offlineAppSecret).setAssetId(offlineAssetId)
                .setUsername(offlineUserName).setPassword(offlineUserPassword).setUserContextRealmId(offlineRealmId)
                .setTargetRealmId(realmId).setAudiences(Arrays.asList(offlineAssetId))
                .setIp(nullSafeOriginatingIp(null)).setMaxLifeSeconds(OFFLINE_TICKET_MAX_LIFE_SECONDS).build();

        try {
            OfflineTicketAuthorizationHeader OLTHeader = ticketClient.getAuthHeaderForSystemOfflineTicket(request);
            offlineTicketHeader = OLTHeader.getAuthorizationHeader();
            realmIdUnderProcessing = realmId;
            lastRefreshTime = System.currentTimeMillis();
            return offlineTicketHeader;
        } catch (Exception e) {
            logger.error(String.format("Unable to use Offline ticket for tid=%s realmId=%s", tid, realmId), e);
            throw e;
        }
    }

    public void refreshOfflineTicket() {
        lastRefreshTime = 0L;
    }

    private boolean shouldRefreshTicket() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastRefreshTime) > REFRESH_INTERVAL_MILLISECONDS;
    }

    private String nullSafeOriginatingIp(String originatingIp) {
        if (StringUtils.isBlank(originatingIp)) {
            originatingIp = localHostAddress;
        }
        return originatingIp;
    }
}
