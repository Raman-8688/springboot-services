package com.mastery.java8.day2;

import java.util.List;
import java.util.Optional;

public class Day2_Streams_Reference {

    public record Product(String id, String name, String category, double price, boolean inStock) {}

    public static void main(String[] args) {
        List<Product> products = List.of(
            new Product("P101", "Laptop", "Electronics", 1200.0, true),
            new Product("P102", "Smartphone", "Electronics", 800.0, true),
            new Product("P103", "Desk Chair", "Furniture", 250.0, false),
            new Product("P104", "Monitor", "Electronics", 350.0, true),
            new Product("P105", "Coffee Table", "Furniture", 150.0, true)
        );

        System.out.println("=== 1. Demonstrating Lazy Evaluation & Short-Circuiting ===");
        Optional<Product> firstInStockElectronics = products.stream()
            .peek(p -> System.out.println("Processing product: " + p.name()))
            .filter(p -> "Electronics".equalsIgnoreCase(p.category()))
            .filter(Product::inStock)
            .findFirst();

        firstInStockElectronics.ifPresent(p -> System.out.println("Found First: " + p.name()));

        System.out.println("\n=== 2. flatMap Example: Extracting All Tags from Orders ===");
        record Order(String orderId, List<String> items) {}
        List<Order> orders = List.of(
            new Order("ORD-1", List.of("Laptop", "Mouse")),
            new Order("ORD-2", List.of("Keyboard", "Monitor")),
            new Order("ORD-3", List.of("Mouse", "Headphones"))
        );

        List<String> distinctItems = orders.stream()
            .flatMap(order -> order.items().stream())
            .distinct()
            .sorted()
            .toList();

        System.out.println("All Unique Items Ordered: " + distinctItems);
    }
}
