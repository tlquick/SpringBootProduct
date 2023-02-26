## Spring Boot Products
A project that creates a small api for a product database
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Example Use](#example-use)
* [Live Site](#live-site)

## General Info
The aim of this project is to develop a simple api using MySQL, work with Actuator, Lombok and Logging then create mocked tests for endpoints. The api will be containerised and deployed to Kubernetes. This project is split up into 4 stages: build the api using h2, add the SQL db as a container and use docker compose, create a docker image and pusg to docker hub, then finally to create the deployment yaml files for K8s and push the deployment to Google Cloud Platform (GKE).
## Technologies
Spring Boot 3.0, Maven, Java 17, Actuator, Lombok, Swagger, JUnit, and Docker Desktop

POM dependancies: 
spring-boot-starter-data-jpa, spring-boot-starter-web, mysql-connector-java, spring-boot-starter-test, junit-jupiter-engine, mockito-core, springdoc-openapi-starter-webmvc-ui, lombok, hibernate-validator, spring-boot-starter-actuator, spring-boot-devtools, spring-security-test, dockerfile-maven-plugin.

## Setup
Besides the usual setup of Eclipse, jdk and maven, Docker desktop needs to be installed locally with a valid Docker Hub account. Kompose needs to be installed locally, along with Google CLoud console (GCloud) attached to a valid GCP account. Mysqlsh should also be installed (to test db connection) for the MySQL container. 

### Version 1: 
Small spring boot api for a product table using: <br>
 H2 db, swagger, lombok, actuator. JUnit tests use Mockito and MockMVC <br>
 
### Version 2: 
Uses MySQL 8.0 - test with a docker container eg: run <br>
 ```
 docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=dummypassword -e MYSQL_DATABASE=products -e MYSQL_USER=product-user -e MYSQL_PASSWORD=dummyproduct mysql:8.0
```
before running the app. <br>
Create a maven clean install to create the jar file while sql container is running <br>
Then stop the sql container in Docker Desktop and run:
```
docker compose up
```
To create the app in a docker container with an associated sql container <br>
### Version 3:
Added dockerfile-maven-plugin to pom.xml to allow docker image creation <br>
Change the repository name from tlquick to your dockerhub username then run:
```
docker login
```
to ensure you are logged in to Docker Hub then
```
mvn clean install -DskipTests
```
to create the docker image then
```
docker push <dockerUser>/products:<Version>
```
For example: I used docker 'push tlquick/products:0.0.1-SNAPSHOT'
Note: you can also uncomment <goal>push</goal> in the dockerfile-maven-plugin to do this task
### Version 4: 
Changed the docker-compose.yml to pull a docker image and  Kompose to create kubernetes yaml files eg
```
kompose convert -f docker-compose.yml
```
I edited the yaml files to remove unused properties like annotations & status etc. <br>
Make sure Kubernetes (kubectl) is installed on the local machine and run:
```
kubectl apply -f db-persistentvolumeclaim.yaml,db-deployment.yaml,db-service.yaml
```
to create the database and then 

```
kubectl apply -f api-deployment.yaml,api-service.yaml
```
to launch the api in k8s.
## Example Use
You can explore the available endpoints by navigating to /swagger-ui.html. <br>
![ProductRun](/product_example.png?raw=true "Example")
To view the contents of the database navigate to /v1/products. <br>
Each item can be viewed by id e.g /v1/products/1 will display the product with id 1 <br>
To change data in the database, you need to use curl in a bash shell (linux) <br>
To add a product to the database use /v1/products to pass the payload for the POST e.g,
```
curl -X POST http://34.134.146.171:8080/v1/products -H "Content-Type: application/json" -d '{"name":"something", "description":"A very nice something", "price":10.00}' 
```
PUT and DELETE are also available - check the swagger docs.

## Live Site
Here is my Live Site: http://34.134.146.171:8080/
