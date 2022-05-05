package com.intuit.accountant.services.dcm.notifications;

public interface INotificationProvider {
    NotificationResponse sendNotification(NotificationRequest notificationRequest);

    boolean isComponentUp();
}
