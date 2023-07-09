package com.example.testing;

public class Event {
    private String eventName;
    private String eventTime;
    private String eventFromTime;
    private String eventTillTime;
    private String eventLocation;
    private boolean isStudent;

    public Event() {
        // Default constructor required for Firebase Realtime Database
    }

    public Event(String eventName, String eventTime, String eventFromTime, String eventTillTime, String eventLocation, boolean isStudent) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventFromTime = eventFromTime;
        this.eventTillTime = eventTillTime;
        this.eventLocation = eventLocation;
        this.isStudent = isStudent;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventFromTime() {
        return eventFromTime;
    }

    public void setEventFromTime(String eventFromTime) {
        this.eventFromTime = eventFromTime;
    }

    public String getEventTillTime() {
        return eventTillTime;
    }

    public void setEventTillTime(String eventTillTime) {
        this.eventTillTime = eventTillTime;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }
}

