package com.dbm.main.mongo.model;

import org.springframework.data.annotation.Id;

import java.util.Calendar;
import java.util.Date;

public abstract class Event {
    @Id
    String id;
    String eventId;
    Date eventCreatedDate;

    public Event(){}
    public Event(String eventId, Date eventCreatedDate) {
        this.eventId = eventId;
        if (eventCreatedDate != null) {
            this.eventCreatedDate = eventCreatedDate;
        }else {
            this.eventCreatedDate = Calendar.getInstance().getTime();
        }
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public Date getEventCreatedDate() {
        return eventCreatedDate;
    }
    public void setEventCreatedDate(Date eventCreatedDate) {
        this.eventCreatedDate = eventCreatedDate;
    }
}
