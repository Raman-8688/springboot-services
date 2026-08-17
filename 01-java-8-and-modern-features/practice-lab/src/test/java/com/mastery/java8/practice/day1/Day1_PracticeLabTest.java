package com.mastery.java8.practice.day1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class Day1_PracticeLabTest {

    private Day1_PracticeLab practiceLab;

    @BeforeEach
    void setUp() {
        practiceLab = new Day1_PracticeLab();
    }

    @Test
    @DisplayName("Task 1: Should correctly evaluate active adult user predicate")
    void testAdultActiveUserPredicate() {
        Predicate<Day1_PracticeLab.UserProfile> predicate = practiceLab.getAdultActiveUserPredicate();
        
        Day1_PracticeLab.UserProfile adultActive = new Day1_PracticeLab.UserProfile("raman", "raman@dev.com", 25, true);
        Day1_PracticeLab.UserProfile minorActive = new Day1_PracticeLab.UserProfile("kid", "kid@dev.com", 16, true);
        Day1_PracticeLab.UserProfile adultInactive = new Day1_PracticeLab.UserProfile("old", "old@dev.com", 30, false);

        assertTrue(predicate.test(adultActive));
        assertFalse(predicate.test(minorActive));
        assertFalse(predicate.test(adultInactive));
    }

    @Test
    @DisplayName("Task 2: Should correctly format user into uppercase string")
    void testUserFormatterFunction() {
        Function<Day1_PracticeLab.UserProfile, String> formatter = practiceLab.getUserFormatterFunction();
        Day1_PracticeLab.UserProfile user = new Day1_PracticeLab.UserProfile("raman", "raman@dev.com", 25, true);

        String result = formatter.apply(user);
        assertEquals("RAMAN <RAMAN@DEV.COM>", result);
    }

    @Test
    @DisplayName("Task 3: Should supply default guest user")
    void testGuestUserSupplier() {
        Supplier<Day1_PracticeLab.UserProfile> supplier = practiceLab.getGuestUserSupplier();
        Day1_PracticeLab.UserProfile guest = supplier.get();

        assertNotNull(guest);
        assertEquals("guest_user", guest.username());
        assertEquals("guest@system.com", guest.email());
        assertTrue(guest.active());
    }
}
