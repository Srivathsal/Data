package com.intuit.accountant.services.dcm.notifications;

import com.intuit.accountant.services.dcm.notifications.exceptions.NotificationProviderFactoryException;
import com.intuit.accountant.services.dcm.notifications.exceptions.NotificationProviderNotAvailableException;
import com.intuit.accountant.services.dcm.notifications.oinp.OINPNotificationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationProviderFactory {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OINPNotificationProvider oinpNotificationProvider;


    public void setTxeNotificationProvider(
            OINPNotificationProvider txeNotificationProvider) {
        this.oinpNotificationProvider = txeNotificationProvider;
    }

    public INotificationProvider getNotificationProvider(NotificationProvider providerId, NotificationType notificationType) {
        if (providerId == null)
            throw new NotificationProviderFactoryException("Unable to create a notification provider with a null provider id.");
        if (notificationType == null)
            throw new NotificationProviderFactoryException("Unable to create a notification provider for provider " + providerId.toString() + " for a null notification type.");
        INotificationProvider notificationProvider = null;
        switch (notificationType) {
            case EMAIL:
                switch (providerId) {
                    case OINP :
                        notificationProvider = oinpNotificationProvider;
                        break;
                    default:
                        String message = "Notification provider " + notificationProvider + " does not support notifications of type " +  notificationType.toString() + ".";
                        throw new NotificationProviderNotAvailableException(message);
                }
                break;
            case INVITE:
            case SMS :
            case PUSH :
            default:
                logger.error("Requested unsupported notification provider {}", providerId.toString());
                throw new NotificationProviderNotAvailableException("Notification provider " + providerId + " with type " + notificationType.toString() + " is not currently supported.");
        }
        return notificationProvider;
    }
}
