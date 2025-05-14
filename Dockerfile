FROM ubuntu:latest
LABEL authors="Mehdi"

ENTRYPOINT ["top", "-b"]

FROM openjdk:21-jdk-slim
COPY target/classes/com/ziplink-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]