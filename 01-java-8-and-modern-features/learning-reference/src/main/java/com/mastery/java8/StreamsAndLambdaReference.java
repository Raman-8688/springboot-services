package com.mastery.java8;

import java.util.*;
import java.util.stream.Collectors;

public class StreamsAndLambdaReference {

    public record Employee(Long id, String name, String department, double salary, int age) {}

    public static void main(String[] args) {
        List<Employee> employees = List.of(
            new Employee(1L, "Raman", "Engineering", 95000, 26),
            new Employee(2L, "Anitha", "Engineering", 110000, 29),
            new Employee(3L, "Kiran", "HR", 65000, 31),
            new Employee(4L, "Priya", "Finance", 85000, 27),
            new Employee(5L, "Vijay", "Engineering", 125000, 33)
        );

        System.out.println("--- 1. Filter High Salary Employees in Engineering ---");
        List<Employee> highEarnersEng = employees.stream()
            .filter(e -> "Engineering".equalsIgnoreCase(e.department()))
            .filter(e -> e.salary() > 90000)
            .collect(Collectors.toList());
        highEarnersEng.forEach(System.out::println);

        System.out.println("\n--- 2. Grouping Employees by Department ---");
        Map<String, List<Employee>> byDept = employees.stream()
            .collect(Collectors.groupingBy(Employee::department));
        byDept.forEach((dept, empList) -> System.out.println(dept + " -> " + empList.size() + " employees"));

        System.out.println("\n--- 3. Find Employee with Maximum Salary ---");
        Optional<Employee> topEarner = employees.stream()
            .max(Comparator.comparingDouble(Employee::salary));
        topEarner.ifPresent(e -> System.out.println("Top Earner: " + e.name() + " ($" + e.salary() + ")"));

        System.out.println("\n--- 4. Calculate Average Salary per Department ---");
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::department,
                Collectors.averagingDouble(Employee::salary)
            ));
        avgSalaryByDept.forEach((dept, avgSal) -> System.out.printf("%s Avg Salary: $%.2f%n", dept, avgSal));
    }
}
