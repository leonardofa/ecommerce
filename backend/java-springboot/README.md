# E-commerce Product Management System

This is a Java 21 Spring Boot application that implements a product management system for an e-commerce platform. The application follows the hexagonal architecture pattern and includes comprehensive unit tests for both services and REST controllers.

## Architecture

The application is built using the hexagonal architecture (also known as ports and adapters) pattern, which separates the core business logic from external concerns:

- **Domain Layer**: Contains the core business entities and ports (interfaces)
  - `domain/model`: Core domain entities
  - `domain/port`: Interfaces defining the boundaries of the domain

- **Application Layer**: Contains the use cases and business logic
  - `application/service`: Implementations of the domain service ports

- **Infrastructure Layer**: Contains adapters for external systems and frameworks
  - `infrastructure/persistence`: Database-related components
  - `infrastructure/rest`: REST API controllers and DTOs
  - `infrastructure/config`: Configuration classes

## Technologies

- Java 21
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Security
- H2 Database
- SpringDoc OpenAPI
- JUnit 5 & Mockito for testing

## Features

- Product management (CRUD operations)
  - Create product
  - Update product
  - Enable/disable product
  - Delete product
  - Get product by SKU
  - Get all products

- Security
  - Basic authentication
  - Role-based access control (ADMIN and USER roles)
  - ADMIN role required for modifying operations
  - USER role can only view products

- API Documentation
  - OpenAPI documentation available at `/swagger-ui.html`

## Running the Application

### Prerequisites

- Java 21 JDK
- Maven

### Steps

1. Clone the repository
2. Navigate to the project directory
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```
5. Access the application:
   - API: http://localhost:8080/api/products
   - H2 Console: http://localhost:8080/h2-console
   - API Documentation: http://localhost:8080/swagger-ui.html

## Authentication

The application uses basic authentication with the following predefined users:

- Admin user:
  - Username: `admin`
  - Password: `admin`
  - Roles: ADMIN, USER

- Regular user:
  - Username: `user`
  - Password: `user`
  - Roles: USER

## API Endpoints

### Products

- `GET /api/products`: Get all products (USER, ADMIN)
- `GET /api/products/{sku}`: Get a product by SKU (USER, ADMIN)
- `POST /api/products`: Create a new product (ADMIN only)
- `PUT /api/products/{sku}`: Update a product (ADMIN only)
- `PATCH /api/products/{sku}/enable`: Enable a product (ADMIN only)
- `PATCH /api/products/{sku}/disable`: Disable a product (ADMIN only)
- `DELETE /api/products/{sku}`: Delete a product (ADMIN only)

## Testing

The application includes comprehensive unit tests for both services and controllers:

- Service tests: `src/test/java/com/ecommerce/application/service`
- Controller tests: `src/test/java/com/ecommerce/infrastructure/rest/controller`

Run the tests with:
```
mvn test
```