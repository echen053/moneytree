# Use an official Maven image to build the project
FROM maven:3.8.5-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the project using Maven
RUN mvn clean package

# Use a smaller JRE image for running the app
FROM eclipse-temurin:17-jre

# Set the working directory for the runtime
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/s0-1.0-SNAPSHOT.jar /app/s0-1.0-SNAPSHOT.jar

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "s0-1.0-SNAPSHOT.jar"]