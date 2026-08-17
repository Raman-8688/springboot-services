package com.mastery.java8.practice;

import java.util.*;
import java.util.stream.Collectors;

public class StreamsPracticeLab {

    public record Transaction(String id, String category, double amount, String status) {}

    /**
     * TASK 1: Filter completed transactions and return total sum.
     */
    public double getTotalAmountForCompletedTransactions(List<Transaction> transactions) {
        // TODO: Filter status == "COMPLETED" and sum the amounts using Streams
        return transactions.stream()
            .filter(t -> "COMPLETED".equalsIgnoreCase(t.status()))
            .mapToDouble(Transaction::amount)
            .sum();
    }

    /**
     * TASK 2: Group transactions by category and count how many exist in each category.
     */
    public Map<String, Long> getTransactionCountByCategory(List<Transaction> transactions) {
        // TODO: Use Collectors.groupingBy and Collectors.counting
        return transactions.stream()
            .collect(Collectors.groupingBy(Transaction::category, Collectors.counting()));
    }

    /**
     * TASK 3: Find the highest transaction amount in a given category.
     */
    public Optional<Transaction> getHighestTransactionInCategory(List<Transaction> transactions, String category) {
        // TODO: Filter by category and find max using Comparator
        return transactions.stream()
            .filter(t -> t.category().equalsIgnoreCase(category))
            .max(Comparator.comparingDouble(Transaction::amount));
    }
}
