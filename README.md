# management-device

Device database management.

## Description

This project is an application for managing devices, allowing the creation, updating, listing, and deletion of devices. The application is built using Java, Spring Boot, and Maven.

## Technologies Used

- **Java**
- **Spring Boot**
- **Maven**
- **PostgreSQL**
- **Redis**
- **Flyway**

## Pattern Used
- **Hexagonal**

## Environment Setup

1. **Database Configuration:**
    - Ensure PostgreSQL is installed and running.
    - Create a database named `global`.
    - Update the connection settings in the `application.properties` file as needed.
2. **Docker Configuration:** (Optional)
    - Ensure Docker is installed and running.
    - Run the following command to start a Redis container:
        ```
        docker-compose up
        redis port 6379:6379
        postgres port 5432:5432
        ```