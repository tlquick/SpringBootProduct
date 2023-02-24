# SpringBootProducts
Version 1: Small spring boot api for a product table using: <br>
 H2 db, swagger, lombok, actuator. JUnit tests use Mockito and MockMVC <br>
 
 Version 2: use MySQL 8.0 - test with a docker container eg: run <br>
 '''
 docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=dummypassword -e MYSQL_DATABASE=products -e MYSQL_USER=product-user -e MYSQL_PASSWORD=dummyproduct mysql:8.0
 '''
before running the app. <br>