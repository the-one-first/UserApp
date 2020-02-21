# User App REST using Spring Boot

This is a user application that build using spring boot framework, spring validation, liquibase initialization load data, orika mapper, JUnit4 and Mockito for unit test, and JUnit5 and Mock Mvc for Integration test.

### Build the Application

1. Open the project in Eclipse or Spring Tool Suite
2. Right click on pom.xml and choose Run As, Run Configuration
3. Right click on Maven Build, New Configuration
4. Fill Name with UserApp
5. Choose UserApp workspace by clicking Workspace button
6. Fill Goals with clean install -X
7. Click Apply
8. Click Run

### Run the Application

1. Open command prompt in target folder
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