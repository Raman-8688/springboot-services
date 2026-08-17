# 🔒 Spring Security 6.x Deep Dive: Anti-Forgetfulness Interview Cheat-Sheet

---

## 1. Spring Security 6.x Filter Chain Architecture

### 🗣️ 30-Second Interview Answer
> "Spring Security operates as a chain of **Servlet Filters** (`DelegatingFilterProxy` -> `FilterChainProxy` -> `SecurityFilterChain`) positioned **BEFORE** Spring's `DispatcherServlet`. When an HTTP request enters, it passes through security filters (like `CorsFilter`, `CsrfFilter`, `JwtAuthenticationFilter`, `UsernamePasswordAuthenticationFilter`). If authenticated, Spring populates the **`SecurityContextHolder`** with an `Authentication` object."

### 💡 Real-World Analogy
* **Security Filter Chain**: Airport Security Checkpoint before reaching the flight gate.
  * **CORS / CSRF Filter**: Initial ID & Boarding Pass validation at airport entry.
  * **JWT Filter**: X-Ray scanning your security badge.
  * **`SecurityContextHolder`**: Stamping your passport so flight attendants (Controllers) know you are cleared.
  * **`DispatcherServlet`**: The airport boarding gate leading to your airplane (Controller method).

---

### 🔍 Key Architectural Components

```
Client Request 
   │
   ▼
[ DelegatingFilterProxy ]
   │
   ▼
[ SecurityFilterChain ] ──► (1. JwtAuthenticationFilter) ──► (2. AuthorizationFilter)
   │
   ▼ (Valid Token)
[ SecurityContextHolder ] ──► Stores Authentication object (Principal, Credentials, Authorities)
   │
   ▼
[ DispatcherServlet ] ──► [ RestController ]
```

---

## 2. Spring Security 6.x Config Class (`SecurityFilterChain` Bean)

In Spring Security 6.x, `WebSecurityConfigurerAdapter` is completely removed! We define security using a `@Bean` returning `SecurityFilterChain`:

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables @PreAuthorize & @PostAuthorize
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless REST APIs using JWT
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**", "/actuator/health", "/v3/api-docs/**").permitAll()
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
```

---

## 3. Stateless JWT Authentication Step-by-Step

### Step 1: User Login
1. Client POSTs credentials to `/api/v1/auth/login`.
2. `AuthenticationManager.authenticate(...)` verifies username & password.
3. Upon success, server generates a signed **JWT Token** (using Secret Key + Expiration + Claims like User ID & Roles) and returns it to Client.

### Step 2: Authenticated Requests
1. Client sends HTTP request with Header: `Authorization: Bearer <jwt-token>`.
2. Custom `JwtAuthenticationFilter` (extending `OncePerRequestFilter`) intercepts request:
   - Extracts token from header.
   - Validates signature and expiration via `JwtUtils`.
   - Loads `UserDetails` and creates `UsernamePasswordAuthenticationToken`.
   - Sets security context:
     ```java
     SecurityContextHolder.getContext().setAuthentication(authToken);
     ```
3. Request proceeds to Controller.

---

## 4. Role-Based (RBAC) & Method-Level Security

### 🗣️ 30-Second Interview Answer
> "Role-Based Access Control (RBAC) restricts API access based on granted authorities. In Spring Security 6, we use **`@EnableMethodSecurity`** on `@Configuration` and annotate controller/service methods with **`@PreAuthorize("hasRole('ADMIN')")`** or **`@PreAuthorize("hasAuthority('SCOPE_read')")`** to enforce fine-grained access control before method execution."

```java
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public AccountDto getAccount(@PathVariable Long id) { ... }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('ACCOUNT_DELETE')")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) { ... }
}
```

---

## 🎯 3-Minute Pre-Interview Practice Summary
1. **Spring Security Location**: Filters run *before* `DispatcherServlet`.
2. **Stateless APIs**: Set `SessionCreationPolicy.STATELESS` and disable CSRF when using JWT.
3. **`SecurityContextHolder`**: Holds current authenticated user state per thread.
4. **Method Security**: Enable with `@EnableMethodSecurity` and protect with `@PreAuthorize`.
