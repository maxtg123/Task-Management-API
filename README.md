![Untitled](https://github.com/user-attachments/assets/465c8175-5f7f-4d7b-9255-f979cc388bcd)
# Task Management API

A simple task management application built with a Spring Boot back-end and Angular front-end. This project provides user authentication (register/login) and task management (create, read, update, delete) functionalities, with data stored in a MySQL database.

## Table of Contents
- [Features](#features)
- [Architecture](#architecture)
- [Diagram](#diagram)
- [Functional Flow](#functional-flow)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)

## Features
- **User Authentication**: Register and login with JWT-based authentication.
- **Task Management**: Create, view, update, and delete tasks associated with authenticated users.
- **Responsive UI**: Built with Angular for a user-friendly interface.

## Architecture
The application follows a monolithic architecture:
- **Front-end**: Angular handles the UI and sends HTTP requests to the back-end.
- **Back-end**: Spring Boot processes requests through layered components:
  - **Controller Layer**: Handles REST API endpoints.
  - **Service Layer**: Contains business logic (authentication, task operations).
  - **Repository Layer**: Interacts with the MySQL database.
- **Database**: MySQL stores user and task data.

## Diagram
The architecture diagram displays the following components:
- **User**: The individual interacting with the system.
- **Angular Frontend**: Sends requests to the backend.
- **Spring Boot Backend**: A monolith with multiple layers:
  - **Controller Layer**: Receives requests from the frontend.
  - **Service Layer**: Processes logic (authentication, task operations).
  - **Repository Layer**: Interacts with the database.
- **MySQL Database**: Stores the `users` and `tasks` tables.

### PlantUML Code for Diagram
```plantuml
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
  Sends HTTP requests (REST API)
  to backend via endpoints:
  - /api/auth/register
  - /api/auth/login
  - /api/tasks (GET, POST, PUT, DELETE)
end note

note right of [Spring Boot Backend]
  Processes logic:
  - Authentication (JWT)
  - Task CRUD
  - Stores data in MySQL
end note


Functional Flow
Below is the functional flow of the Task-Management-API in its monolithic form:

a. Register
User: Enters username, password, and email on the Angular interface.
Angular Frontend: Sends a POST /api/auth/register request with form data (application/x-www-form-urlencoded).
Spring Boot Backend:
AuthController: Receives the request and calls AuthService.
AuthService: Encrypts the password (using BCrypt), creates a User object, and saves it to the users table via UserRepository.
JwtUtil: Generates a JWT token based on the username.
Returns the token to the frontend.
Angular Frontend: Stores the token in localStorage and redirects to /tasks.
b. Login
User: Enters username and password.
Angular Frontend: Sends a POST /api/auth/login request.
Spring Boot Backend:
AuthController: Calls AuthService.
AuthService: Retrieves the User from UserRepository, verifies the password, and generates a token using JwtUtil.
Returns the token.
Angular Frontend: Stores the token and redirects to /tasks.
c. Task Management (CRUD)
User: Interacts with the Angular interface (view, add, update, delete tasks).
Angular Frontend:
Sends requests to:
GET /api/tasks: Retrieves the list of tasks.
POST /api/tasks: Creates a new task.
PUT /api/tasks/{id}: Updates a task.
DELETE /api/tasks/{id}: Deletes a task.
Attaches the Authorization: Bearer <token> header.
Spring Boot Backend:
TaskController: Receives the request, extracts the username from the token using JwtUtil, and retrieves the User via UserRepository.
TaskService: Performs CRUD operations on Task objects through TaskRepository.
TaskRepository: Saves or retrieves data from the tasks table in MySQL.
Returns the result (task list, new task, or HTTP 200 status).
Angular Frontend: Updates the UI based on the response.
d. Data Storage
MySQL Database:
Table users: Stores id, username, password, and email.
Table tasks: Stores id, title, description, completed, createdAt, and user_id (foreign key referencing users).
Technologies
Back-end:
Spring Boot 3.4.4
Spring Data JPA
Spring Security (JWT)
MySQL Connector
Lombok
Front-end:
Angular 17.x
TypeScript
HTML/CSS
Database: MySQL 8.x
Tools:
Maven (build tool)
IntelliJ IDEA (IDE)
Node.js/NPM (for Angular)
Prerequisites
Java: JDK 17 or higher
Node.js: 20.x (LTS) or higher
MySQL: 8.x
Maven: 3.8.x or higher
IntelliJ IDEA: Recommended IDE
Setup
Back-end
Clone the repository:
bash

Collapse

Wrap

Copy
git clone https://github.com/maxtg123/Task-Management-API.git
cd Task-Management-API
Install MySQL:
Install MySQL locally or use Docker:
bash

Collapse

Wrap

Copy
docker run -p 3306:3306 --name mysql-container -e MYSQL_ROOT_PASSWORD=your_password -e MYSQL_DATABASE=task_management_db -d mysql:latest
Configure application.properties:
Edit src/main/resources/application.properties:
properties

Collapse

Wrap

Copy
spring.datasource.url=jdbc:mysql://localhost:3306/task_management_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
Build the project:
bash

Collapse

Wrap

Copy
mvn clean install
Front-end
Navigate to frontend folder (if separate):
bash

Collapse

Wrap

Copy
cd frontend
Install dependencies:
bash

Collapse

Wrap

Copy
npm install
Running the Application
Run Back-end:
In IntelliJ: Open TaskManagementApiApplication.java, right-click > Run.
Or via terminal:
bash

Collapse

Wrap

Copy
mvn spring-boot:run
Run Front-end:
In terminal:
bash

Collapse

Wrap

Copy
cd frontend
ng serve
Open browser: http://localhost:4200.
API Endpoints
Method	Endpoint	Description	Request Body (Form)	Headers
POST	/api/auth/register	Register a new user	username, password, email	
POST	/api/auth/login	Login and get JWT token	username, password	
GET	/api/tasks	Get all tasks for user		Authorization: Bearer <token>
POST	/api/tasks	Create a new task	title, description	Authorization: Bearer <token>
PUT	/api/tasks/{id}	Update a task	title, description, completed	Authorization: Bearer <token>
DELETE	/api/tasks/{id}	Delete a task		Authorization: Bearer <token>
Database Schema
Table users:
id (PK, Long)
username (String, unique)
password (String, hashed)
email (String, unique)
Table tasks:
id (PK, Long)
title (String)
description (String)
completed (Boolean)
createdAt (DateTime)
user_id (FK to users.id)



