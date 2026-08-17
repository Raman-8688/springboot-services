# 🌐 Microservices Ecosystem: Anti-Forgetfulness Interview Cheat-Sheet

---

## 1. Core Microservices Components

### 🗣️ 30-Second Interview Answer
> "An enterprise microservices architecture consists of 5 core infrastructure components:
> 1. **API Gateway** (Spring Cloud Gateway): Single entry point for routing, authentication, rate limiting.
> 2. **Service Discovery** (Netflix Eureka): Service registry where instances dynamically register their IP and port.
> 3. **Circuit Breaker** (Resilience4j): Prevents cascading failures when a downstream microservice is failing.
> 4. **Distributed Tracing** (Micrometer Tracing + Zipkin): Propagates `traceId` and `spanId` across HTTP/Kafka headers to trace requests across services.
> 5. **Centralized Config** (Spring Cloud Config): Externalized configuration per environment."

---

## 2. Resilience4j Circuit Breaker States (Top Interview Question)

```
       [ CLOSED ] (Normal Operation)
          │  ▲
   Failures > Threshold
          │  │ Successes > Threshold
          ▼  │
        [ OPEN ] (Fallback returned immediately; Calls blocked)
          │
      Wait Duration Elapsed
          │
          ▼
      [ HALF-OPEN ] (Sends limited trial requests to check downstream health)
```

| State | Behavior |
| :--- | :--- |
| **CLOSED** | Normal state. All requests pass through to the downstream service. |
| **OPEN** | Downstream service is failing. Calls are **blocked immediately** and routed to fallback method. |
| **HALF-OPEN** | After wait duration, a limited number of requests are allowed through to check if the downstream service recovered. |

---

## 3. Distributed Transactions: Saga Pattern

### 🗣️ 30-Second Interview Answer
> "In microservices, each service owns its database. We cannot use traditional 2PC (Two-Phase Commit) database locks across services due to high latency and tight coupling. Instead, we use the **Saga Pattern**: a sequence of local transactions where each step updates a service DB and publishes an event. If a step fails, the Saga executes **Compensating Transactions** in reverse order to undo changes."

### 💡 Choreography vs Orchestration Saga

| Feature | Choreography (Event-Driven) | Orchestration (Central Controller) |
| :--- | :--- | :--- |
| **Flow** | Services listen to events and trigger their own next step. | A central **Saga Orchestrator** service explicitly calls each service step via REST/gRPC. |
| **Coupling** | Loose coupling. | Centralized control logic; easier to trace complex workflows. |
| **Best For** | Simple workflows (2-4 steps). | Complex enterprise workflows (E-commerce Order Fulfillment). |

---

## 🎯 3-Minute Pre-Interview Practice Summary
1. **Circuit Breaker**: Transitions CLOSED -> OPEN -> HALF-OPEN -> CLOSED.
2. **Distributed Tracing**: `TraceId` identifies the overall request path; `SpanId` identifies a single service unit of work.
3. **Saga Pattern**: Replaces 2PC with local transactions + compensating rollback transactions.
