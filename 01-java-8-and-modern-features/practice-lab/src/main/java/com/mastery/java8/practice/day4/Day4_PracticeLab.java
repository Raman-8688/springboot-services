package com.mastery.java8.practice.day4;

import java.util.List;
import java.util.Optional;

public class Day4_PracticeLab {

    public record AccountInfo(String accountId, Optional<String> email) {}
    public record UserProfile(String userId, Optional<AccountInfo> accountInfo) {}

    /**
     * TASK 1: Extract email safely from nested Optional<UserProfile> -> AccountInfo -> Email. Default to "NO_EMAIL_PROVIDED".
     */
    public String extractUserEmailSafely(Optional<UserProfile> userProfile) {
        // TODO: Chain optionals using flatMap and map, fallback to "NO_EMAIL_PROVIDED"
        return userProfile
            .flatMap(UserProfile::accountInfo)
            .flatMap(AccountInfo::email)
            .orElse("NO_EMAIL_PROVIDED");
    }

    /**
     * TASK 2: Sum prices of a list of numbers using parallelStream safely.
     */
    public long sumLargeListOfNumbersParallel(List<Long> numbers) {
        // TODO: Use parallelStream with mapToLong and sum
        return numbers.parallelStream()
            .mapToLong(Long::longValue)
            .sum();
    }
}
