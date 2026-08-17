# 📦 Collections Framework Mastery: Anti-Forgetfulness Interview Cheat-Sheet

---

## 1. HashMap Internals (Top MNC Interview Question)

### 🗣️ 30-Second Interview Answer
> "A **HashMap** in Java is an array of buckets (`Node<K,V>[]`). When `put(key, value)` is called, Java computes `key.hashCode()`, applies a hash distribution function, and calculates the bucket index using `index = hash & (capacity - 1)`. If two keys hash to the same bucket (collision), Java stores them as a LinkedList. In Java 8+, if a bucket reaches 8 elements and array capacity is at least 64, the LinkedList converts into a **Red-Black Tree** to guarantee **O(log N)** search time instead of **O(N)**."

### 💡 Real-World Analogy
* **HashMap**: A library mail sorting room with 16 post-boxes (buckets).
* **Hash Code**: The ZIP code on an envelope.
* **Collision**: Two letters have the same ZIP code and go into the same box.
* **Treeification**: When a single box gets too full with 8 letters, the librarian replaces the pile with an alphabetized index folder so letters can be retrieved fast.

---

### 🔍 Crucial Internal Parameters & Rules

| Parameter | Default Value | Why It Matters |
| :--- | :--- | :--- |
| **Initial Capacity** | `16` (`1 << 4`) | Must always be a power of 2 so bitwise `hash & (n-1)` index calculation works efficiently. |
| **Load Factor** | `0.75` | Balance between time complexity and memory overhead. |
| **Threshold** | `Capacity * LoadFactor` (16 * 0.75 = **12**) | When number of entries exceeds 12, HashMap doubles capacity to **32** and rehashes all elements. |
| **TREEIFY_THRESHOLD** | `8` | Bucket converts from LinkedList to Red-Black Tree. |
| **UNTREEIFY_THRESHOLD** | `6` | Tree converts back to LinkedList during resize if elements drop to 6. |
| **MIN_TREEIFY_CAPACITY** | `64` | Minimum table capacity before treeification is allowed. |

---

### ⚠️ Common Interview Questions & Traps

#### Q1: What happens if two keys have the exact same `hashCode()`?
* They end up in the **same bucket**.
* Java calls `key1.equals(key2)`.
  * If `equals()` returns `true`: The old value is **overwritten**.
  * If `equals()` returns `false`: A **collision** occurs, and key2 is appended to the bucket's LinkedList/Tree.

#### Q2: What is the contract between `equals()` and `hashCode()`?
* If two objects are equal according to `equals()`, they **MUST** have the same `hashCode()`.
* If two objects have the same `hashCode()`, they are **NOT necessarily equal** (hash collision).
* **Interview Trap**: If you override `equals()` without overriding `hashCode()`, HashMap lookup fails (`get()` returns `null`) because different buckets will be checked for equal keys!

---

## 2. ConcurrentHashMap vs Hashtable vs `Collections.synchronizedMap()`

### 🗣️ 30-Second Interview Answer
> "`Hashtable` and `synchronizedMap()` lock the **entire map** for every read/write operation, creating severe performance bottlenecks under high thread concurrency. `ConcurrentHashMap` uses **fine-grained locking**: in Java 8+, it uses **CAS (Compare-And-Swap)** for insertion into empty buckets and locks only the **head node of a single bucket** (`synchronized(bucketHead)`) during writes. Reads do NOT require locking at all."

### 💡 Real-World Analogy
* **`Hashtable` / `synchronizedMap`**: A single-person bathroom. Only one person can enter the entire building's bathroom at a time.
* **`ConcurrentHashMap`**: A building with 16 separate stalls. 16 people can use different stalls simultaneously without blocking each other.

---

## 3. `ArrayList` vs `LinkedList`

| Feature | `ArrayList` | `LinkedList` |
| :--- | :--- | :--- |
| **Internal Structure** | Resizable Dynamic Array | Doubly-Linked List |
| **Random Access (`get(i)`)** | **O(1)** (Direct index calculation) | **O(N)** (Traverses nodes from head/tail) |
| **Insertion at End** | **O(1)** amortized | **O(1)** |
| **Insertion in Middle** | **O(N)** (Elements shifted) | **O(N)** (Traverse + node relinking) |
| **Memory Efficiency** | High (Contiguous memory, better CPU cache locality) | Low (Node overhead for `next`/`prev` pointers) |
| **MNC Interview Verdict** | Default choice for 95% of use cases due to CPU cache locality! |

---

## 4. `HashSet` Internals

* **How does `HashSet` store unique elements?**
  * Internally, a `HashSet` is backed by a **`HashMap`**.
  * When you call `hashSet.add(element)`, it calls `hashMap.put(element, DUMMY_PRESENT_OBJECT)`.
  * Uniqueness is guaranteed automatically by HashMap's key deduplication mechanism!

---

## 🎯 3-Minute Pre-Interview Practice Summary
1. **HashMap Index Formula**: `index = (capacity - 1) & hash`.
2. **HashMap Collision**: Bucket LinkedList turns Red-Black Tree at count >= 8 & capacity >= 64.
3. **`equals()` & `hashCode()`**: Equal objects must have equal hashCodes; failing this breaks HashMap/HashSet.
4. **`ConcurrentHashMap`**: Uses CAS + Bucket-level node locking (no global map lock).
