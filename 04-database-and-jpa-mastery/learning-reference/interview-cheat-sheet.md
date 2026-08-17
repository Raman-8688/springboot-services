# 🗄️ Database & JPA Mastery: Anti-Forgetfulness Interview Cheat-Sheet

---

## 1. Database Indexing (Top MNC Interview Question)

### 🗣️ 30-Second Interview Answer
> "An **Index** is a data structure (typically a **B-Tree**) that speeds up data retrieval on a database table at the cost of slower write performance (`INSERT`, `UPDATE`, `DELETE`) and extra storage. Indexing allows the DB engine to jump straight to rows in **O(log N)** time instead of scanning every row (**Full Table Scan**)."

### 💡 Real-World Analogy
* **Without Index**: Reading a 500-page book page-by-page to find the word "Kafka".
* **With Index**: Looking up "Kafka" in the book's index at the back to jump directly to page 240.

---

### 🔍 Types of Indexes to Remember
1. **Primary Index / Clustered Index**: Automatically created on Primary Key. Rows are physically ordered on disk by this index. (Only 1 per table!).
2. **Secondary / Non-Clustered Index**: Separate index structure holding index keys and pointers to the physical table rows.
3. **Composite Index**: An index on multiple columns `(department_id, salary)`.
   - **Interview Rule (Leftmost Prefix Rule)**: A composite index `(A, B, C)` will be used for queries filtering on `(A)`, `(A, B)`, or `(A, B, C)`. It will **NOT** be used if you query only on `(B)` or `(C)`!

---

## 2. The N+1 Query Problem in JPA / Hibernate

### 🗣️ 30-Second Interview Answer
> "The **N+1 Query Problem** occurs when loading a parent entity with a `@OneToMany` or `@ManyToOne` relationship executing 1 query for the parent, and then Hibernate fires **N separate queries** to fetch child entities for each of the N parent rows. This degrades performance severely. It is fixed using **`JOIN FETCH`**, **`@EntityGraph`**, or **`BatchSize`**."

### 💡 Real-World Analogy
* **N+1 Problem**: A teacher calls 30 students to the office one-by-one with 30 separate trips instead of announcing all 30 names at once in one announcement.

---

### 🛠️ 3 Solutions to Fix N+1 Query Problem

#### Solution 1: `JOIN FETCH` in JPQL
```java
@Query("SELECT c FROM Customer c JOIN FETCH c.orders")
List<Customer> findAllCustomersWithOrders();
```

#### Solution 2: `@EntityGraph` in Spring Data JPA
```java
@EntityGraph(attributePaths = {"orders"})
List<Customer> findAll();
```

#### Solution 3: `@BatchSize` (Hibernate Annotation)
```java
@OneToMany(mappedBy = "customer")
@BatchSize(size = 20)
private List<Order> orders;
```

---

## 3. Database Transactions & ACID Properties

| Property | Meaning | Real-World Example |
| :--- | :--- | :--- |
| **Atomicity** | All operations succeed, or all roll back (All-or-Nothing). | Money deducted from Sender MUST land in Receiver account; if receiver fails, sender money is restored. |
| **Consistency** | Data must move from one valid state to another, preserving constraints. | Account balance cannot become negative if DB constraint forbids it. |
| **Isolation** | Concurrent transactions execute without interfering with each other. | Two people withdrawing from the same joint account simultaneously see isolated balances. |
| **Durability** | Once committed, data survives system crashes/power outages. | Committed transaction written to disk Write-Ahead Log (WAL). |

---

### ⚠️ `@Transactional` Interview Traps

#### Trap 1: Self-Invocation Problem
Calling a `@Transactional` method from another method **inside the same class** bypasses the Spring AOP Proxy! The transaction will NOT be opened.
```java
public void placeOrder() {
    // BUG: Self-invocation! Spring AOP proxy is bypassed!
    this.saveToDatabase(); 
}

@Transactional
public void saveToDatabase() { ... }
```
* **Fix**: Move `saveToDatabase()` to a separate Spring `@Service` component or use `AopContext.currentProxy()`.

#### Trap 2: Checked Exceptions don't rollback by default!
By default, `@Transactional` rolls back ONLY on `RuntimeException` and `Error`. It does **NOT** roll back on checked exceptions (`IOException`, `SQLException`).
* **Fix**: Use `@Transactional(rollbackFor = Exception.class)`.

---

## 🎯 3-Minute Pre-Interview Practice Summary
1. **Leftmost Prefix Rule**: Composite Index `(A,B)` works for `WHERE A=?` but NOT `WHERE B=?`.
2. **N+1 Query Fix**: Use `JOIN FETCH` or `@EntityGraph`.
3. **`@Transactional` Self-Invocation**: Internal method calls bypass AOP proxy.
4. **Transaction Rollback**: Default rollback applies to `RuntimeException` only (add `rollbackFor = Exception.class`).
