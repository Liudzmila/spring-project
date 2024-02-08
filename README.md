# Spring Project

This is a sample Spring project showcasing basic CRUD operations using Spring MVC, Spring Data JPA, and Thymeleaf.

## Prerequisites
- JDK 18 or later
- Apache Maven
- MySQL Server

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/liudzmila/spring-project.git
    ```

2. Navigate to the project directory:

    ```bash
    cd spring-project
    ```

3. Build the project using Maven:

    ```bash
    mvn clean package
    ```

4. Start the MySQL server and execute the `startMySQL.sql` script located in `src/main/resources` to create the necessary database and tables.

5. Deploy the WAR file (`spring-project-1.war`) generated in the `target` directory to Tomcat 10.

6. Access the application at [http://localhost:8080/spring-project-1](http://localhost:8080/spring-project-1).

## Usage

- The application provides basic CRUD functionality for managing tasks.
- Navigate to the tasks page to view, add, edit, and delete tasks.

## Technologies Used

- Spring Framework
- Spring MVC
- Spring Data JPA
- Thymeleaf
- MySQL Database
- Tomcat 10