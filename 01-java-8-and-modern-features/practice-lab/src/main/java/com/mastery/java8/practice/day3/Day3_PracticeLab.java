package com.mastery.java8.practice.day3;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3_PracticeLab {

    public record EmployeeRecord(String id, String name, String department, double salary) {}

    /**
     * TASK 1: Calculate the average salary for each department.
     */
    public Map<String, Double> getAverageSalaryByDepartment(List<EmployeeRecord> employees) {
        // TODO: Use groupingBy department and averagingDouble salary
        return employees.stream()
            .collect(Collectors.groupingBy(
                EmployeeRecord::department,
                Collectors.averagingDouble(EmployeeRecord::salary)
            ));
    }

    /**
     * TASK 2: Partition employees into high earners (salary >= 100,000) vs regular earners.
     */
    public Map<Boolean, List<EmployeeRecord>> partitionBySalaryThreshold(List<EmployeeRecord> employees, double threshold) {
        // TODO: Use partitioningBy salary >= threshold
        return employees.stream()
            .collect(Collectors.partitioningBy(e -> e.salary() >= threshold));
    }

    /**
     * TASK 3: Map employee ID to Employee object, handling duplicate IDs by keeping the employee with the higher salary.
     */
    public Map<String, EmployeeRecord> getMapOfIdToHighestSalaryEmployee(List<EmployeeRecord> employees) {
        // TODO: Use toMap with merge function (e1, e2) -> higher salary employee
        return employees.stream()
            .collect(Collectors.toMap(
                EmployeeRecord::id,
                Function.identity(),
                (e1, e2) -> e1.salary() >= e2.salary() ? e1 : e2
            ));
    }
}
