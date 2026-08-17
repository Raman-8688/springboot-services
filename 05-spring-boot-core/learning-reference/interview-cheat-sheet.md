# 🍃 Spring Boot 3.x Core: Anti-Forgetfulness Interview Cheat-Sheet

---

## 1. Spring IoC & Dependency Injection

### 🗣️ 30-Second Interview Answer
> "**Inversion of Control (IoC)** is a design principle where the control of object creation and lifecycle is handed over to the Spring container (`ApplicationContext`) instead of being managed manually with `new`. **Dependency Injection (DI)** is the pattern used to supply those objects (Beans) into dependent classes at runtime."

### 💡 Real-World Analogy
* **Manual Object Creation (`new`)**: Driving to a factory, buying engine parts, assembling a car yourself every time you need to commute.
* **Spring IoC / DI**: Renting a car from a fleet provider. You ask for a car (`@Autowired`), the provider hands you a ready-to-drive car, and takes care of maintenance.

---

### ⚠️ Constructor Injection vs Field Injection (`@Autowired`)

| Feature | Constructor Injection (Recommended) | Field Injection (`@Autowired` on field) |
| :--- | :--- | :--- |
| **Testability** | Easy to unit test without Spring context (`new Service(mockRepo)`). | Hard to test; requires Reflection or `@SpringBootTest`. |
| **Immutability** | Fields can be declared `final`. | Fields cannot be `final`. |
| **Circular Dependencies** | Detected immediately at application startup. | Hidden until runtime execution. |

```java
// ✅ BEST PRACTICE: Constructor Injection with Lombok @RequiredArgsConstructor
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // Immutable & mandatory
}
```

---

## 2. Spring Bean Lifecycle

### 🗣️ 30-Second Interview Answer
> "When Spring initializes a Bean, it follows 6 key steps:
> 1. **Instantiate** (Constructor call).
> 2. **Populate Properties** (Dependency Injection).
> 3. **BeanNameAware / ApplicationContextAware** callbacks.
> 4. **`BeanPostProcessor` pre-initialization**.
> 5. **Initialization method** (`@PostConstruct` or `afterPropertiesSet()`).
> 6. **`BeanPostProcessor` post-initialization** (Where AOP proxies are created!).
> 7. **Ready for use**, followed by `@PreDestroy` upon container shutdown."

---

## 3. Spring Bean Scopes

| Scope | Description | Use Case |
| :--- | :--- | :--- |
| **`singleton`** (Default) | Single instance per Spring IoC container. | Stateless Services, Repositories, Controllers. |
| **`prototype`** | A new instance created **every time** bean is requested. | Stateful objects like order cart builders. |
| **`request`** | One instance per HTTP request lifecycle. | User request context, audit logging. |
| **`session`** | One instance per HTTP session. | User session security token state. |

---

## 4. `@SpringBootApplication` Annotation Breakdown

It is a meta-annotation combining 3 essential annotations:
1. **`@Configuration`**: Marks class as a source of bean definitions.
2. **`@EnableAutoConfiguration`**: Tells Spring Boot to auto-configure beans based on classpath dependencies (via `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`).
3. **`@ComponentScan`**: Scans the current package and sub-packages for `@Component`, `@Service`, `@Repository`, `@RestController`.

---

## 5. Global Exception Handling Pattern

### 🗣️ 30-Second Interview Answer
> "In Spring Boot, we implement centralized exception handling using **`@RestControllerAdvice`** and **`@ExceptionHandler`**. This intercepts exceptions thrown anywhere in the controller layer and converts them into standardized JSON error responses (`ProblemDetail` or custom DTO) with appropriate HTTP status codes."

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

---

## 🎯 3-Minute Pre-Interview Practice Summary
1. **Constructor Injection**: Recommended over Field Injection for immutability & easy mocking.
2. **Bean Scope**: Default is `singleton` (stateless).
3. **Bean Lifecycle Post-Processing**: AOP proxies are wrapped during `BeanPostProcessor` post-initialization.
4. **Exception Handling**: Use `@RestControllerAdvice` + `@ExceptionHandler`.
