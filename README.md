# User App REST using Spring Boot

This is a user application that build using spring boot framework, spring validation, liquibase initialization load data, orika mapper, JUnit4 and Mockito for unit test, and JUnit5 and Mock Mvc for Integration test.

### Build the Application

1. Open the project in Eclipse or Spring Tool Suite
2. Right click on build.gradle and choose Run As, Run Configuration
3. Right click on Gradle Task, New Configuration
4. Fill Name with UserApp
5. Choose UserApp workspace by clicking File System button
6. Fill Gradle Tasks with clean in first row, and bootJar in second row
7. Click Apply
8. Click Run

### Run the Application

1. Open command prompt in build/libs folder
2. Run below command
```sh
    java -jar UserApp-0.0.1-SNAPSHOT.jar
```
3. Open browser and go to http://localhost:8080
4. It should be appear Whiteable Error Page
5. You can make sure the local Database H2 is up too using this URL http://localhost:8080/h2-console

### Test the Application

1. Open postman application
2. Import UserAppCollectionDoc.postman_collection.json
3. Start test the REST API service

### Run Unit Test for the Application

1. Open command prompt in root application folder
2. Run below command
```sh
    gradle test --info
```
3. You can see the result in the command prompt screen

### Run Integration Test for the Application

1. Open command prompt in root application folder
2. Run below command
```sh
    gradle integrationTest --info
```
3. You can see the result in the command prompt screen

### Run Unit Test and Integration Test for the Application

1. Open command prompt in root application folder
2. Run below command
```sh
    gradle integrationTest test --info
```
3. You can see the result in the command prompt screen