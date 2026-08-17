package com.mastery.java8.practice.day1;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Day1_PracticeLab {

    public record UserProfile(String username, String email, int age, boolean active) {}

    /**
     * TASK 1: Create a Predicate that checks if a user is ACTIVE AND at least 18 years old.
     */
    public Predicate<UserProfile> getAdultActiveUserPredicate() {
        // TODO: Return predicate evaluating active == true AND age >= 18
        return user -> user.active() && user.age() >= 18;
    }

    /**
     * TASK 2: Create a Function that formats a UserProfile into an uppercase email format "USERNAME <EMAIL>".
     */
    public Function<UserProfile, String> getUserFormatterFunction() {
        // TODO: Return function transforming user into "USERNAME <EMAIL>" (e.g. "RAMAN <RAMAN@COMPANY.COM>")
        return user -> String.format("%s <%s>", user.username().toUpperCase(), user.email().toUpperCase());
    }

    /**
     * TASK 3: Create a Supplier that returns a default guest UserProfile ("guest_user", "guest@system.com", 20, true).
     */
    public Supplier<UserProfile> getGuestUserSupplier() {
        // TODO: Return supplier instantiating default guest user
        return () -> new UserProfile("guest_user", "guest@system.com", 20, true);
    }
}
