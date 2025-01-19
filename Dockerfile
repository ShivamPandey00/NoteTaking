# Use a minimal Java runtime image to run the application
FROM eclipse-temurin:8-jre

# Set the working directory for the application
WORKDIR /app

# Copy the JAR file from your local target directory into the container
COPY target/notes-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port (8080)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
