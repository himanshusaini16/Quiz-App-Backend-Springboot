# Step 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Create the runtime image
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the JAR file from the builder image
COPY --from=builder /app/target/Service-registry-0.0.1-SNAPSHOT.jar service-registry.jar

# Expose port 8761 (default Eureka port)
EXPOSE 8761

# Command to run the application
ENTRYPOINT ["java", "-jar", "service-registry.jar"]
