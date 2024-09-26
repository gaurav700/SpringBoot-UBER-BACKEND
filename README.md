# UBER-BACKEND-PROJECT

This project is a backend service built using Spring Boot for managing a ride-hailing application like Uber. It provides essential services such as managing user wallets, transactions, rides, and more. The application uses PostgreSQL as the database and is configured to send transactional emails using Gmail's SMTP server.

---

## Table of Contents
1. [Project Overview](#project-overview)
2. [Technologies Used](#technologies-used)
3. [Prerequisites](#prerequisites)
4. [Configuration](#configuration)
5. [Database Setup](#database-setup)
6. [Running the Project](#running-the-project)
7. [Database Schema](#database-schema)
8. [JWT Security](#jwt-security)
9. [Mail Configuration](#mail-configuration)
10. [Data Initialization](#data-initialization)

---

## Project Overview

The **UBER-BACKEND-PROJECT** is a backend service that provides functionalities for:
- Managing user rides and drivers
- Handling user wallet operations (adding/deducting balance, transactions)
- Managing user authentication and authorization using JWT
- Sending transactional emails using Gmail SMTP server

This backend is structured with a service-layer approach and relies on model entities like `User`, `Ride`, `Wallet`, `Transaction`, etc., for its operations.

---

## Technologies Used
- **Spring Boot** - Backend framework
- **PostgreSQL** - Relational database
- **Hibernate** - ORM for interacting with the database
- **JWT (JSON Web Tokens)** - Security for user authentication
- **Gmail SMTP** - Email configuration for notifications
- **Lombok** - For reducing boilerplate code

---

## Prerequisites

Make sure you have the following installed on your system:
- **Java 17** or higher
- **Maven** (to build and manage dependencies)
- **PostgreSQL** (for database setup)

---

## Configuration

All configurations are managed through the `application.properties` file. The most critical configurations include database credentials, JWT secret key, and mail server settings.

### Database Configuration:
Update the following properties to match your local PostgreSQL setup:

```properties
spring.application.name=UBER-BACKEND-PROJECT

# database configurations
spring.datasource.url=jdbc:postgresql://localhost:5432/<database-name>?useSSL=false
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show.sql=true
spring.jpa.properties.hibernate.format_sql=true

### data dummy initialization (use these data.sql file when you want to insert data on build)
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data.sql

#security config
jwt.secretKey = <any-secret-key>;

# mail configurations
spring.mail.username=<email>
spring.mail.password=<app= password>  genearted in the google account
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true


