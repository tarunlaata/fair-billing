package com.test.fairbilling.model;

import java.time.LocalTime;

public class InputData {
    private LocalTime time;
    private String name;
    private String event;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return time + " " + name + " " + event;
    }
}
