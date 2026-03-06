package com.bridgelabz.wipro.java17_scenariobased.banking_fraudtranscationdetection;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FraudDetection {
    record Transaction(String transactionId, String accountId, double amount, String city, LocalTime time) {}

    public static void main(String[] args){
        List<Transaction> transactions= List.of(
                new Transaction("T1","A1",150000,"Bangalore",LocalTime.of(10,00)),
                new Transaction("T2","A1",5000,"Bangalore",LocalTime.of(10,01)),
                new Transaction("T3","A1",7000,"Mumbai",LocalTime.of(10,03)),
                new Transaction("T4","A2",2000,"Chennai",LocalTime.of(11,00)),
                new Transaction("T5","A1",6000,"Delhi",LocalTime.of(10,04)));

        List<String> suspiciousAccounts=detectFraud(transactions);
        System.out.println("Suspicious Accounts: "+suspiciousAccounts);
    }
    public static List<String> detectFraud(List<Transaction> transactions){

        Predicate<Transaction> highAmount=t-> Optional.ofNullable(t).map(Transaction::amount).orElse(0.0)>10000;

        Map<String,List<Transaction>> accountMap=transactions.stream().collect(Collectors.groupingBy(Transaction::accountId));

        return accountMap.entrySet().stream().filter(entry->{
            List<Transaction> txns=entry.getValue();
            boolean rule1=txns.stream().anyMatch(highAmount);
            boolean rule2=false;
            for(int i=0;i<txns.size();i++){
                int count=1;
                for(int j=i+1;j<txns.size();j++){
                    if(Duration.between(txns.get(i).time(),txns.get(j).time()).toMinutes()<=1){
                        count++;
                    }
                }
                if(count>3){
                    rule2=true;
                    break;
                }
            }
            boolean rule3=false;
            for(int i=0;i<txns.size();i++){
                for(int j=i+1;j<txns.size();j++){
                    if(!txns.get(i).city().equals(txns.get(j).city()) && Duration.between(txns.get(i).time(),txns.get(j).time()).toMinutes()<=5){
                        rule3=true;
                        break;
                    }
                }
            }
            return rule1||rule2||rule3;
        })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
