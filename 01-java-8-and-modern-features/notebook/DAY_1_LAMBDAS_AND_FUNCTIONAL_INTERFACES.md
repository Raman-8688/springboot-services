# 📘 Day 1 Notebook: Lambdas & Functional Interfaces (Senior Architect Guide)

> **Mentor Note**: "Welcome to Day 1! In enterprise Java applications, Lambda expressions are not just 'shorter code'—they represent a fundamental shift from Imperative Programming (HOW to do things) to Declarative Programming (WHAT to do). Under the hood, Java 8 lambdas use the `invokedynamic` bytecode instruction introduced in Java 7, which dynamically generates implementation classes at runtime without creating bulky anonymous inner class files on disk."

---

## 1. What is a Functional Interface?

### 🗣️ 30-Second Interview Answer
> "A **Functional Interface** is an interface that contains **exactly one abstract method** (Single Abstract Method - SAM). It can contain any number of `default` or `static` methods. We annotate it with `@FunctionalInterface` so the compiler enforces the SAM rule."

### 💡 Real-World Analogy
Think of a **Single Button Remote Control**.
* A remote control with 10 buttons is complicated (Standard Interface).
* A single red panic button has **ONLY 1 job**: press it to trigger an alert. That single button is a Functional Interface!

---

## 2. The Core 4 Built-In Functional Interfaces

Java 8 provides 4 core functional interfaces in `java.util.function`. Memorize these for life:

```
                  ┌─────────────────────────────────────┐
                  │ 1. Predicate<T>                     │
                  │ Input: T  ──► Output: boolean       │
                  │ Method: test(T t)                   │
                  └─────────────────────────────────────┘
                                     │
                  ┌──────────────────┴──────────────────┐
                  ▼                                     ▼
┌───────────────────────────────────┐ ┌───────────────────────────────────┐
│ 2. Function<T, R>                 │ │ 3. Consumer<T>                    │
│ Input: T  ──► Output: R           │ │ Input: T  ──► Output: void        │
│ Method: apply(T t)                │ │ Method: accept(T t)               │
└───────────────────────────────────┘ └───────────────────────────────────┘
                                     │
                                     ▼
                  ┌─────────────────────────────────────┐
                  │ 4. Supplier<T>                      │
                  │ Input: None ──► Output: T           │
                  │ Method: get()                       │
                  └─────────────────────────────────────┘
```

### Detailed Breakdown

#### 1. `Predicate<T>` (The Condition Checker)
- **Signature**: `boolean test(T t)`
- **When to use**: Filtering items, validating conditions in `filter()`.
- **Example**:
  ```java
  Predicate<Employee> isHighEarner = emp -> emp.getSalary() > 100000;
  boolean result = isHighEarner.test(myEmployee);
  ```

#### 2. `Function<T, R>` (The Transformer)
- **Signature**: `R apply(T t)`
- **When to use**: Data transformation in `map()`, DTO mapping.
- **Example**:
  ```java
  Function<Employee, UserDto> toDto = emp -> new UserDto(emp.getId(), emp.getName());
  UserDto dto = toDto.apply(myEmployee);
  ```

#### 3. `Consumer<T>` (The Action Executor)
- **Signature**: `void accept(T t)`
- **When to use**: Side effects like printing, logging, saving to DB in `forEach()`.
- **Example**:
  ```java
  Consumer<String> logger = msg -> System.out.println("[LOG] " + msg);
  logger.accept("Order processed successfully");
  ```

#### 4. `Supplier<T>` (The Object Factory)
- **Signature**: `T get()`
- **When to use**: Lazy evaluation, object creation in `Optional.orElseGet()`.
- **Example**:
  ```java
  Supplier<User> defaultUserSupplier = () -> new User("Guest", "guest@company.com");
  User guest = defaultUserSupplier.get();
  ```

---

## 3. Method References (`Class::method`)

Method references are shorthand syntaxes for lambdas that execute existing methods.

| Lambda Syntax | Method Reference Equivalent | Type |
| :--- | :--- | :--- |
| `str -> str.toUpperCase()` | `String::toUpperCase` | Instance Method of an Arbitrary Object |
| `str -> System.out.println(str)` | `System.out::println` | Instance Method of a Specific Object |
| `str -> Integer.parseInt(str)` | `Integer::parseInt` | Static Method |
| `() -> new ArrayList<>()` | `ArrayList::new` | Constructor Reference |

---

## 4. Custom Functional Interfaces & `@FunctionalInterface`

You can create your own functional interface for custom business logic:

```java
@FunctionalInterface
public interface DiscountCalculator {
    double calculate(double originalPrice, double discountPercentage);
    
    // Default method (does NOT break SAM rule!)
    default void printInfo() {
        System.out.println("Calculating enterprise discount...");
    }
}
```

---

## ❓ Day 1 MNC Interview Questions & Answers

### Q1: Can a `@FunctionalInterface` inherit from another interface?
* **Answer**: YES, as long as the total number of un-implemented abstract methods in the child interface remains **EXACTLY ONE**. If parent has 1 abstract method and child adds another, child is NO LONGER a functional interface.

### Q2: What is the difference between Lambda Expression and Anonymous Inner Class regarding `this` keyword?
* **Answer**:
  - In an **Anonymous Inner Class**, `this` refers to the anonymous inner class object itself.
  - In a **Lambda Expression**, `this` refers to the enclosing outer class object (Lexical Scoping).

### Q3: How does Java 8 handle Primitive Functional Interfaces to avoid Auto-boxing overhead?
* **Answer**: Auto-boxing between `Integer` and `int` causes CPU overhead. Java 8 provides specialized primitive functional interfaces: `IntPredicate`, `DoubleFunction`, `LongConsumer`, `IntSupplier` to avoid boxing cost!

---

## 📝 Day 1 Practice Checklist
- [ ] Read this notebook carefully.
- [ ] Inspect [`Day1_Lambda_Reference.java`](file:///d:/springboot-services/01-java-8-and-modern-features/learning-reference/src/main/java/com/mastery/java8/day1/Day1_Lambda_Reference.java).
- [ ] Solve [`Day1_PracticeLab.java`](file:///d:/springboot-services/01-java-8-and-modern-features/practice-lab/src/main/java/com/mastery/java8/practice/day1/Day1_PracticeLab.java) and run `mvn test`.
