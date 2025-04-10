# Use an official OpenJDK image as base
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/survey-app-0.0.1-SNAPSHOT.jar app.jar

# Expose port for Kubernetes
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
