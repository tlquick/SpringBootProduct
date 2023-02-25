#
# Package stage
#
FROM openjdk:17-oracle
COPY target/*.jar products.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","products.jar"]