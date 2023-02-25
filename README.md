# SpringBootProducts
## Version 1: 
Small spring boot api for a product table using: <br>
 H2 db, swagger, lombok, actuator. JUnit tests use Mockito and MockMVC <br>
 
## Version 2: 
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
## Version 3:
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
## Version 4: 
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

### Live Site
If you make sure you have gcloud installed and have a valid GCP account attached, you can deploy directly to GKE with the above steps.<br>
Here is my Live Site: http://34.134.146.171:8080/
