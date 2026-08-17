# 📘 Day 2 Notebook: Streams Fundamentals & The Lazy Execution Engine

> **Senior Architect Note**: "Many developers think a Stream processes data in loops like `for-each` where step 1 completes for ALL elements before step 2 starts. **This is completely wrong!** The Streams API processes elements **vertically (element-by-element)** through the pipeline. An element flows through filter -> map -> collect before the second element even enters the filter! This is called **Lazy Execution Engine**."

---

## 1. Stream Execution Architecture (Vertical Pipeline Processing)

Consider this code:
```java
List<String> names = List.of("Raman", "Anitha", "Kiran", "Vijay");

names.stream()
    .filter(name -> {
        System.out.println("Filter: " + name);
        return name.startsWith("A");
    })
    .map(name -> {
        System.out.println("Map: " + name);
        return name.toUpperCase();
    })
    .findFirst();
```

### 🧠 Output Execution Trace:
```text
Filter: Raman   <-- Does NOT start with 'A', skipped!
Filter: Anitha  <-- Matches 'A'! Passed to map!
Map: Anitha     <-- Transformed to ANITHA! Passed to findFirst!
findFirst() returns "ANITHA" and STOP!
```
Notice that `"Kiran"` and `"Vijay"` were **NEVER EVEN CHECKED**! This is **Short-Circuiting Optimization**.

---

## 2. Intermediate vs Terminal Operations

| Category | Operations | Characteristics |
| :--- | :--- | :--- |
| **Intermediate (Stateless)** | `filter()`, `map()`, `flatMap()`, `peek()` | Takes 1 item, processes, passes down pipeline. Fast O(1) memory. |
| **Intermediate (Stateful)** | `distinct()`, `sorted()`, `limit()`, `skip()` | Must store internal state of previous items before passing down pipeline! |
| **Terminal (Collector)** | `collect()`, `toList()`, `toSet()`, `toMap()` | Accumulates items into a data structure and ends stream. |
| **Terminal (Reducer)** | `reduce()`, `count()`, `min()`, `max()` | Combines elements into a single value. |
| **Terminal (Short-circuiting)** | `findFirst()`, `findAny()`, `anyMatch()`, `allMatch()`, `noneMatch()` | Stops processing as soon as matching condition is satisfied! |

---

## ❓ Day 2 MNC Interview Questions & Answers

### Q1: Why does calling `stream.filter(...).map(...)` without a terminal operation do absolutely nothing?
* **Answer**: Intermediate operations build an internal **Pipeline Linked-List** of operation flags (`ReferencePipeline`). No processing begins until a **Terminal Operation** is invoked. This is called **Lazy Evaluation**.

### Q2: What is the difference between `findFirst()` and `findAny()` in Parallel Streams?
* **Answer**:
  - `findFirst()` guarantees returning the **first element in encounter order**, even across multiple threads in parallel processing (higher synchronization cost).
  - `findAny()` returns **whichever element is found first by any thread** (faster in parallel processing!).

---

## 📝 Day 2 Checklist
- [ ] Inspect [`Day2_Streams_Reference.java`](file:///d:/springboot-services/01-java-8-and-modern-features/learning-reference/src/main/java/com/mastery/java8/day2/Day2_Streams_Reference.java).
- [ ] Solve [`Day2_PracticeLab.java`](file:///d:/springboot-services/01-java-8-and-modern-features/practice-lab/src/main/java/com/mastery/java8/practice/day2/Day2_PracticeLab.java) and run `mvn test`.
