package com.mastery.java8.day3;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3_Collectors_Reference {

    public record SalesRecord(String id, String region, String salesperson, double amount) {}

    public static void main(String[] args) {
        List<SalesRecord> sales = List.of(
            new SalesRecord("S1", "North", "Raman", 4500.0),
            new SalesRecord("S2", "South", "Anitha", 6200.0),
            new SalesRecord("S3", "North", "Kiran", 3100.0),
            new SalesRecord("S4", "South", "Raman", 7800.0),
            new SalesRecord("S5", "North", "Raman", 2200.0)
        );

        System.out.println("=== 1. Total Sales Amount per Region ===");
        Map<String, Double> totalByRegion = sales.stream()
            .collect(Collectors.groupingBy(
                SalesRecord::region,
                Collectors.summingDouble(SalesRecord::amount)
            ));
        totalByRegion.forEach((reg, total) -> System.out.printf("%s Region: $%.2f%n", reg, total));

        System.out.println("\n=== 2. Top Salesperson per Region using collectingAndThen ===");
        Map<String, SalesRecord> topByRegion = sales.stream()
            .collect(Collectors.groupingBy(
                SalesRecord::region,
                Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.comparingDouble(SalesRecord::amount)),
                    Optional::get
                )
            ));
        topByRegion.forEach((reg, record) -> 
            System.out.println(reg + " Top Sale: " + record.salesperson() + " ($" + record.amount() + ")"));

        System.out.println("\n=== 3. Partitioning Sales into High-Value (> $5000) vs Regular ===");
        Map<Boolean, List<SalesRecord>> partitioned = sales.stream()
            .collect(Collectors.partitioningBy(s -> s.amount() > 5000.0));

        System.out.println("High Value Count: " + partitioned.get(true).size());
        System.out.println("Regular Count: " + partitioned.get(false).size());

        System.out.println("\n=== 4. Handling Duplicate Keys in toMap() ===");
        // Convert to Map<Salesperson, Highest Single Sale>
        Map<String, Double> maxSaleByPerson = sales.stream()
            .collect(Collectors.toMap(
                SalesRecord::salesperson,
                SalesRecord::amount,
                Math::max // Merge function resolves key collisions!
            ));
        maxSaleByPerson.forEach((person, maxSale) -> System.out.println(person + " Max Sale: $" + maxSale));
    }
}
