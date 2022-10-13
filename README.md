#ReadingIsGood

##INTRODUCTION

ReadingIsGood is an online books retail firm which operates only on the Internet. Main target of ReadingIsGood is to deliver books from its one centralized warehouse to their customers within the same day. That is why stock consistency is the first priority for their vision operations.

##REQUIREMENTS

    JDK version 11
    Spring Boot version 2.7.4
    Maven version 3.6.3 
    
    
##TECH STACK

    Java
    Spring Boot
    Spring Security
    Spring Data
    Spring Logging
    Spring Validation
    Spring Test
    Spring Retry
    JUnit
    Mockito
    H2 Database
    Log4j
    Swagger2
    JWT
    Lombok
    Docker


##RUN USING DOCKER

You can start the project with docker.

    docker compose up reading-is-good
    
##Database

    You can use the H2-Console for exploring the database under http://localhost:8080/h2

##Customer

    You can use the customer api for creating customer. 'api/v1/customers'

##Auth

    You can use the auth api for creating jwt token. '/api/v1/auth'
    You must add jwt token for all request.


##RUN TEST
  
    If you want to run tests all together, you can use the commands below
    
    1-) mvn test : Run tests
    2-) mvn clean install -Dit : Clears the target directory and builds the project and installs the resulting artifact (JAR) with unit and integration tests
    3-) mvn clean package -Dmaven.test.skip=true : Clears the target directory and builds the project and packages the resulting JAR file into the target directory
       without running the unit tests during the build.
    
    if you want to run tests one by one, you can use run button of your own ide in test class. 
    
