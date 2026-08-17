# 🧠 JVM Memory Management & Multithreading: Anti-Forgetfulness Interview Cheat-Sheet

---

## 1. JVM Memory Structure (Heap, Stack & Metaspace)

### 🗣️ 30-Second Interview Answer
> "The JVM memory is divided into **Stack** and **Heap** memory:
> - **Stack Memory**: Thread-private memory that stores method call frames, local variables, and object reference addresses. Allocated and deallocated automatically on method invocation and return.
> - **Heap Memory**: Shared across all threads. Stores actual object instances and arrays. Managed by the Garbage Collector.
> - **Metaspace**: Stores class metadata, bytecode, and method definitions in native memory (replaced PermGen in Java 8)."

### 💡 Real-World Analogy
* **Stack**: A stack of sticky notes on your desk while working on a task. As soon as the task finishes, you crumple the note and throw it away.
* **Heap**: The big storage warehouse behind your office where actual physical equipment (Objects) is kept.
* **Garbage Collector**: The cleanup crew that visits the warehouse to clear out broken/unused equipment.

---

### 🔍 Heap Memory Breakdown & GC Generations

```
Heap Memory
├── Young Generation (Short-lived objects)
│   ├── Eden Space (Where new objects are born)
│   ├── Survivor 0 (S0 / From Space)
│   └── Survivor 1 (S1 / To Space)
└── Old Generation (Tenured / Long-lived objects)
```

1. **Minor GC**: Collects dead objects from Young Generation (Eden -> S0 -> S1). Fast, low latency.
2. **Major / Full GC**: Collects dead objects from Old Generation and Metaspace. Causes "Stop-The-World" pauses.
3. **Garbage Collectors**:
   - **G1GC** (Garbage First): Default in Java 9+. Divides heap into equal regions.
   - **ZGC** (Z Garbage Collector): Scalable ultra-low latency GC (pause times < 1ms, supports terabyte heaps).

---

### ⚠️ Top 4 Memory Leak Causes in Java

1. **Static Collections**: Adding objects to `static List` or `static Map` without removing them keeps references alive forever in Heap.
2. **Unclosed Connection / Stream Resources**: Failing to close DB Connections, Sockets, or I/O Streams. (Fix: Use `try-with-resources`).
3. **Improper `ThreadLocal` Cleanup**: `ThreadLocal` variables bound to thread pool worker threads persist even after HTTP request completion. Must call `.remove()`.
4. **Overriding `equals()` / `hashCode()` Incorrectly**: Inserting items into `HashSet` or `HashMap` with changing hashCodes makes objects unfindable and impossible to remove.

---

## 2. Multithreading & Concurrency Concepts

### 🗣️ 30-Second Interview Answer
> "A **Thread** is the smallest unit of execution inside a process. Java threads map 1-to-1 to OS native threads. Modern Java applications manage threads using **ExecutorService** and **ThreadPools** to avoid the high overhead of thread creation/destruction."

---

### 💡 `volatile` Keyword vs `synchronized`

| Feature | `volatile` | `synchronized` |
| :--- | :--- | :--- |
| **Visibility** | Guarantees changes are immediately written to & read from main RAM (bypasses CPU L1/L2 cache). | Guarantees RAM visibility. |
| **Atomicity** | ❌ NO atomicity for compound ops (e.g., `count++` is NOT thread-safe with `volatile`). | ✅ Guarantees atomicity (only 1 thread inside block). |
| **Blocking** | Non-blocking. | Blocks other waiting threads. |

---

### ⚙️ `ThreadPoolExecutor` Core Parameters (High-Frequency Interview Question)

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    corePoolSize,     // 1. Min threads kept alive
    maximumPoolSize,  // 2. Max threads created under heavy load
    keepAliveTime,    // 3. Idle time before destroying extra threads above core
    TimeUnit.SECONDS,
    new ArrayBlockingQueue<>(capacity), // 4. Queue for waiting tasks
    new ThreadPoolExecutor.CallerRunsPolicy() // 5. Rejection strategy when full
);
```

#### How ThreadPool handles incoming tasks:
1. If running threads < `corePoolSize`: Creates a new core thread.
2. If running threads >= `corePoolSize`: Adds task to `workQueue`.
3. If `workQueue` is FULL and threads < `maximumPoolSize`: Creates a new non-core thread.
4. If `workQueue` is FULL and threads == `maximumPoolSize`: Triggers **Rejection Execution Handler** (e.g., `AbortPolicy`, `CallerRunsPolicy`).

---

## 3. Java 21 Virtual Threads (Project Loom)

### 🗣️ 30-Second Interview Answer
> "**Virtual Threads** are lightweight threads managed by the JVM runtime rather than the OS kernel. Unlike platform threads (which take ~1MB stack and limited to a few thousand), millions of virtual threads can run concurrently. When a virtual thread performs blocking I/O (like DB or HTTP call), the JVM unmounts it from the underlying carrier OS thread, allowing another virtual thread to run."

```java
// Create a virtual thread per task
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    IntStream.range(0, 10_000).forEach(i -> {
        executor.submit(() -> {
            Thread.sleep(1000); // Non-blocking I/O in Virtual Threads!
            return i;
        });
    });
}
```

---

## 🎯 3-Minute Pre-Interview Practice Summary
1. **Stack vs Heap**: Stack = Method frames & variables (per-thread); Heap = Objects (shared).
2. **`ThreadLocal.remove()`**: Must be called when using thread pools to prevent memory leaks.
3. **`volatile`**: Fixes visibility/cache flushing; does NOT fix race condition on `count++`.
4. **ThreadPool Order**: Core Threads -> Queue -> Max Threads -> Rejection Policy.
5. **Virtual Threads (Java 21)**: Unmounts from carrier thread on I/O blocking; enables massive concurrency.
