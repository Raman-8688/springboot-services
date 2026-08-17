package com.mastery.java8.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StreamsPracticeLabTest {

    private StreamsPracticeLab practiceLab;
    private List<StreamsPracticeLab.Transaction> testTransactions;

    @BeforeEach
    void setUp() {
        practiceLab = new StreamsPracticeLab();
        testTransactions = List.of(
            new StreamsPracticeLab.Transaction("T1", "ELECTRONICS", 500.0, "COMPLETED"),
            new StreamsPracticeLab.Transaction("T2", "GROCERIES", 150.0, "COMPLETED"),
            new StreamsPracticeLab.Transaction("T3", "ELECTRONICS", 1200.0, "PENDING"),
            new StreamsPracticeLab.Transaction("T4", "CLOTHING", 200.0, "COMPLETED"),
            new StreamsPracticeLab.Transaction("T5", "ELECTRONICS", 850.0, "COMPLETED")
        );
    }

    @Test
    @DisplayName("Should correctly calculate sum of completed transactions")
    void testTotalAmountForCompletedTransactions() {
        double total = practiceLab.getTotalAmountForCompletedTransactions(testTransactions);
        assertEquals(1700.0, total, 0.001);
    }

    @Test
    @DisplayName("Should correctly group and count transactions by category")
    void testTransactionCountByCategory() {
        Map<String, Long> countMap = practiceLab.getTransactionCountByCategory(testTransactions);
        assertEquals(3L, countMap.get("ELECTRONICS"));
        assertEquals(1L, countMap.get("GROCERIES"));
        assertEquals(1L, countMap.get("CLOTHING"));
    }

    @Test
    @DisplayName("Should find highest transaction amount in a category")
    void testHighestTransactionInCategory() {
        Optional<StreamsPracticeLab.Transaction> highestElec = 
            practiceLab.getHighestTransactionInCategory(testTransactions, "ELECTRONICS");
        
        assertTrue(highestElec.isPresent());
        assertEquals(1200.0, highestElec.get().amount());
        assertEquals("T3", highestElec.get().id());
    }
}
