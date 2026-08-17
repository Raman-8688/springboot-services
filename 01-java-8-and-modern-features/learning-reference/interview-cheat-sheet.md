# ☕ Java 8 & Modern Java Features: Anti-Forgetfulness Interview Cheat-Sheet

---

## 1. Streams API vs Collections

### 🗣️ 30-Second Interview Answer
> "A **Collection** is an in-memory data structure that holds data (like a list of items on a shelf). A **Stream** is a pipeline of computations that processes data on-demand (like a conveyor belt in a factory). Streams do not store data, do not modify the original source, and process elements lazily."

### 💡 Real-World Analogy
* **Collection**: A bucket of water holding 10 liters.
* **Stream**: A pipe where water flows through filters, one drop at a time, to purify it.

### ❓ High-Frequency Interview Questions

#### Q1: What is the difference between `map()` and `flatMap()`?
* **Simple Answer**:
  * `map()` converts **1 item into 1 item** (1-to-1 transformation). Example: `String -> String.toUpperCase()`.
  * `flatMap()` converts **1 item into a stream of items and flattens them into a single list** (1-to-N transformation). Example: A list of lists `[[1,2], [3,4]]` becomes `[1,2,3,4]`.

```java
// map() example: List<String> names -> List<Integer> lengths
List<Integer> lengths = names.stream()
    .map(String::length)
    .collect(Collectors.toList());

// flatMap() example: List<Customer> where each customer has List<String> phoneNumbers
List<String> allPhoneNumbers = customers.stream()
    .flatMap(c -> c.getPhoneNumbers().stream())
    .collect(Collectors.toList());
```

#### Q2: What is the difference between Intermediate and Terminal Operations?
* **Intermediate Operations** (`filter`, `map`, `sorted`): Return a new `Stream`. They are **lazy** and execute ONLY when a terminal operation is called.
* **Terminal Operations** (`collect`, `forEach`, `reduce`, `count`, `findFirst`): Produce a result or side-effect and **close the stream pipeline**.

#### Q3: How does `Collectors.groupingBy()` work?
* Groups stream items by a key, returning a `Map<K, List<V>>`.
* **Example**: Group employees by department name.

```java
Map<String, List<Employee>> empByDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));
```

---

## 2. Functional Interfaces & Lambda Expressions

### 🗣️ 30-Second Interview Answer
> "A **Functional Interface** is an interface that has **exactly one abstract method** (SAM). Lambda expressions provide a clean, concise way to implement that single abstract method without creating anonymous inner classes."

### 💡 The Core 4 Functional Interfaces to Remember Forever

| Interface | Method Signature | Inputs -> Output | Simple Analogy | Real Example |
| :--- | :--- | :--- | :--- | :--- |
| **`Predicate<T>`** | `boolean test(T t)` | Takes 1 item -> returns `boolean` | Quality Check Inspector | `num -> num % 2 == 0` |
| **`Function<T, R>`** | `R apply(T t)` | Takes 1 item -> returns transform result | Converter Machine | `str -> Integer.parseInt(str)` |
| **`Consumer<T>`** | `void accept(T t)` | Takes 1 item -> returns `void` | Trash Can / Printer | `item -> System.out.println(item)` |
| **`Supplier<T>`** | `T get()` | Takes NOTHING -> produces 1 item | Vending Machine | `() -> new Order()` |

---

## 3. `Optional<T>` Best Practices & Traps

### 🗣️ 30-Second Interview Answer
> "`Optional` is a wrapper container used to avoid `NullPointerException` (NPE) and explicitly signal to caller methods that a value may or may not be present. Never use `Optional.get()` directly without checking `isPresent()` or using `orElse()` / `orElseGet()`."

### ⚠️ Common Interview Trap: `orElse()` vs `orElseGet()`
* `orElse(defaultValue)`: Evaluates the default value **ALWAYS**, even if the `Optional` is NOT empty!
* `orElseGet(() -> defaultValue)`: Evaluates the default value **LAZILY** only if the `Optional` IS empty.

```java
// BAD: DB call runs even if user exists!
User user = optionalUser.orElse(fetchDefaultUserFromDB()); 

// GOOD: DB call runs ONLY if user is missing!
User user = optionalUser.orElseGet(() -> fetchDefaultUserFromDB()); 
```

---

## 4. Modern Java (Java 17 & Java 21) Quick Reference

### 1. `record` (Java 14+)
Immutable data carriers that auto-generate constructors, getters, `equals()`, `hashCode()`, and `toString()`.
```java
public record UserDto(Long id, String name, String email) {}
```

### 2. Pattern Matching for `switch` (Java 21)
Clean type checks inside switch statements:
```java
String result = switch (obj) {
    case Integer i -> "Integer: " + (i * 2);
    case String s  -> "String length: " + s.length();
    case null      -> "Null object!";
    default        -> "Unknown type";
};
```

---

## 🎯 3-Minute Pre-Interview Practice Summary
1. **Stream Pipeline**: Source -> Intermediate Operations (Lazy) -> Terminal Operation (Trigger).
2. **`flatMap`**: Flattens nested collections (1-to-N).
3. **`orElseGet`**: Lazy evaluation for default fallback (avoids extra method execution).
4. **`Predicate` (boolean), `Function` (transform), `Consumer` (action), `Supplier` (produce)**.
