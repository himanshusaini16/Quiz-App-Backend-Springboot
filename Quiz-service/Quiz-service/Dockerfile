# Stage 1: Build the app
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Use local Maven cache to avoid re-downloading
VOLUME ["/root/.m2"]
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/Quiz-service-0.0.1-SNAPSHOT.jar quiz-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "quiz-service.jar"]
