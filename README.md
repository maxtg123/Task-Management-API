![Untitled](https://github.com/user-attachments/assets/465c8175-5f7f-4d7b-9255-f979cc388bcd)
# Task Management API

A simple task management application built with a Spring Boot back-end and Angular front-end. This project provides user authentication (register/login) and task management (create, read, update, delete) functionalities, with data stored in a MySQL database.
Table of Contents
Features

Architecture

Diagram

Functional Flow

Technologies

Prerequisites

Setup

Running the Application

API Endpoints

Swagger Documentation

Database Schema

Contributing

License

Features
✅ User Authentication: JWT-based register/login.

✅ Task Management: Create, view, update, and delete tasks.

✅ Swagger UI: Interactive API documentation and testing.

✅ Responsive Frontend: Built using Angular.

Architecture
This is a monolithic application with:

Frontend: Angular (UI layer).

Backend: Spring Boot (REST API with layered architecture).

Database: MySQL.

Layers in the back-end:

Controller Layer: Handles incoming API requests.

Service Layer: Business logic.

Repository Layer: Data persistence.

Diagram
plantuml
Copy
Edit
@startuml
actor User

package "Task-Management-API (Monolith)" {
[Angular Frontend] #--> [Spring Boot Backend]

package "Spring Boot Backend" {
[Controller Layer] --> [Service Layer]
[Service Layer] --> [Repository Layer]
[Repository Layer] --> [MySQL Database]
}
}

[User] --> [Angular Frontend]

note right of [Angular Frontend]
Sends HTTP requests to:
- /api/auth/*
- /api/tasks
  end note
  @enduml
  Functional Flow
  a. Register
  User fills out form.

Frontend sends POST /api/auth/register.

Backend creates user, returns JWT.

Frontend saves token in localStorage.

b. Login
User submits login credentials.

Frontend sends POST /api/auth/login.

Backend authenticates, returns JWT.

Frontend stores token and navigates to dashboard.

c. Task Management (CRUD)
GET /api/tasks

POST /api/tasks

PUT /api/tasks/{id}

DELETE /api/tasks/{id}

All require the Authorization: Bearer <token> header.

Technologies
Back-end
Spring Boot 3.4.4

Spring Data JPA

Spring Security (JWT)

Springdoc OpenAPI (Swagger)

MySQL Connector

Lombok

Front-end
Angular 17.x

TypeScript, HTML, CSS

Database
MySQL 8.x

Tools
Maven

IntelliJ IDEA

Node.js / NPM

Prerequisites
JDK 17+

Node.js 20+

MySQL 8+

Maven 3.8+

IntelliJ IDEA

Setup
Backend
bash
Copy
Edit
git clone <repository-url>
cd backend
mvn clean install
Frontend
bash
Copy
Edit
cd frontend
npm install
ng serve
Access at: http://localhost:4200

Running the Application
Start MySQL and ensure schema is set up.

Run the Spring Boot app.

Open the Angular app in the browser.

API Endpoints

Method	Endpoint	Description	Auth Required
POST	/api/auth/register	Register new user	❌
POST	/api/auth/login	Authenticate user	❌
GET	/api/tasks	Get all tasks	✅
POST	/api/tasks	Create a new task	✅
PUT	/api/tasks/{id}	Update task by ID	✅
DELETE	/api/tasks/{id}	Delete task by ID	✅
Swagger Documentation
📘 Access your API docs here:

👉 http://localhost:8080/swagger-ui.html
👉 http://localhost:8080/api-docs

Includes all endpoints with test capabilities and schema visualization.

Database Schema
users table
id: Long (PK)

username: String (unique)

password: String (hashed)

email: String (unique)

tasks table
id: Long (PK)

title: String

description: String

completed: Boolean

createdAt: DateTime

user_id: FK → users.id

Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

License
MIT

