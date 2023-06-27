package com.mobility.india.rentalservice.model;

public enum EventState {
    CREATED("crtd"),
    IN_PROGRESS("inprogress"),
    CANCELLED("cancelled"),
    COMPLETED("completed");

    private final String value;

    EventState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

