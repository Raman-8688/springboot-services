package com.mastery.java8.day4;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

public class Day4_Optional_Parallel_Reference {

    public record Department(String deptName, Optional<Employee> manager) {}
    public record Employee(String id, String name, double salary) {}

    public static void main(String[] args) {
        System.out.println("=== 1. Safe Optional Chaining with flatMap ===");
        Employee manager = new Employee("E101", "Raman Architect", 150000.0);
        Department deptWithManager = new Department("Core Platform", Optional.of(manager));
        Department deptWithoutManager = new Department("Research", Optional.empty());

        System.out.println("Dept 1 Manager Name: " + getManagerName(deptWithManager));
        System.out.println("Dept 2 Manager Name: " + getManagerName(deptWithoutManager));

        System.out.println("\n=== 2. Parallel Streams Performance Comparison ===");
        long n = 10_000_000L;

        long startTime = System.currentTimeMillis();
        long sequentialSum = LongStream.rangeClosed(1, n).sum();
        long sequentialTime = System.currentTimeMillis() - startTime;
        System.out.printf("Sequential Sum: %d (Time: %d ms)%n", sequentialSum, sequentialTime);

        startTime = System.currentTimeMillis();
        long parallelSum = LongStream.rangeClosed(1, n).parallel().sum();
        long parallelTime = System.currentTimeMillis() - startTime;
        System.out.printf("Parallel Sum:   %d (Time: %d ms)%n", parallelSum, parallelTime);

        System.out.println("ForkJoinPool Common Pool Parallelism: " + ForkJoinPool.getCommonPoolParallelism());
    }

    public static String getManagerName(Department department) {
        return Optional.ofNullable(department)
            .flatMap(Department::manager)
            .map(Employee::name)
            .orElse("NO MANAGER ASSIGNED");
    }
}
