package com.mastery.java8.practice.day2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class Day2_PracticeLabTest {

    private Day2_PracticeLab practiceLab;
    private List<Day2_PracticeLab.CustomerOrder> orders;

    @BeforeEach
    void setUp() {
        practiceLab = new Day2_PracticeLab();
        orders = List.of(
            new Day2_PracticeLab.CustomerOrder("O1", "Raman", List.of("Laptop", "Mouse"), 1200.0, "COMPLETED"),
            new Day2_PracticeLab.CustomerOrder("O2", "Kiran", List.of("Monitor", "Mouse"), 400.0, "COMPLETED"),
            new Day2_PracticeLab.CustomerOrder("O3", "Raman", List.of("Keyboard"), 150.0, "CANCELLED"),
            new Day2_PracticeLab.CustomerOrder("O4", "Raman", List.of("Headphones", "Laptop"), 1500.0, "COMPLETED")
        );
    }

    @Test
    @DisplayName("Task 1: Should extract unique sorted items for completed orders")
    void testGetUniquePurchasedItems() {
        List<String> items = practiceLab.getUniquePurchasedItems(orders);
        assertEquals(List.of("Headphones", "Laptop", "Monitor", "Mouse"), items);
    }

    @Test
    @DisplayName("Task 2: Should find first high value order for customer")
    void testGetFirstHighValueOrderForCustomer() {
        Optional<Day2_PracticeLab.CustomerOrder> highValOrder = 
            practiceLab.getFirstHighValueOrderForCustomer(orders, "Raman", 1000.0);
        
        assertTrue(highValOrder.isPresent());
        assertEquals("O1", highValOrder.get().orderId());
    }
}
