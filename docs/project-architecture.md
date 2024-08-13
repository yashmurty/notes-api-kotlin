# Project Architecture

### Desired Project Tree

```
notes/
├── application/
│   ├── note/
│   │   ├── NoteService.kt
│   │   └── NoteServiceImpl.kt
│   └── user/
│       ├── UserService.kt
│       └── UserServiceImpl.kt
├── domain/
│   ├── note/
│   │   ├── Note.kt
│   │   └── NoteRepository.kt
│   └── user/
│       ├── User.kt
│       └── UserRepository.kt
├── infrastructure/
│   ├── persistence/
│   │   ├── note/
│   │   │   └── NoteRepositoryImpl.kt
│   │   └── user/
│   │       └── UserRepositoryImpl.kt
│   └── config/
│       └── DatabaseConfig.kt
└── presentation/
    ├── note/
    │   └── NoteController.kt
    └── user/
        └── UserController.kt
```

### Project Design Overview

This project follows the principles of Domain-Driven Design (DDD) to create a clear, maintainable, and scalable
architecture.

The project is divided into four main layers:

1. Domain
2. Application
3. Infrastructure
4. Presentation

### 1. Domain Layer

The Domain Layer is the core of our application. It contains:

- **Entities**: The core business objects of the application (e.g., `Note`, `User`).
- **Value Objects**: Immutable objects that don't have an identity (e.g., `EmailAddress`).
- **Aggregates**: Clusters of domain objects that can be treated as a single unit.
- **Domain Events**: Classes representing something that happened in the domain.
- **Repository Interfaces**: Definitions for data access operations.

This layer is independent of other layers and contains pure business logic.

Example:

```kotlin
// domain/note/Note.kt
data class Note(val id: Int, val title: String, val content: String)

// domain/note/NoteRepository.kt
interface NoteRepository {
    fun findById(id: Int): Note?
    fun save(note: Note): Note
    // ...
}
```

### 2. Application Layer

The Application Layer acts as a facade for the domain layer. It contains:

- **Application Services**: Orchestrate the execution of domain logic.
- **DTOs (Data Transfer Objects)**: Objects used to transfer data between processes.
- **Mappers**: Convert between domain objects and DTOs.

This layer defines the jobs the software is supposed to do and directs the expressive domain objects to work
out problems.

### 3. Infrastructure Layer

The Infrastructure Layer supports all the other layers. It contains:

- **Repository Implementations**: Concrete implementations of repository interfaces.
- **ORM Configurations**: Setup for Object-Relational Mapping.
- **External Service Integrations**: Implementations for third-party services.
- **Persistence Configurations**: Database setup and configurations.

This layer contains all the details that are not directly related to the domain but are necessary for the
application to function.

### 4. Presentation Layer

The Presentation Layer is responsible for showing information to the user and interpreting user commands. It
contains:

- **Controllers**: Handle HTTP requests and responses.
- **View Models**: Prepare data for display.
- **API Resources**: Define the structure of API responses.

This layer depends on the Application Layer to execute user actions.
