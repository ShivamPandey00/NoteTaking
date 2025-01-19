# Build Stage
FROM maven:3.8.4-openjdk-8 AS build

WORKDIR /app

# Copy pom.xml and download dependencies to cache them
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code into the container
COPY src/ ./src

# Build the application and skip tests
RUN mvn clean package -DskipTests

# Runtime Stage
FROM openjdk:8-jdk-slim

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/notes-0.0.1-SNAPSHOT.jar /app/notes-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/notes-0.0.1-SNAPSHOT.jar"]
