# 4️⃣ Day 4 Notebook: `Optional` API, Parallel Streams & `Spliterator`

> **Senior Architect Note**: "Parallel Streams are often misused as a 'magical speedup button' by junior developers. In reality, parallel streams use the shared `ForkJoinPool.commonPool()`. If you execute a blocking I/O call inside a parallel stream, you risk **starving the entire JVM's common thread pool**! Use parallel streams ONLY for CPU-bound computations on large in-memory collections."

---

## 1. `Optional<T>` API: Master Techniques & Traps

### ⚠️ Trap: `orElse()` vs `orElseGet()`
```java
// BAD: DB query or expensive method ALWAYS runs, even if optional is PRESENT!
User user = optionalUser.orElse(fetchUserFromDatabase()); 

// GOOD: DB query runs LAZILY only if optional is EMPTY!
User user = optionalUser.orElseGet(() -> fetchUserFromDatabase());
```

### 💡 Chaining `Optional` with `flatMap()`
When a method returns an `Optional<T>`, calling `map()` results in `Optional<Optional<T>>`! Use `flatMap()` to unwrap nested Optionals cleanly:

```java
public record ZipCode(String code) {}
public record Address(Optional<ZipCode> zipCode) {}
public record Customer(Optional<Address> address) {}

// Chaining Optionals safely without NullPointerExceptions!
String zipCodeStr = customer.getAddress()
    .flatMap(Address::zipCode)
    .map(ZipCode::code)
    .orElse("00000");
```

---

## 2. Parallel Streams & `ForkJoinPool`

```java
List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000).boxed().toList();

// Runs across multiple worker threads in ForkJoinPool.commonPool()
long sum = numbers.parallelStream()
    .mapToLong(Integer::longValue)
    .sum();
```

### ⚠️ When NOT to use Parallel Streams:
1. **Small Datasets**: Thread coordination & splitting overhead outweighs sequential execution speed.
2. **Blocking I/O Calls**: HTTP requests, DB queries, file I/O block common pool threads.
3. **Stateful / Non-Thread-Safe Operations**: Mutating a shared `ArrayList` inside `parallelStream().forEach()` causes race conditions and lost elements!

---

## 3. How `Spliterator` Works

Every Stream source implements `Spliterator<T>` (Splitable Iterator).
* `tryAdvance(Consumer)`: Processes a single element (like `iterator.next()`).
* `trySplit()`: Splits the current dataset in half to create a new `Spliterator` for another parallel thread in `ForkJoinPool`!

---

## ❓ Day 4 MNC Interview Questions & Answers

### Q1: Is `ArrayList` or `LinkedList` better for Parallel Streams?
* **Answer**: **`ArrayList` is drastically better!** `ArrayList` supports random access and can be split cleanly in **O(1)** time using index ranges `(start, mid, end)`. `LinkedList` requires **O(N)** traversal to find the split midpoint, ruining parallel performance.

---

## 📝 Day 4 Checklist
- [ ] Inspect [`Day4_Optional_Parallel_Reference.java`](file:///d:/springboot-services/01-java-8-and-modern-features/learning-reference/src/main/java/com/mastery/java8/day4/Day4_Optional_Parallel_Reference.java).
- [ ] Solve [`Day4_PracticeLab.java`](file:///d:/springboot-services/01-java-8-and-modern-features/practice-lab/src/main/java/com/mastery/java8/practice/day4/Day4_PracticeLab.java) and run `mvn test`.
