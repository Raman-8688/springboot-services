# ⚡ Apache Kafka & Event-Driven Architecture: Anti-Forgetfulness Interview Cheat-Sheet

---

## 1. Core Kafka Architecture Components

### 🗣️ 30-Second Interview Answer
> "Apache Kafka is a distributed, partitioned, append-only commit log event streaming platform. Data is organized into **Topics**, which are split into **Partitions** across **Brokers** for parallel processing. Messages inside a partition are ordered and assigned an incremental **Offset**."

### 💡 Real-World Analogy
* **Topic**: A multi-lane highway (e.g., "Order-Events Highway").
* **Partitions**: The lanes on the highway. More lanes = higher throughput!
* **Producer**: Cars driving onto the highway publishing messages.
* **Consumer Group**: A fleet of delivery trucks. Each truck takes 1 lane (partition). No two trucks in the *same* group drive on the same lane!
* **Offset**: Mileage markers along each lane showing how far a consumer truck has driven.

---

### 🔍 Producer Acknowledgment (`acks`) Levels

| `acks` Setting | Meaning | Throughput | Durability / Reliability |
| :--- | :--- | :--- | :--- |
| **`acks = 0`** | Producer fires event and doesn't wait for response. | Highest | ❌ Low (Risk of message loss). |
| **`acks = 1`** (Default) | Producer waits for **Leader broker** to write event. | High | ⚠️ Medium (If leader crashes before replicating to followers, message lost). |
| **`acks = all` / `-1`** | Producer waits for **Leader + all In-Sync Replicas (ISR)**. | Medium | ✅ Highest (Zero message loss!). |

---

## 2. Consumer Groups & Partition Rebalancing

### 🗣️ 30-Second Interview Answer
> "A **Consumer Group** consists of one or more consumers reading from a topic. Kafka assigns each partition to **exactly one consumer** within a group. If consumer count exceeds partition count, extra consumers sit idle. If a consumer crashes, Kafka triggers a **Rebalance** to reassign its partitions to remaining active consumers."

```
Topic: "user-orders" (3 Partitions: P0, P1, P2)

Consumer Group A (3 Consumers):
Consumer 1 ──► P0
Consumer 2 ──► P1
Consumer 3 ──► P2

Consumer Group A (4 Consumers - 1 Idle):
Consumer 1 ──► P0
Consumer 2 ──► P1
Consumer 3 ──► P2
Consumer 4 ──► (IDLE - Waiting as standby)
```

---

## 3. Handling Message Failure: Retry Topics & Dead Letter Queue (DLQ)

When processing a message fails (e.g., DB down or downstream service error):
1. **Immediate Retries**: Retry processing `N` times with exponential backoff (e.g., 1s, 2s, 4s).
2. **Retry Topics**: Move failed event to `order-events-retry-5m` so main topic pipeline isn't blocked.
3. **Dead Letter Queue (DLQ)**: If all retries fail, publish message to `order-events-dlq` for manual inspection and alerting.

---

## 4. Idempotent Consumer & The Outbox Pattern

### 1. Idempotent Consumer
Kafka guarantees **at-least-once delivery** (duplications can occur due to network retries).
* **Solution**: Every event includes a unique `eventId` or `transactionId`. The consumer checks Redis or DB (`processed_events` table) before processing:
  ```sql
  INSERT INTO processed_events (event_id) VALUES ('evt_123');
  -- If duplicate key error, skip processing!
  ```

### 2. Transactional Outbox Pattern
Solves the problem: *"How do I update DB and send Kafka event atomically without distributed transactions?"*
* **Solution**: Write the business data AND the event record inside the **same DB local transaction** into an `OUTBOX` table. A background CDC engine (Debezium / Poller) reads the `OUTBOX` table and publishes to Kafka!

---

## 5. Kafka vs RabbitMQ Quick Comparison

| Feature | Apache Kafka | RabbitMQ |
| :--- | :--- | :--- |
| **Model** | Pull-based (Consumers pull events). | Push-based (Broker pushes to consumers). |
| **Persistence** | Permanent Log (Retained for X days/forever). | Message deleted after consumer ACK. |
| **Replayability** | ✅ Yes! Reset offset to re-read old messages. | ❌ No. |
| **Use Case** | High-throughput event streaming, analytics, microservices EDA. | Complex routing, task queues, AMQP RPC. |

---

## 🎯 3-Minute Pre-Interview Practice Summary
1. **`acks=all`**: Highest reliability for Kafka producers.
2. **Consumer Group Rule**: Max active consumers in 1 group = total partitions.
3. **Idempotency**: Maintain `eventId` check to handle duplicate messages.
4. **DLQ Pattern**: Unprocessable messages go to Dead Letter Queue to avoid blocking partition.
