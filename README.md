# Spring Boot Notes Application Backend

This is the backend service for the Notes application, built using **Spring Boot** and **Spring Data JPA**. It provides APIs for user authentication, note management, and more, and is connected to a PostgreSQL database.

---

## Features

- User authentication with **JWT** (JSON Web Tokens).
- CRUD operations for managing notes.
- Secure, user-specific data access.
- Token-based authentication and authorization.

---

## Tech Stack

- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Deployment**: Render

---

## Prerequisites

Before setting up the project, ensure you have the following installed:

- **Java** (Jdk-1.8)
- **Maven**
- **PostgreSQL** database
- **Git**

---

## Setup Instructions

### 1. Clone the Repository

``
git clone https://github.com/<your-username>/<your-repo>.git
cd <your-repo>
``
### 2. Configure the Database
Create a PostgreSQL database locally or on a cloud platform (e.g., Render, AWS, Azure).
Note the database name, username, password, and host.
### 3. Update Configuration
Update the `application.properties` file in `src/main/resources:`

```
spring.application.name=notes
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
spring.datasource.username=<db-username>
spring.datasource.password=<db-password>
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
### 4. Build the Project
Run the following Maven commands to build the project:
```
./mvnw clean install
```

### 5. Run the Application
Start the application using:

```
./mvnw spring-boot:run
```
### 6. Access the Application
By default, the application runs at `http://localhost:8080`.


## API Endpoints
### Authentication
#### POST /notes/auth/signup
#### Register a new user.
### Request Body:

```
{
  "username": "testuser",
  "password": "password123",
  "name": "Test User",
  "email": "test@example.com"
}
```
### Response:
```
{
  "message": "User registered successfully!"
}
```
#### POST /notes/auth/login
#### Authenticate a user and return a JWT token.
### Request Body:

```
{
  "username": "testuser",
  "password": "password123"
}
```
### Response:
```
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```
### Notes
#### GET /notes/list
#### Get all notes for the logged-in user.
### Headers:
```
{
  "Authorization": "Bearer <JWT-TOKEN>"
}
```
#### POST /notes/create
#### Create a new note.
### Request Body:
```
{
  "title": "My Note",
  "content": "This is the content of the note.",
  "category": "Personal"
}
```
#### DELETE /notes/delete/{id}
##### Delete a note by `ID`.

#### GET /notes/search/{keyword}
##### Search notes by title or category.

## Security Features

#### JWT Authentication:
Tokens are generated during login and must be included in the Authorization header for secured endpoints.
#### Role-Based Access: 
Each user can only access their own notes.
## Troubleshooting
### Common Issues
#### 1. Application Fails to Start
Ensure the database is running and the application.properties file is correctly configured.
#### 2. 401 Unauthorized
Ensure you're including a valid JWT token in the Authorization header for secured endpoints.
### 3. Database Errors
Verify that the database is accessible and credentials are correct.

## Contact
For any questions or support, please reach out to [Shivam Pandey](mailto:shivampandey870@gmail.com)

---
