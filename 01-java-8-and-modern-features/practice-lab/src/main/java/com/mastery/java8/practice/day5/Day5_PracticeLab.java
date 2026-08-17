package com.mastery.java8.practice.day5;

import java.util.*;
import java.util.stream.Collectors;

public class Day5_PracticeLab {

    public record EnterpriseOrder(String orderId, String customerId, String category, double amount, String status) {}

    /**
     * TASK 1: Find top K customers sorted by total spent on "COMPLETED" orders.
     */
    public List<String> getTopKSpenders(List<EnterpriseOrder> orders, int k) {
        // TODO: Filter COMPLETED status, group by customerId summing amount, sort descending by value, limit k, collect keys to list
        return orders.stream()
            .filter(o -> "COMPLETED".equalsIgnoreCase(o.status()))
            .collect(Collectors.groupingBy(
                EnterpriseOrder::customerId,
                Collectors.summingDouble(EnterpriseOrder::amount)
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(k)
            .map(Map.Entry::getKey)
            .toList();
    }

    /**
     * TASK 2: Perform 2-level nested grouping: Category -> Status -> Total Revenue Sum.
     */
    public Map<String, Map<String, Double>> getRevenueByCategoryAndStatus(List<EnterpriseOrder> orders) {
        // TODO: Use Collectors.groupingBy category and downstream groupingBy status with summingDouble amount
        return orders.stream()
            .collect(Collectors.groupingBy(
                EnterpriseOrder::category,
                Collectors.groupingBy(
                    EnterpriseOrder::status,
                    Collectors.summingDouble(EnterpriseOrder::amount)
                )
            ));
    }
}
