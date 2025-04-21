# Step 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the built JAR using OpenJDK
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/Service-registry-0.0.1-SNAPSHOT.jar service-registry.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "service-registry.jar"]
