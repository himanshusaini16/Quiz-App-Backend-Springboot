# Stage 1: Build the app
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/Question-service-0.0.1-SNAPSHOT.jar question-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "question-service.jar"]
