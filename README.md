# Exhibitions Management System

## Overview
This project is a microservices-based **Exhibitions Management System** developed for a university course, designed to provide a scalable and maintainable solution for managing exhibitions, users, and booking operations.

Built with Spring Boot and Spring Cloud, the system follows modern microservices architecture principles such as service discovery, centralized configuration, and API gateway routing. The project focuses on backend development and does not include a frontend application.

---

## Project Structure

The system consists of the following microservices:

- **API Gateway** – Entry point for all client requests, handles routing and security  
- **Config Server** – Centralized configuration management  
- **Eureka Server** – Service discovery and registration  
- **Exhibition Service** – Manages exhibitions and tickets  
- **User Service** – Handles user management and authentication  
- **Booking Service** – Manages booking operations  

---

## Technology Stack

- **Java 17**
- **Spring Boot 4.0.5**
- **Spring Cloud 2025.1.1**
- **Maven** (with mvnw wrappers)
- **Spring Cloud Gateway** (WebFlux)
- **Netflix Eureka**
- **Spring Cloud Config Server**
- **Spring Data JPA**
- **MySQL**
- **OpenFeign + LoadBalancer**
- **Spring Security**
- **Jakarta Bean Validation**
- **Lombok**

---

## Service Endpoints

### API Gateway
- Base URL: `http://localhost:8765`

### Eureka Server
- Dashboard: `http://localhost:8761`

### Microservices
- User Service: `http://localhost:8001`
- Exhibition Service: `http://localhost:8002`
- Booking Service: `http://localhost:8003`

## API Gateway Security

The API Gateway uses an API key-based authentication mechanism. All requests (except registration and login) **must include an `x-api-key` HTTP header**.
### API Keys
| Role  | API Key       |
|-------|----------------|
| USER  | `userKey123`   |
| ADMIN | `adminKey123`  |

### Open Endpoints (no API key required)
- `/users/register`
- `/users/login`

### Protected Endpoints

> The following routes **require the `x-api-key` header**:

#### Admin-only access (`x-api-key: adminKey123`)
- `/users/admin/**`
- `/exhibitions/admin/**`
- `/artists/admin/**`

#### User or Admin access (`x-api-key: userKey123` or `adminKey123`)
- `/bookings/**`

### Denied Endpoints
Any other endpoints not listed above will be denied with a `403 Forbidden` response.

---

## ▶️ How to Run

1. Start Config Server
2. Start Eureka Server
3. Start all microservices:
   - User Service
   - Exhibition Service
   - Booking Service
4. Start API Gateway

Make sure MySQL is running and properly configured in `application.yml`.

Access the system via:
http://localhost:8765

## Database Model
<img width="1237" height="726" alt="er_model" src="https://github.com/user-attachments/assets/a7d7938b-b158-40ed-940e-898d8bcc996a" />

## Database Setup

To set up the initial data in the database, add the following rows to the `role` table:

- **Row 1**
    - `id`: 1
    - `name`: ADMIN

- **Row 2**
    - `id`: 2
    - `name`: USER
