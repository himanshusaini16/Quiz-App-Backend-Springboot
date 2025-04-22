# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy and build the project
COPY . .
RUN mvn clean package -DskipTests

# ---------- Stage 2: Runtime ----------
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the built JAR file
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Expose API Gateway port
EXPOSE 8083

# Run the Gateway
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
