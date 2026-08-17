# 📘 Day 3 Notebook: Advanced `Collectors` Deep Dive

> **Senior Architect Note**: "In enterprise application development, 80% of data aggregation queries (grouping sales by region, calculating average customer spend, partitioning active vs inactive accounts) are solved elegantly using `Collectors`. Master `groupingBy` with downstream collectors and you will stand out in any MNC code review."

---

## 1. The Anatomy of `Collectors.groupingBy()`

`groupingBy` comes in 3 overloaded flavors:

### Flavor 1: Simple Grouping
Returns `Map<K, List<V>>`.
```java
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));
```

### Flavor 2: Grouping with Downstream Collector
Returns `Map<K, SummaryResult>`.
```java
// Total salary sum per department
Map<String, Double> totalSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.summingDouble(Employee::getSalary)
    ));
```

### Flavor 3: Grouping with Downstream Collector & Custom Map Implementation
Returns a specific Map implementation (like `TreeMap` for sorted keys).
```java
Map<String, Long> countByDeptSorted = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        TreeMap::new, // Keep keys sorted alphabetically!
        Collectors.counting()
    ));
```

---

## 2. `Collectors.partitioningBy()` vs `groupingBy()`

* **`partitioningBy`**: A specialized form of grouping that takes a `Predicate` and ALWAYS splits data into a `Map<Boolean, List<V>>` with exactly 2 keys: `true` and `false`.
* **When to use**: Splitting active vs inactive users, passing vs failing test scores.

```java
Map<Boolean, List<Student>> passFailMap = students.stream()
    .collect(Collectors.partitioningBy(s -> s.getScore() >= 60));

List<Student> passedStudents = passFailMap.get(true);
List<Student> failedStudents = passFailMap.get(false);
```

---

## 3. `Collectors.toMap()` Duplicate Key Trap

When converting a Stream to a Map, if two elements generate the SAME key, `Collectors.toMap(keyMapper, valueMapper)` throws an `IllegalStateException: Duplicate key`!

### ⚠️ How to fix Duplicate Key Exceptions:
Supply a **Merge Function** `(existingValue, replacementValue) -> newValue`:

```java
// Handle duplicate employee IDs by keeping the one with higher salary
Map<Long, Employee> uniqueEmpMap = employees.stream()
    .collect(Collectors.toMap(
        Employee::getId,
        Function.identity(),
        (existing, replacement) -> existing.getSalary() > replacement.getSalary() ? existing : replacement
    ));
```

---

## ❓ Day 3 MNC Interview Questions & Answers

### Q1: How do you find the highest paid employee in EACH department using Streams?
```java
Map<String, Optional<Employee>> topEarnerByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))
    ));
```
* **Follow-up Tip**: To unwrap the `Optional` directly inside the collector, wrap it in `Collectors.collectingAndThen()`!

---

## 📝 Day 3 Checklist
- [ ] Inspect [`Day3_Collectors_Reference.java`](file:///d:/springboot-services/01-java-8-and-modern-features/learning-reference/src/main/java/com/mastery/java8/day3/Day3_Collectors_Reference.java).
- [ ] Solve [`Day3_PracticeLab.java`](file:///d:/springboot-services/01-java-8-and-modern-features/practice-lab/src/main/java/com/mastery/java8/practice/day3/Day3_PracticeLab.java) and run `mvn test`.
