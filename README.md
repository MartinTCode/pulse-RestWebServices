# Pulse EduDataIntegrator

A local JavaFX and REST-based prototype that demonstrates an integrated system for managing study results across multiple data sources using a Service-Oriented Architecture (SOA) approach.

---

## Overview

- Uses **PostgreSQL** for databases (three schemas: Epok, StudentITS, Ladok)  
- Uses **JavaFX** for the frontend (with optional CSS styling)  
- Uses **JUnit 5** for testing 

## Branching strategy:

- frontend
- database 1 for each service.
- database integration.
- testing for each branch.
- JSON communication test (API)

---

## Dependencies Explained

This project implements a local prototype of LTU’s *“Administration of Study Results”* process, following EA and SOA principles.  
Each dependency below supports a specific architectural layer or development activity.

### Frontend (JavaFX)
- **`org.openjfx:javafx-controls` / `javafx-fxml`**  
  Provide the graphical user interface (GUI). Used to build the *Pulse EduDataIntegrator* desktop app that interacts with backend REST services.

### Database Access
- **`org.postgresql:postgresql`**  
  JDBC driver for connecting Java applications to a PostgreSQL database (used for the three local schemas Epok, StudentITS and Ladok).  
- **`com.zaxxer:HikariCP`**  
  High-performance connection pool that efficiently manages concurrent database access.  
- **`jakarta.persistence:jakarta.persistence-api`**  
  Standard API (JPA) for defining how Java objects map to database tables.  
- **`org.hibernate.orm:hibernate-core` / `hibernate-hikaricp`**  
  Implements the JPA API; handles object-relational mapping and SQL generation automatically.

### REST and Integration (SOA Layer)
- **`org.glassfish.jersey.*` (server, grizzly2-http, json-jackson, bean-validation, hk2)**  
  Jersey is the framework used to build REST web services.  
  - `jersey-server` — core JAX-RS engine that defines endpoints.  
  - `jersey-container-grizzly2-http` — runs the REST API in a lightweight embedded server.  
  - `jersey-media-json-jackson` — auto-converts Java objects ⇄ JSON.  
  - `jersey-bean-validation` — validates incoming data automatically.  
  - `jersey-hk2` — provides dependency injection for REST resources.
- **`com.fasterxml.jackson.datatype:jackson-datatype-jsr310`**  
  Adds JSON support for Java 8+ date/time types (e.g. `LocalDate`).

### Validation
- **`jakarta.validation:jakarta.validation-api`** / **`org.hibernate.validator:hibernate-validator`**  
  Define and execute validation annotations such as `@NotNull` and `@Email` on DTOs before data reaches the database.  
- **`org.glassfish:jakarta.el`**  
  Required by the validator for expression-based checks.

### Logging and Security
- **`org.slf4j:slf4j-api`** and **`ch.qos.logback:logback-classic`**  
  Unified, configurable logging for all project layers.  
- **`at.favre.lib:bcrypt`**  
  Optional library for securely hashing sensitive data like passwords.

### Database Migrations
- **`org.flywaydb:flyway-core`**  
  Manages SQL migrations and keeps the three local schemas synchronized.  
  Also used by the Maven Flyway Plugin profile (`-Pdb`).

### Testing
- **`org.junit.jupiter:junit-jupiter`**  
  Core unit-testing framework for Java 21.  
- **`org.testfx:*` (core, junit5)**  
  Automates JavaFX GUI tests.  
- **`io.rest-assured:rest-assured`**  
  Simplifies integration testing of REST endpoints with JSON assertions.

### Build and Enforcement Plugins
- **Maven Compiler Plugin** — compiles sources to Java 21.  
- **Maven Surefire / Failsafe Plugins** — run unit and integration tests separately.  
- **Maven Enforcer Plugin** — ensures correct Java and Maven versions.  
- **JavaFX Maven Plugin** — runs the GUI via `mvn javafx:run`.  
- **Maven Jar / Dependency Plugins** — package the project and copy required runtime JARs.  
- **Flyway Maven Plugin** — applies SQL migrations through the `db` profile.

---

## Why These Dependencies Are Used

This project implements a prototype that simulates a digital workflow for managing study results across multiple integrated systems.  
The dependencies together provide all layers required for this architecture:

- **Jersey + Jackson** enable building and exposing REST endpoints that exchange JSON between the simulated systems.  
- **Hibernate + JPA + PostgreSQL + HikariCP** handle persistence for each system’s local database schema.  
- **Validation (Jakarta / Hibernate Validator)** ensures data integrity before it reaches the database.  
- **Flyway** keeps the three schemas synchronized and versioned for each subsystem.  
- **JavaFX** provides a lightweight GUI for interacting with the backend prototype.  
- **Logging and Testing (SLF4J, Logback, JUnit, Rest-Assured)** support debugging and verifying correct REST communication.

In short, these dependencies implement a realistic **Service-Oriented Architecture (SOA)** stack that covers the complete flow—from GUI input to REST integration and database persistence.

---

## Technical Terms Explained

This section clarifies the main technical keywords used in the project description and dependency overview.

- **Enterprise Architecture (EA)**  
  A structured way to describe how business processes, information, applications, and technology work together across an organization.

- **Service-Oriented Architecture (SOA)**  
  A design approach where functionality is divided into independent, reusable services that communicate through defined interfaces, such as REST APIs.

- **REST (Representational State Transfer)**  
  A standard architecture for web services using HTTP methods (GET, POST, PUT, DELETE) and JSON data for system-to-system communication.

- **JPA (Jakarta Persistence API)**  
  A specification that defines how Java objects are stored and retrieved from relational databases in an object-oriented way.

- **ORM (Object-Relational Mapping)**  
  A technique for mapping objects in code to database tables automatically, reducing the need for manual SQL.

- **Schema**  
  A logical section inside a database that groups related tables. This project uses three schemas—Epok, StudentITS, and Ladok—to simulate separate systems.

- **Database Migrations**  
  In practice, a *database migration* means applying controlled changes to the database automatically — such as creating tables, adding columns, or inserting initial data — through versioned SQL files.  
  Each migration file has a name like `V1__create_tables.sql` or `V2__add_column.sql`, and Flyway runs them in order when the project starts or when you execute `mvn -Pdb flyway:migrate`.  
  This makes sure all three schemas (Epok, StudentITS, Ladok) always use the same, up-to-date structure without having to run SQL manually.

- **Validation**  
  Automatic checking of incoming data (for example, ensuring a field is not empty) before it’s saved or processed.

- **Dependency Injection (DI)**  
  A design pattern where required components are provided automatically by the framework instead of being created manually, used here by Jersey.

- **GUI (Graphical User Interface)**  
  The visual interface built with JavaFX that allows users to interact with the prototype.

These concepts form the technical foundation of the project’s architecture—connecting the GUI, REST services, and database through a clear, modular SOA structure.

