package com.mastery.java8.day5;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Day5_MNC_Capstone_Reference {

    public record PaymentTransaction(String id, String customerId, String category, double amount, LocalDate date, String status) {}

    public static void main(String[] args) {
        List<PaymentTransaction> transactions = List.of(
            new PaymentTransaction("TX1", "CUST-1", "ELECTRONICS", 1200.0, LocalDate.now().minusDays(5), "SUCCESS"),
            new PaymentTransaction("TX2", "CUST-2", "GROCERIES", 250.0, LocalDate.now().minusDays(10), "SUCCESS"),
            new PaymentTransaction("TX3", "CUST-1", "FASHION", 450.0, LocalDate.now().minusDays(15), "SUCCESS"),
            new PaymentTransaction("TX4", "CUST-3", "ELECTRONICS", 2200.0, LocalDate.now().minusDays(20), "SUCCESS"),
            new PaymentTransaction("TX5", "CUST-2", "ELECTRONICS", 1800.0, LocalDate.now().minusDays(25), "SUCCESS"),
            new PaymentTransaction("TX6", "CUST-1", "ELECTRONICS", 900.0, LocalDate.now().minusDays(2), "FAILED")
        );

        System.out.println("=== 1. Top Spenders (Customer IDs) with Successful Transactions ===");
        List<Map.Entry<String, Double>> topSpenders = transactions.stream()
            .filter(t -> "SUCCESS".equalsIgnoreCase(t.status()))
            .collect(Collectors.groupingBy(
                PaymentTransaction::customerId,
                Collectors.summingDouble(PaymentTransaction::amount)
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .toList();

        topSpenders.forEach(entry -> 
            System.out.printf("Customer %s Total Spend: $%.2f%n", entry.getKey(), entry.getValue()));

        System.out.println("\n=== 2. Multi-Level Grouping: Category -> Status -> Count ===");
        Map<String, Map<String, Long>> categoryStatusCount = transactions.stream()
            .collect(Collectors.groupingBy(
                PaymentTransaction::category,
                Collectors.groupingBy(
                    PaymentTransaction::status,
                    Collectors.counting()
                )
            ));

        categoryStatusCount.forEach((cat, statusMap) -> 
            System.out.println(cat + " -> " + statusMap));
    }
}
