# RPG Admin Panel with Hibernate  

This project replaces the in-memory data storage of the RPG admin panel backend with a MySQL database using Hibernate. It demonstrates integrating Hibernate with a Spring Boot application to perform CRUD operations on the "player" entity.  

## Key Features  
- Integration of Hibernate with Spring Boot for database operations.  
- Migration from in-memory storage to MySQL.  
- Logging SQL queries with P6Spy for detailed execution tracking.  

## Prerequisites  
- Java 1.8  
- Maven  
- MySQL 8.0  
- Apache Tomcat (optional for testing frontend integration)  

## Setup and Run  
1. Fork the repository and clone it locally.  
2. Configure database schema in MySQL:  
   ```sql
   CREATE SCHEMA `rpg`;
3. Initialize data using the provided init.sql script.
4. Run the application.
