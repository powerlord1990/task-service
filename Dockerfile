FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/task-management-system-1.0.0-SNAPSHOT.jar /app/task-management-system.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "task-management-system.jar"]