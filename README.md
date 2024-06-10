# Security project 
Security Project is a Spring Boot application that uses the basic auth authentication mechanism and saves data in the H2 database.
Endpoints use security mechanisms such as authorization of roles and user permissions.

## Description

## Requirements
For building and running the application you need:
- Java Development Kit (JDK) 17
- Maven 3

## Running the application locally
There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the pl.holowinska.securityproject.SecurityprojectApplication class from your IDE.
Alternatively you can use the Spring Boot Maven plugin like so:
1. Navigate to the project directory.
2. Use Maven to run the Spring Boot application:
    ``` sh
    ./mvnw spring-boot:run
    ```

## Testing
To run the tests, use:
```sh
    ./mvnw test
```

