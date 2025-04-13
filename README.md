# RentVideo - Online Video Rental System API

## Overview
RentVideo is a RESTful API service built with Spring Boot that manages an online video rental system. The application provides functionality for user management with role-based access control and video inventory management, all persisted in a MySQL database.

## Features

### Authentication & Authorization
- Basic Authentication for securing endpoints
- Two user roles: CUSTOMER and ADMIN
- Public endpoints for registration and login
- Private endpoints with role-based access control

### User Management
- User registration with email, password, name, and role
- Password hashing using BCrypt
- User login using Basic Auth

### Video Management
- Browse available videos (all authenticated users)
- Create, update, and delete videos (ADMIN role only)
- Video details include title, director, genre, and availability status

## Technology Stack
- Java 17
- Spring Boot 3.x
- Spring Security (Basic Auth)
- Spring Data JPA
- MySQL Database
- Maven

## Getting Started

### Prerequisites
- Java JDK 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Setup
1. Create a MySQL database (or let the application create it):
   ```sql
   CREATE DATABASE rentvideo;
   ```
2. Configure your database connection in `application.properties`

### Configuration
The application uses `application.properties` for configuration. Key settings include:

```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/rentvideo?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA/Hibernate Properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Building and Running

#### Using Maven
```bash
# Clone the repository
git clone https://github.com/yourusername/rentvideo.git
cd rentvideo

# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
```

#### Using JAR file
```bash
java -jar target/rent-video-0.0.1-SNAPSHOT.jar
```

The application will start on port 8080 by default.

## API Endpoints

### Public Endpoints

#### User Registration
- **URL**: `POST /api/auth/register`
- **Request Body**:
  ```json
  {
    "email": "user@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "role": "CUSTOMER"
  }
  ```
- **Response**: 201 Created

#### User Login
- **URL**: `POST /api/auth/login`
- **Authentication**: Basic Auth with email and password
- **Response**: 200 OK

### Private Endpoints (Require Authentication)

#### Get All Videos (Customer & Admin)
- **URL**: `GET /api/videos`
- **Authentication**: Basic Auth
- **Response**: 200 OK

#### Get Available Videos (Customer & Admin)
- **URL**: `GET /api/videos/available`
- **Authentication**: Basic Auth
- **Response**: 200 OK

#### Get Video by ID (Customer & Admin)
- **URL**: `GET /api/videos/{id}`
- **Authentication**: Basic Auth
- **Response**: 200 OK

#### Create New Video (Admin Only)
- **URL**: `POST /api/videos`
- **Authentication**: Basic Auth (ADMIN role required)
- **Request Body**:
  ```json
  {
    "title": "The Matrix",
    "director": "Lana and Lilly Wachowski",
    "genre": "Sci-Fi",
    "available": true
  }
  ```
- **Response**: 201 Created

#### Update Video (Admin Only)
- **URL**: `PUT /api/videos/{id}`
- **Authentication**: Basic Auth (ADMIN role required)
- **Request Body**:
  ```json
  {
    "title": "The Matrix Reloaded",
    "director": "Lana and Lilly Wachowski",
    "genre": "Sci-Fi",
    "available": true
  }
  ```
- **Response**: 200 OK

#### Delete Video (Admin Only)
- **URL**: `DELETE /api/videos/{id}`
- **Authentication**: Basic Auth (ADMIN role required)
- **Response**: 204 No Content

## Testing

### Setup for Testing
1. Register an admin user:
   ```bash
   curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{
       "email": "admin@example.com",
       "password": "admin123",
       "firstName": "Admin",
       "lastName": "User",
       "role": "ADMIN"
     }'
   ```

2. Register a customer:
   ```bash
   curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{
       "email": "customer@example.com",
       "password": "customer123",
       "firstName": "Customer",
       "lastName": "User"
     }'
   ```

3. Login and use the API with appropriate credentials.

## Project Structure
```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── qvideo
│   │               ├── config
│   │               │   └── SecurityConfig.java
│   │               ├── controller
│   │               │   ├── AuthController.java
│   │               │   └── VideoController.java
│   │               ├── dto
│   │               │   ├── UserDto.java
│   │               │   └── VideoDto.java
│   │               ├── exception
│   │               │   └── GlobalExceptionHandler.java
│   │               ├── model
│   │               │   ├── User.java
│   │               │   └── Video.java
│   │               ├── repository
│   │               │   ├── UserRepository.java
│   │               │   └── VideoRepository.java
│   │               ├── service
│   │               │   ├── AuthService.java
│   │               │   ├── CustomUserDetailsService.java
│   │               │   └── VideoService.java
│   │               └── RentVideoApplication.java
│   └── resources
│       └── application.properties
└── test
    └── java
        └── com
            └── example
                └── rentvideo
                    └── service
                        └── VideoServiceTest.java
```

