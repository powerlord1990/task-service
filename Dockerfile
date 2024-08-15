FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/task-service-1.0.0-SNAPSHOT.jar /app/task-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "task-service.jar"]