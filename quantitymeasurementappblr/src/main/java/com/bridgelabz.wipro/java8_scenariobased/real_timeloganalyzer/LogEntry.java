package com.bridgelabz.wipro.java8_scenariobased.real_timeloganalyzer;

public class LogEntry {
    String serviceName;
    String level;
    long responseTime;

    public LogEntry(String serviceName, String level, long responseTime) {
        this.serviceName = serviceName;
        this.level = level;
        this.responseTime = responseTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getLevel() {
        return level;
    }

    public long getResponseTime() {
        return responseTime;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "serviceName='" + serviceName + '\'' +
                ", level='" + level + '\'' +
                ", responseTime=" + responseTime +
                '}';
    }
}
