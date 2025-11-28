# EduDataIntegrator

A local JavaFX and REST-based prototype that demonstrates an integrated system for managing study results across multiple data sources using a Service-Oriented Architecture (SOA) approach.

---

## Overview
EduDataIntegrator is a prototype that pulls student grades from (mocked) Canvas assignments, integrates them with student data from StudentITS, and sends them to Ladok for official record-keeping. It demonstrates how three separate education systems (mocked locally) can be connected through REST APIs and a shared database layer.

Built with JavaFX for the UI and PostgreSQL for data, it's a hands-on example of Service-Oriented Architecture applied to university data management.

## Hard Depenencies
These need to be installed manually before you can run the project. Maven handles everything else.

- **Java 21** – Required to compile and run the code.
- **PostgreSQL** – The project uses three local database schemas (Epok, StudentITS, Ladok). 
- **Maven 3.9.6+** – Build tool for compiling and running tests and to handle *soft dependencies* automatically. 

### Database setup
Create a PostgreSQL user with the credentials in [`src/main/resources/db.properties`](src/main/resources/db.properties) (or adjust the properties file to match your local setup). The migration scripts will create the three schemas automatically when you run the project.

## How to run
- 1: Build the project with maven: 
`mvn clean install`
- 2: Make sure PostgreSQL is running locally as per db.properties (see above, *Database setup*).
- 3: Run migrations (set's up tables, schemas and populates with test data automatically):
`mvn -P db flyway:migrate`
- 4: Start the REST server (in one terminal): 
`mvn clean compile exec:java`
- 5: Start the JavaFX application (in another terminal):
`mvn javafx:run`
- 6: Run the program through the frontend JavaFX interface.

---

## Maven Core Dependencies and Plugins

Maven manages dependencies across three main layers: the UI (JavaFX), the database (Hibernate + PostgreSQL), and REST integration (Jersey). Here are the core ones:

### Frontend
- **JavaFX** – Builds the desktop GUI.

### Database & Persistence
- **PostgreSQL** – JDBC driver for the local postgreSQL database.
- **HikariCP** – Reuses database connections instead of opening new ones for each query (faster). (See [`ConnectionFactory.java`](src/main/java/com/pulse/config/ConnectionFactory.java) for how it's configured.)
- **Jakarta Persistence API (JPA)** – Standard way to map Java objects to database tables via Entity files. (See entity classes like [`StudentitsStudentAccountEntity.java`](src/main/java/com/pulse/entity/StudentitsStudentAccountEntity.java) and [`persistence.xml`](src/main/resources/META-INF/persistence.xml) for configuration.)
- **Hibernate** – Implements JPA; generates SQL automatically from entity annotations.
- **Flyway** – Automatically ensures everyone has the same database state across the three schemas (no manual SQL needed).

### REST & Integration
- **Jersey** – REST framework for HTTP endpoints.Automates java methods into HTTP endpoints via URL mapping.
- **Jackson** – Automatically converts Java objects to JSON when sending REST responses and JSON to Java objects when receiving requests. (See [`ObjectMapperContextResolver.java`](src/main/java/com/pulse/config/ObjectMapperContextResolver.java) for configuration.)

### Validation & Logging
- **SLF4J + Logback** – Logging framework for debugging.

### Testing
- **JUnit 5** – Unit testing.
- **TestFX** – JavaFX GUI testing.(not used in this project)
- **Rest-Assured** – REST endpoint testing. (not used in this project)

### Build Plugins
- **Maven Compiler Plugin** – Compiles to Java 21.
- **Maven Surefire / Failsafe** – Run tests.
- **Maven Enforcer Plugin** – Ensures correct Java/Maven versions.
- **JavaFX Maven Plugin** – Runs GUI with `mvn javafx:run`.
- **Flyway Maven Plugin** – Applies migrations via the `db` profile.

## Technical Terms Explained
This section clarifies the main technical keywords used in the project description.

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
  Each migration file has a name like `V1__create_tables.sql` or `V2__add_column.sql`, and Flyway runs them in order when the project starts or when you execute `mvn -P db flyway:migrate`.  
  This makes sure all three schemas (Epok, StudentITS, Ladok) always use the same, up-to-date structure without having to run SQL manually.

- **Validation**  
  Automatic checking of incoming data (for example, ensuring a field is not empty) before it’s saved or processed.

- **Dependency Injection (DI)**  
  A design pattern where required components are provided automatically by the framework instead of being created manually, used here by Jersey.

- **GUI (Graphical User Interface)**  
  The visual interface built with JavaFX that allows users to interact with the prototype.

These concepts form the technical foundation of the project’s architecture—connecting the GUI, REST services, and database through a clear, modular SOA structure.

