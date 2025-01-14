# Use an official Maven image to build the project
FROM maven:3.8.5-eclipse-temurin-17 AS build

# Set the working directory to /server
WORKDIR /server

# Copy the project files into the container
COPY . .

# Build the project using Maven
RUN mvn clean package

# Use a smaller JRE image for running the app
FROM eclipse-temurin:17-jre

# Set the working directory to /server
WORKDIR /server

# Copy the built JAR file from the build stage
COPY --from=build /server/target/s0-1.0-SNAPSHOT.jar /server/s0-1.0-SNAPSHOT.jar

# Expose port 3232 (the port your server runs on)
EXPOSE 3232

# Command to run the application
CMD ["java", "-jar", "s0-1.0-SNAPSHOT.jar"]