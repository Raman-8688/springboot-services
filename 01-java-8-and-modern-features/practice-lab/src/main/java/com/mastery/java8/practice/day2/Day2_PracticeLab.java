package com.mastery.java8.practice.day2;

import java.util.List;
import java.util.Optional;

public class Day2_PracticeLab {

    public record CustomerOrder(String orderId, String customerName, List<String> items, double totalAmount, String status) {}

    /**
     * TASK 1: Extract all distinct items purchased across all completed orders, sorted alphabetically.
     */
    public List<String> getUniquePurchasedItems(List<CustomerOrder> orders) {
        // TODO: Filter status == "COMPLETED", flatMap items, distinct, sorted
        return orders.stream()
            .filter(o -> "COMPLETED".equalsIgnoreCase(o.status()))
            .flatMap(o -> o.items().stream())
            .distinct()
            .sorted()
            .toList();
    }

    /**
     * TASK 2: Find the first order for a given customer that exceeds a minimum total amount threshold.
     */
    public Optional<CustomerOrder> getFirstHighValueOrderForCustomer(List<CustomerOrder> orders, String customerName, double minAmount) {
        // TODO: Filter customerName and totalAmount >= minAmount, use findFirst()
        return orders.stream()
            .filter(o -> o.customerName().equalsIgnoreCase(customerName))
            .filter(o -> o.totalAmount() >= minAmount)
            .findFirst();
    }
}
