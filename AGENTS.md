# Atlas Bank - Agent Instructions

## Project Overview
Java 21 Spring Boot 4.0.8 hexagonal architecture banking application with Maven. Uses H2 in-memory DB for dev, PostgreSQL for Keycloak in Docker. Secured via OAuth2/JWT (Keycloak).

## Key Commands
```bash
# Build & run tests (includes ArchUnit architecture tests)
./mvnw verify

# Run application (requires Keycloak running)
./mvnw spring-boot:run

# Run single test class
./mvnw test -Dtest=HexagonalArchitectureTest

# Start Keycloak + PostgreSQL (for auth)
docker compose -f docker/docker-compose.yml up -d

# Keycloak: http://localhost:8181 (admin/admin)
# Realm: atlas-bank, Issuer URI: http://localhost:8181/realms/atlas-bank
# H2 Console: http://localhost:8080/h2-console (jdbc:h2:mem:atlasbank)
```

## Architecture Rules (Enforced by ArchUnit Tests)
- **Domain** (`..domain..`): Pure core — no dependencies on `application`, `infrastructure`, or Spring
- **Application** (`..application..`): Uses ports (interfaces) — no dependencies on `infrastructure`
- **Infrastructure** (`..infrastructure..`): Adapters implementing ports; Spring config, controllers, JPA entities
- **No cyclic dependencies** between any packages
- **Naming conventions**:
  - REST controllers: `*Controller` in `infrastructure.adapter.in.rest`
  - Use cases: `*UseCase` interfaces in `application.port.in`
  - Input/output ports: must be interfaces

## Package Structure
```
com.atlas.bank.atlas_bank
├── application
│   ├── command/          # Write-side commands
│   ├── facade/           # Read-side facades
│   ├── port/in/          # Use case interfaces (UseCase)
│   ├── port/out/         # Repository/service ports (interfaces)
│   ├── query/            # Read models
│   ├── service/          # Application services
│   └── validation/       # Application validators
├── domain
│   ├── exception/        # Domain exceptions
│   ├── model/            # Entities, value objects, events
│   ├── service/          # Domain services
│   ├── strategy/fee/     # Fee calculators (strategy pattern)
│   └── validation/       # Domain validators
└── infrastructure
    ├── adapter/in/rest/  # Controllers, DTOs, mappers
    ├── adapter/out/
    │   ├── fraud/        # External fraud check adapter
    │   └── persistence/  # JPA entities, Spring Data repos, mappers
    ├── adapter/listener/ # Event listeners (audit, notification)
    └── config/           # Spring config (Security, Domain beans)
```

## Key Conventions
- **MapStruct + Lombok** for mappers/DTOs — annotation processors configured in `pom.xml`
- **Fee calculators**: Ordered beans (`@Order`) in `DomainBeanConfig` — Savings(1) → Checking(2) → Premium(3) → Default
- **Validators**: Chain of responsibility via `@Order` — AccountStatus(1) → SufficientFunds(2) → Fraud(3)
- **Transaction state machine**: `PendingState → ValidatedState → ExecutedState` (or `RejectedState`/`ReversedState`)
- **Security**: OAuth2 Resource Server with JWT; roles from `realm_access.roles` claim; H2 console public

## Testing
- Unit tests: Standard JUnit 5 + Spring Boot Test
- Architecture tests: ArchUnit in `src/test/.../archtest/` — run with `./mvnw verify`
- No integration test profile; H2 `create-drop` for test isolation

## Common Gotchas
- Keycloak must be running before app starts (issuer URI in `application.yaml`)
- MapStruct mappers require `mvn compile` to generate implementations
- Domain classes must not import Spring or infrastructure types
- Use `@EnableCaching` on main app class (already present)