package com.mastery.java8.practice.day3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Day3_PracticeLabTest {

    private Day3_PracticeLab practiceLab;
    private List<Day3_PracticeLab.EmployeeRecord> employees;

    @BeforeEach
    void setUp() {
        practiceLab = new Day3_PracticeLab();
        employees = List.of(
            new Day3_PracticeLab.EmployeeRecord("E1", "Raman", "Engineering", 120000.0),
            new Day3_PracticeLab.EmployeeRecord("E2", "Anitha", "Engineering", 110000.0),
            new Day3_PracticeLab.EmployeeRecord("E3", "Kiran", "HR", 70000.0),
            new Day3_PracticeLab.EmployeeRecord("E1", "Raman Duplicate", "Engineering", 140000.0) // Duplicate ID E1 with higher salary
        );
    }

    @Test
    @DisplayName("Task 1: Should correctly calculate average salary per department")
    void testAverageSalaryByDepartment() {
        Map<String, Double> avgSalary = practiceLab.getAverageSalaryByDepartment(employees);
        assertEquals(123333.333, avgSalary.get("Engineering"), 0.01);
        assertEquals(70000.0, avgSalary.get("HR"), 0.01);
    }

    @Test
    @DisplayName("Task 2: Should correctly partition employees by salary threshold")
    void testPartitionBySalaryThreshold() {
        Map<Boolean, List<Day3_PracticeLab.EmployeeRecord>> partitioned = 
            practiceLab.partitionBySalaryThreshold(employees, 100000.0);
        
        assertEquals(3, partitioned.get(true).size());
        assertEquals(1, partitioned.get(false).size());
    }

    @Test
    @DisplayName("Task 3: Should handle duplicate keys in toMap keeping higher salary employee")
    void testMapOfIdToHighestSalaryEmployee() {
        Map<String, Day3_PracticeLab.EmployeeRecord> map = practiceLab.getMapOfIdToHighestSalaryEmployee(employees);
        assertEquals(3, map.size());
        assertEquals("Raman Duplicate", map.get("E1").name());
        assertEquals(140000.0, map.get("E1").salary());
    }
}
