package com.mobility.india.rentalservice.events;

/**
 * Holds Event data as byte. The event data is immutable
 */
public class Event {
    private final byte[] eventData;

    public Event(byte[] eventData) {
        this.eventData = eventData;
    }
    private Long timeStamp;
    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
