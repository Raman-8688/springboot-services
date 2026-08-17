package com.mastery.java8.day1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class Day1_Lambda_Reference {

    public record Account(String accountNumber, String accountHolder, double balance, boolean active) {}

    @FunctionalInterface
    public interface RiskEvaluator {
        boolean evaluateRisk(Account account, double threshold);
    }

    public static void main(String[] args) {
        Account acc1 = new Account("ACC-101", "Raman", 150000.0, true);
        Account acc2 = new Account("ACC-102", "Kiran", 25000.0, true);
        Account acc3 = new Account("ACC-103", "Priya", 5000.0, false);

        List<Account> accounts = List.of(acc1, acc2, acc3);

        System.out.println("=== 1. Predicate Example: Filter Active Accounts ===");
        Predicate<Account> isActive = Account::active;
        Predicate<Account> isHighBalance = acc -> acc.balance() > 50000;
        
        // Combining predicates with .and()
        Predicate<Account> isActiveHighBalance = isActive.and(isHighBalance);
        
        accounts.stream()
            .filter(isActiveHighBalance)
            .forEach(acc -> System.out.println("Active High Balance: " + acc.accountHolder()));

        System.out.println("\n=== 2. Function Example: Transform Account to Summary String ===");
        Function<Account, String> toSummary = acc -> 
            String.format("[%s] %s -> Balance: $%.2f", acc.accountNumber(), acc.accountHolder(), acc.balance());

        accounts.stream()
            .map(toSummary)
            .forEach(System.out::println);

        System.out.println("\n=== 3. Consumer Example: Deposit Interest ===");
        Consumer<Account> logAudit = acc -> 
            System.out.println("AUDIT LOG: Processing account " + acc.accountNumber());

        accounts.forEach(logAudit);

        System.out.println("\n=== 4. Supplier Example: Generate Default Fallback Account ===");
        Supplier<Account> defaultAccountSupplier = () -> 
            new Account("ACC-999", "Guest User", 0.0, false);

        Account fallback = defaultAccountSupplier.get();
        System.out.println("Fallback Account: " + fallback);

        System.out.println("\n=== 5. Custom Functional Interface Example ===");
        RiskEvaluator highRiskEvaluator = (account, threshold) -> account.balance() < threshold || !account.active();
        
        boolean isAcc3Risky = highRiskEvaluator.evaluateRisk(acc3, 10000.0);
        System.out.println("Is Account 3 High Risk? " + isAcc3Risky);
    }
}
