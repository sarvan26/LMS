# Library Management System

A comprehensive Java application for managing library operations including book management, patron services, branch management, lending processes, and book reservations.

## System Design Diagram

This diagram shows the relationships between the main components of the Library Management System.

### Core Components

```
+----------------------------------+
|    LibraryManagementSystem       |
+----------------------------------+
| - bookService                    |
| - patronService                  |
| - lendingService                 |
| - reservationSystem              |
| - branchService                  |
+----------------------------------+
```

### Domain Models

```
+---------------+       +---------------+       +---------------+
|     Book      |       |    Branch     |       |    Patron     |
+---------------+       +---------------+       +---------------+
| - title       |<----->| - id          |       | - id          |
| - author      |       | - name        |       | - name        |
| - isbn        |       | - address     |       | - contactInfo |
| - pubYear     |       | - books       |       | - history     |
| - available   |       +---------------+       +---------------+
| - branch      |                                      ^
+---------------+                                      |
       ^                                               |
       |                                               |
       |            +-------------------+              |
       +----------->|  LendingRecord    |--------------+
                    +-------------------+
                    | - book            |
                    | - patron          |
                    | - checkoutDate    |
                    | - dueDate         |
                    | - returnDate      |
                    +-------------------+
```

### Service Layer

```
+----------------+    +----------------+    +----------------+
|  BookService   |    | PatronService  |    | BranchService  |
+----------------+    +----------------+    +----------------+
| - books        |    | - patrons      |    | - branches     |
| + addBook()    |    | + addPatron()  |    | + addBranch()  |
| + removeBook() |    | + updatePatron |    | + updateBranch |
| + updateBook() |    | + findById()   |    | + findById()   |
| + findBook()   |    | + getAll()     |    | + findByName() |
| + getAll()     |    +----------------+    | + getAll()     |
+----------------+                          | + transferBook |
                                            +----------------+
```

### Lending and Reservation

```
+----------------------+          +----------------------+
|   LendingService     |          |  ReservationSystem   |
+----------------------+          +----------------------+
| - lendingRecords     |          | - bookReservations   |
| - bookService        |--------->| - observers          |
| - patronService      |          | + addObserver()      |
| - reservationSystem  |          | + removeObserver()   |
| + checkoutBook()     |          | + notifyObservers()  |
| + returnBook()       |          | + reserveBook()      |
| + getActiveLendings()|          | + getReservations()  |
| + getOverdueBooks()  |          | + notifyAvailable()  |
| + getHistory()       |          +----------------------+
+----------------------+                   |
                                           |
                                           v
                      +------------------------------------+
                      |            Observer                |
                      +------------------------------------+
                      | + update(message)                  |
                      +------------------------------------+
                                   ^
                                   |
                      +------------------------------------+
                      |          PatronNotifier            |
                      +------------------------------------+
                      | + update(message)                  |
                      +------------------------------------+
```

### Main Relationships

1. The `LibraryManagementSystem` contains and coordinates all services
2. `Book` and `Branch` have a bidirectional relationship
3. `LendingRecord` connects `Book` and `Patron`
4. Service classes manage their respective domain entities
5. `LendingService` depends on other services
6. `ReservationSystem` implements the Observer pattern with `PatronNotifier`

## System Overview

The Library Management System is designed to manage all aspects of a multi-branch library system with the following features:

- Book inventory management across multiple branches
- Patron registration and management
- Book lending and return processing
- Reservation system with notifications
- Branch management including book transfers between branches
- Reporting functionality for overdue books and lending history

## Architecture

This system follows object-oriented design principles with a service-oriented architecture. Key components include:

### Core Domain Models

- **Book**: Represents a book with title, author, ISBN, publication year, availability status, and branch location
- **Branch**: Represents a library branch with ID, name, address, and a collection of books
- **Patron**: Represents a library member with ID, name, contact information, and borrowing history
- **LendingRecord**: Tracks book checkouts including checkout date, due date, and return date

### Service Components

- **BookService**: Manages book inventory across the library system
- **PatronService**: Handles patron registration and data management
- **BranchService**: Manages library branches including book transfers
- **LendingService**: Processes book checkouts and returns
- **ReservationSystem**: Implements a reservation queue for books

### Design Patterns

The system implements the following design patterns:

- **Observer Pattern**: Used for the notification system when books become available
- **Service Layer Pattern**: Separates business logic from domain models
- **Repository Pattern**: Manages data access and persistence

## Class Structure

The system is organized into the following main classes:

1. **LibraryManagementSystem**: The main class that integrates all components
2. **Model Classes**: Book, Branch, Patron, LendingRecord
3. **Service Classes**: BookService, PatronService, BranchService, LendingService
4. **ReservationSystem**: Handles book reservations with Observer pattern implementation
5. **Observer Interface and PatronNotifier**: For notification functionality

## Key Features

### Book Management
- Add, update, and remove books
- Search books by ISBN, title, or author
- Track book availability
- Transfer books between branches

### Patron Management
- Register new patrons
- Update patron information
- View patron borrowing history

### Lending Operations
- Check out books to patrons
- Process book returns
- Generate overdue book reports
- Track lending history

### Reservation System
- Allow patrons to reserve unavailable books
- Maintain a waitlist for each book
- Send notifications when reserved books become available

### Branch Management
- Add and update branch information
- Transfer books between branches
- Track inventory by branch

## Technical Details

- Implemented in Java with object-oriented design
- Uses Java Collections Framework for data structures
- Implements logging with Java's Logger class
- Designed for extensibility with clear separation of concerns

## Sample Usage

The sample code in the main method demonstrates:
1. Creating library branches
2. Adding books to the library inventory
3. Registering patrons
4. Processing book checkouts and returns
5. Handling book reservations
6. Transferring books between branches
7. Generating reports

## Future Enhancements

Potential improvements to the system could include:
- Database persistence layer
- User interface (web or desktop application)
- Fine calculation for overdue books
- Integration with external catalog systems
- Barcode scanning functionality
- Advanced reporting capabilities