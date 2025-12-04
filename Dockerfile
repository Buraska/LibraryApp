FROM maven:3.9.11-eclipse-temurin-11-alpine
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM eclipse-temurin:17-jdk-jammy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
