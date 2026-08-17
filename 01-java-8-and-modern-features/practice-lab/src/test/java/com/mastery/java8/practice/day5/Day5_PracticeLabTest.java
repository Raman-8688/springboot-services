package com.mastery.java8.practice.day5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Day5_PracticeLabTest {

    private Day5_PracticeLab practiceLab;
    private List<Day5_PracticeLab.EnterpriseOrder> orders;

    @BeforeEach
    void setUp() {
        practiceLab = new Day5_PracticeLab();
        orders = List.of(
            new Day5_PracticeLab.EnterpriseOrder("O1", "CUST-1", "ELECTRONICS", 1200.0, "COMPLETED"),
            new Day5_PracticeLab.EnterpriseOrder("O2", "CUST-2", "GROCERIES", 500.0, "COMPLETED"),
            new Day5_PracticeLab.EnterpriseOrder("O3", "CUST-1", "ELECTRONICS", 800.0, "COMPLETED"),
            new Day5_PracticeLab.EnterpriseOrder("O4", "CUST-3", "CLOTHING", 3000.0, "COMPLETED"),
            new Day5_PracticeLab.EnterpriseOrder("O5", "CUST-2", "GROCERIES", 200.0, "CANCELLED")
        );
    }

    @Test
    @DisplayName("Task 1: Should find top K spenders correctly")
    void testGetTopKSpenders() {
        List<String> top2 = practiceLab.getTopKSpenders(orders, 2);
        assertEquals(List.of("CUST-3", "CUST-1"), top2);
    }

    @Test
    @DisplayName("Task 2: Should correctly perform 2-level grouping revenue sum")
    void testRevenueByCategoryAndStatus() {
        Map<String, Map<String, Double>> revMap = practiceLab.getRevenueByCategoryAndStatus(orders);
        assertEquals(2000.0, revMap.get("ELECTRONICS").get("COMPLETED"));
        assertEquals(500.0, revMap.get("GROCERIES").get("COMPLETED"));
        assertEquals(200.0, revMap.get("GROCERIES").get("CANCELLED"));
    }
}
