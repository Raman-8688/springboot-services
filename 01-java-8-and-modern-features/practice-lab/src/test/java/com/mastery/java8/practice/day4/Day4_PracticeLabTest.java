package com.mastery.java8.practice.day4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class Day4_PracticeLabTest {

    private Day4_PracticeLab practiceLab;

    @BeforeEach
    void setUp() {
        practiceLab = new Day4_PracticeLab();
    }

    @Test
    @DisplayName("Task 1: Should safely extract email from nested Optional")
    void testExtractUserEmailSafely() {
        Day4_PracticeLab.AccountInfo accWithEmail = new Day4_PracticeLab.AccountInfo("ACC-1", Optional.of("raman@dev.com"));
        Day4_PracticeLab.UserProfile userWithEmail = new Day4_PracticeLab.UserProfile("U1", Optional.of(accWithEmail));

        Day4_PracticeLab.AccountInfo accNoEmail = new Day4_PracticeLab.AccountInfo("ACC-2", Optional.empty());
        Day4_PracticeLab.UserProfile userNoEmail = new Day4_PracticeLab.UserProfile("U2", Optional.of(accNoEmail));

        assertEquals("raman@dev.com", practiceLab.extractUserEmailSafely(Optional.of(userWithEmail)));
        assertEquals("NO_EMAIL_PROVIDED", practiceLab.extractUserEmailSafely(Optional.of(userNoEmail)));
        assertEquals("NO_EMAIL_PROVIDED", practiceLab.extractUserEmailSafely(Optional.empty()));
    }

    @Test
    @DisplayName("Task 2: Should sum large list using parallel stream")
    void testSumLargeListOfNumbersParallel() {
        List<Long> nums = LongStream.rangeClosed(1, 100_000).boxed().toList();
        long total = practiceLab.sumLargeListOfNumbersParallel(nums);
        assertEquals(5000050000L, total);
    }
}
