# 🤖 Spring AI & Generative AI Integration: Anti-Forgetfulness Interview Cheat-Sheet

---

## 1. Spring AI Core Architecture

### 🗣️ 30-Second Interview Answer
> "**Spring AI** is an official Spring framework project that provides a unified, portable abstraction layer for integrating Artificial Intelligence and Large Language Models (OpenAI, Ollama, Anthropic, HuggingFace) into Spring Boot applications. It eliminates boilerplate code using standard Spring concepts like `ChatClient`, `Prompt`, and `VectorStore`."

---

## 2. Retrieval-Augmented Generation (RAG) Architecture

### 🗣️ 30-Second Interview Answer
> "**RAG** is an architectural pattern that enhances LLM responses by retrieving relevant enterprise private data from a **Vector Database** and passing it as context inside the prompt to the LLM. This prevents AI hallucinations and allows the model to answer questions based on up-to-date company data without retraining the LLM."

### 💡 RAG Step-by-Step Flow

```
1. Document Ingestion Phase:
PDF / DB Docs ──► Text Chunks ──► Embedding Model ──► Vector DB (PgVector / Milvus)

2. Query Retrieval Phase:
User Question ──► Vector Search ──► Relevant Context Chunks
                                            │
                                            ▼
User Prompt + Retrieved Context ──► LLM (OpenAI / Ollama) ──► Accurate Answer
```

---

## 3. Function Calling in Spring AI

Allows LLMs to dynamically execute local Java method logic when it needs real-time data (e.g., checking order status in DB or current weather):

```java
@Configuration
public class FunctionConfig {

    @Bean
    @Description("Fetch order status by order ID")
    public Function<OrderRequest, OrderStatusResponse> getOrderStatusFunction(OrderService orderService) {
        return request -> orderService.findStatus(request.orderId());
    }
}
```

---

## 🎯 3-Minute Pre-Interview Practice Summary
1. **Spring AI Abstraction**: `ChatClient` works across OpenAI, Ollama, Azure AI.
2. **RAG Goal**: Inject private enterprise context into LLM prompt via Vector search.
3. **Vector Database**: Stores text as high-dimensional numerical array (Embeddings).
4. **Function Calling**: LLM determines when to invoke your local Spring Beans.
