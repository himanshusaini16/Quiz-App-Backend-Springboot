# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Service Registry JAR file into the container
COPY target/Service-registry-0.0.1-SNAPSHOT.jar service-registry.jar

# Expose port 8761 (default Eureka port)
EXPOSE 8761

# Run the JAR file
ENTRYPOINT ["java", "-jar", "service-registry.jar"]
