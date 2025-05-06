# Inventory Management System

A web-based inventory management system built with Spring Boot and Thymeleaf.

## Features

- Product management (add, edit, delete, view)
- Store management (add, edit, delete, view)
- Transaction management (incoming and outgoing)
- Real-time inventory tracking
- Web-based user interface

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

1. Clone the repository:
```bash
git clone <repository-url>
cd inventory-management
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the application:
- Web interface: http://localhost:8080
- H2 Database Console: http://localhost:8080/h2-console
  - JDBC URL: jdbc:h2:mem:inventorydb
  - Username: sa
  - Password: (leave empty)

## Usage

1. First, add some products and stores through their respective pages.
2. Create transactions to move products in and out of stores.
3. View transaction history and current inventory levels.

## Development

The project uses:
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database
- Thymeleaf
- Bootstrap 5
- Maven

## Project Structure

```
src/main/java/com/inventory/
├── controller/    # Web controllers
├── model/        # Entity classes
├── repository/   # Data access layer
└── InventoryApplication.java

src/main/resources/
├── templates/    # Thymeleaf templates
└── application.properties
``` 