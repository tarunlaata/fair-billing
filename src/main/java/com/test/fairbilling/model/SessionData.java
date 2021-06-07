package com.test.fairbilling.model;

import java.time.LocalTime;
import java.util.SortedMap;

public class SessionData {
    private String name;
    private double sessionCount;
    private long totalTime;
    private SortedMap<Integer, LocalTime> cacheTime;
    private String lastEvent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(double sessionCount) {
        this.sessionCount = sessionCount;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public SortedMap<Integer, LocalTime> getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(SortedMap<Integer, LocalTime> cacheTime) {
        this.cacheTime = cacheTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public String getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(String lastEvent) {
        this.lastEvent = lastEvent;
    }

    @Override
    public String toString() {
        return name + " " + (int) sessionCount + " " + totalTime;
    }
}
