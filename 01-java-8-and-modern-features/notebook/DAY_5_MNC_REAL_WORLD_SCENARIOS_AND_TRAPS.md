# 5️⃣ Day 5 Notebook: MNC Production Scenarios & Streams Capstone

> **Senior Architect Note**: "Congratulations on reaching Day 5! In MNC technical architecture interviews, interviewers test your ability to combine multiple Stream operations into clean pipelines to solve complex business queries. Today we tackle complex multi-level aggregations, revenue analytics, and performance trade-offs."

---

## 1. Complex Multi-Level Grouping

### Scenario: Group Orders by Category, then by Status, and sum total revenue for each group.

```java
Map<String, Map<String, Double>> revenueByCategoryAndStatus = orders.stream()
    .collect(Collectors.groupingBy(
        Order::category,
        Collectors.groupingBy(
            Order::status,
            Collectors.summingDouble(Order::totalAmount)
        )
    ));
```

---

## 2. Streams vs Traditional Loops: Performance & Readability Matrix

| Feature | Streams API | Traditional `for` / `for-each` Loop |
| :--- | :--- | :--- |
| **Readability** | High (Declarative, intent is clear). | Medium/Low (Imperative, boilerplate counters). |
| **Performance (Small Collections < 100 items)** | Slightly slower (Stream overhead). | Faster (Direct memory access). |
| **Performance (Large Collections > 10,000 items)** | Fast & Parallelizable cleanly. | Fast sequentially, hard to parallelize cleanly. |
| **Debugging** | Intermediate peek / breakpoint in IDE. | Easy step-by-step variable inspection. |
| **Best Practice** | Use Streams for complex transformations/aggregations. | Use simple `for` loops for low-level performance-critical tight loops. |

---

## ❓ Day 5 MNC Capstone Interview Scenarios

### Scenario 1: Given a list of transactions, find the top 3 customers who spent the most money overall in the last 30 days.

```java
List<String> top3Customers = transactions.stream()
    .filter(t -> t.date().isAfter(LocalDate.now().minusDays(30)))
    .collect(Collectors.groupingBy(
        Transaction::customerName,
        Collectors.summingDouble(Transaction::amount)
    ))
    .entrySet().stream()
    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
    .limit(3)
    .map(Map.Entry::getKey)
    .toList();
```

---

## 📝 Day 5 Checklist
- [ ] Inspect [`Day5_MNC_Capstone_Reference.java`](file:///d:/springboot-services/01-java-8-and-modern-features/learning-reference/src/main/java/com/mastery/java8/day5/Day5_MNC_Capstone_Reference.java).
- [ ] Solve [`Day5_PracticeLab.java`](file:///d:/springboot-services/01-java-8-and-modern-features/practice-lab/src/main/java/com/mastery/java8/practice/day5/Day5_PracticeLab.java) and run `mvn test`.
