package com.mobility.india.rentalservice.notificationclient;

import com.mobility.india.rentalservice.events.Event;

/**
 * Client class to access existing Notification service. The Notification Service
 */
public interface PushNotification {
    /**
     * Push notify to the user. The event data is wrapped in #Event object
     * @param event
     * @param userID
     */
    public void notify(Event event, Long userID);
}
