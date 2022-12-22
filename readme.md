## How to Run 

### Requirements

- JDK 17
- Maven 3
- in-memory database H2 is used
- `mvn clean`
- `mvn install`
- `mvn spring-boot:run`
- OR run using Docker with following commands in root directory

```
docker build --tag=assessment-usama:latest .  

docker run -p 8080:8080 assessment-usama:latest

For Stopping Docker use 

docker stop assessment-usama
docker rm assessment-usama
```
- initial data for users is in `/resources/data.sql`
- Tests for testing JWT and role based access are in `` src/test/java``
- JWT testing is done using @MockWithUser and Also by generating Token via /token controller

##Application URL: http://localhost:8081

## Documentation
You can access the documentation at `http://localhost:8080/swagger-ui.html` documentation is using swagger

## TODO
- Implement Transaction logic for customers
- Run and compile application in docker container
- Tests for Controller and Repositories

#### IDE: IntelliJ

## Postman Collection 

is available at https://api.postman.com/collections/2853422-30938b65-72c3-4902-900b-91aa654ff62e?access_key=PMAT-01GMXSV9S7PSNRQJ4343E4QZ6Q



