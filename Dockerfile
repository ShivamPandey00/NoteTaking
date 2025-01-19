# Stage 1: Build the JAR file using Maven
FROM maven:3.9.4-eclipse-temurin-8 AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Package the application (skipping tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR file using a lightweight Java runtime
FROM eclipse-temurin:8-jre

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
