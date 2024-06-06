# **Expense Tracker Project**

The Expense Tracker project is a comprehensive application designed to help users manage their expenses efficiently.

## **Features**

1. **User Authentication and Authorization:**
    - Implemented using Spring Security and JWT.
    - Users can register and log in to access the application.
    - Secured endpoints to ensure only authenticated users can access their expense data.

2. **Expense Management:**
    - Users can create, read, update, and delete (CRUD) their expenses.
    - Expenses are stored in a MySQL database.

3. **Category Management:**
    - Users can create and manage expense categories.
    - Each expense can be assigned to a specific category for better organization.

## **Technologies Used**

1. **Spring Boot:**
    - Core framework used for building the RESTful APIs.
    - Simplifies dependency management and application configuration.

2. **Spring Data JPA:**
    - Provides an abstraction layer over the database operations.
    - Makes it easier to perform CRUD operations and manage database entities.

3. **Spring Security:**
    - Used to secure the application with authentication and authorization mechanisms.
    - Implemented JWT (JSON Web Token) for stateless authentication.

4. **MySQL Database:**
    - Used as the primary database to store user and expense data.
    - Integrated with Spring Data JPA for seamless database operations.

5. **Docker:**
    - Containerized the application to ensure consistency across different environments.
    - Facilitates easier deployment and scalability.
