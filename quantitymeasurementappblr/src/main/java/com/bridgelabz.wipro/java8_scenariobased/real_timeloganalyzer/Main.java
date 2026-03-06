package com.bridgelabz.wipro.java8_scenariobased.real_timeloganalyzer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

    private static List<LogEntry> logEntries=List.of(
            new LogEntry("Payment","ERROR",500),
            new LogEntry("Payment","INFO",120),
            new LogEntry("Order","ERROR",700),
            new LogEntry("Order","ERROR",300),
            new LogEntry("Payment","WARN",200)
    );
    public static void main(String[] args){
        Map<String,Long> totalledErrorperservice=logEntries.stream()
                .filter(log->"ERROR".equalsIgnoreCase(log.getLevel()))
                .collect(Collectors.groupingBy(LogEntry::getServiceName, Collectors.counting()));
        totalledErrorperservice.forEach((serviceName,errorCount)-> System.out.println(serviceName+" "+errorCount));

        Map<String, Double> averageResponseTimePerService=logEntries.stream()
                .collect(Collectors.groupingBy(LogEntry::getServiceName, Collectors.averagingLong(LogEntry::getResponseTime)));
        averageResponseTimePerService.forEach((serviceName,avgResponseTime)-> System.out.println(serviceName+" "+avgResponseTime));

        Optional<Map.Entry<String,Long>> serviceWithMostErrors=totalledErrorperservice.entrySet().stream()
                .max(Map.Entry.comparingByValue());
        serviceWithMostErrors.ifPresent(entry-> System.out.println("Service with most errors: "+entry.getKey()+" with "+entry.getValue()+" errors"));

        List<Long> sortedResponseTimes=logEntries.stream()
                .map(LogEntry::getResponseTime)
                .sorted()
                .toList();
        int index= (int) (Math.ceil(0.95*sortedResponseTimes.size())-1);
        long percentile95th=sortedResponseTimes.get(index);
        System.out.println("95th percentile response time: "+percentile95th);

    }
}
