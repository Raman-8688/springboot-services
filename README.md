# 🚀 Java & Spring Boot MNC Mastery Hub

Welcome to your **Enterprise Java & Spring Boot Learning & Practice Workspace**! Designed for 1+ to 5+ years experienced Java Full Stack & Microservices Engineers aiming for top MNC product/service companies (Amazon, Infosys, TCS, Cognizant, Wipro, Capgemini, Accenture, Paypal, Razorpay, Swiggy, etc.).

---

## 💡 The Anti-Forgetfulness Learning System

> **Problem**: *"I read theory, but after 1 week I forget everything during interviews!"*
> **Solution**: This repository uses a **4-Step Retention Framework**:
1. **30-Second Interview Answer**: Direct, bullet-point response to speak out loud during technical screening.
2. **Real-World Analogy**: Simple everyday metaphor so concepts stick in memory permanently.
3. **Dual-Folder Practice**:
   - `learning-reference/`: Fully written working code + clear explanations in simple English (no heavy jargon).
   - `practice-lab/`: Starter template with test cases where **YOU** write the code to test yourself.
4. **Interview Traps**: Trick questions interviewers ask to test true practical depth.

---

## 🗺️ Module Directory & Roadmap

| Module | Topic | Core Focus | Link |
| :--- | :--- | :--- | :--- |
| **`00`** | **Git & DevOps Infrastructure** | Docker Compose infra (Postgres, Kafka, Redis, Zipkin), Git branching, GitHub Actions CI/CD | [Explore `00-git-and-devops`](file:///d:/springboot-services/00-git-and-devops) |
| **`01`** | **Java 8 & Modern Java Features** | Streams API, Lambdas, Optional, Functional Interfaces, Default Methods, Java 17/21 Records | [Explore `01-java-8`](file:///d:/springboot-services/01-java-8-and-modern-features) |
| **`02`** | **Collections Framework Mastery** | HashMap Internals, ConcurrentHashMap, ArrayList vs LinkedList, HashSet, PriorityQueue | [Explore `02-collections`](file:///d:/springboot-services/02-collections-framework-mastery) |
| **`03`** | **JVM Memory & Multithreading** | Heap/Stack, Metaspace, GC (G1GC/ZGC), Locks, ThreadPool, Virtual Threads (Java 21) | [Explore `03-jvm-concurrency`](file:///d:/springboot-services/03-jvm-memory-and-multithreading) |
| **`04`** | **Database & JPA Mastery** | Database Indexing (B-Tree), ACID, N+1 Query Problem, JPA First/Second Level Cache | [Explore `04-database`](file:///d:/springboot-services/04-database-and-jpa-mastery) |
| **`05`** | **Spring Boot 3.x Core** | IoC/DI, Bean Lifecycle, Custom Starters, Global Exception Handling, Actuator, AOP | [Explore `05-spring-boot-core`](file:///d:/springboot-services/05-spring-boot-core) |
| **`06`** | **Spring Security 6.x Deep Dive** | Security Filter Chain, Custom JWT Auth, OAuth2 Resource Server, Method Security (`@PreAuthorize`), RBAC | [Explore `06-spring-security`](file:///d:/springboot-services/06-spring-security-mastery) |
| **`07`** | **Kafka & Event-Driven Architecture** | Kafka Producers/Consumers, Schema Registry (Avro), DLQ, Idempotency, Outbox Pattern | [Explore `07-kafka`](file:///d:/springboot-services/07-kafka-event-driven) |
| **`08`** | **Microservices Ecosystem** | Service Discovery (Eureka), API Gateway, Resilience4j Circuit Breaker, Zipkin Tracing, Saga Pattern | [Explore `08-microservices`](file:///d:/springboot-services/08-microservices-ecosystem) |
| **`09`** | **Spring AI & Generative AI** | Spring AI, OpenAI/Ollama APIs, RAG (Retrieval-Augmented Generation), PgVector Embeddings | [Explore `09-spring-ai`](file:///d:/springboot-services/09-spring-ai-llm) |
| **`10`** | **Enterprise MNC Projects** | Full-stack production capstones: Banking CRUD Service, AI Hub, Microservices Suite | [Explore `10-mnc-projects`](file:///d:/springboot-services/10-enterprise-mnc-projects) |

---

## 🛠️ Quick Start & Local Environment

### 1. Launch All Local Services (Postgres, Redis, Kafka, Zipkin)
```bash
cd 00-git-and-devops
docker-compose -f docker-compose-infra.yml up -d
```

### 2. Practice Workflow
1. Navigate to the desired module's `learning-reference/interview-cheat-sheet.md` to review interview theory in simple terms.
2. Inspect `learning-reference/src/` to see working reference code.
3. Open `practice-lab/src/` and complete the `// TODO` exercises.
4. Run Maven tests to verify your solution:
```bash
mvn test
```

---

## 🏆 Daily Recommended Target
- Spend **30 mins** reviewing `interview-cheat-sheet.md` (speak 30s answers out loud).
- Spend **45 mins** solving coding tasks in `practice-lab/`.
- Maintain clean git commits (`feat:`, `fix:`, `docs:`) to build an active GitHub contribution graph.
