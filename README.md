# shape-test
vessels, equipments and operation orders.

## Language and Fromeworks
- Java 11
- SpringBoot 2.6.6
- Maven 4.0.0
- SpringData
- Lombok project

## About the structure
- I used MVC pattern approach (that`s the most known project structure for Java projects)
- api package contains the resources (endpoints)
- model package constains the domain classes
- repository package deal with database logic
- service package deal with business logic
- dto package data transfer objects
- configuration package just contains the error handling

## About database (H2)
- I decided to use in memory database to make things simple.
- data.sql contains the database scripts

## How to run the application
- You can run manually in case you have maven and JDK installed on your machine
```
# to to project root folder and run the following commands:

# build project
mvn clean package

# run project
java -jar target/vessel-api.jar
``` 

- I also provided a Dockerfile in case you don`t have JDK and Maven installed
```
# to to project root folder and run the following commands:

# Build container
sudo docker build -t vessel-api-java-container:1.0 .

# Run the container
sudo docker run -d -p 8080:8080 -t vessel-api-java-container:1.0
```
## How to test the application
I provided o postman collection with all services required in root folder.


## Unit test
I wasn't able to test the application due to no time.
I'm high skilled to make tests on all layers by using:
- Mockito
- PowerMock
- Mockmvc
- JUnit 4
- Junit 5

Feel free to ask me about at any time in the next steps
