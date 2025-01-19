# Use an official Maven image to build the project
FROM maven:3.8.5-eclipse-temurin-17 AS build

# Set the working directory to /server
WORKDIR /server

# Copy the project files into the container
COPY . .

# Build the project using Maven (skip tests if not needed during build)
RUN mvn clean package -DskipTests

# Use a smaller JRE image for running the app
FROM eclipse-temurin:21-jre

# Set the working directory to /server
WORKDIR /server

# Copy the built JAR file and run script from the build stage
COPY --from=build /server/target/s0-1.0-SNAPSHOT.jar /server/target/s0-1.0-SNAPSHOT.jar

# Expose port 3232 (the port your server runs on)
EXPOSE 3232

# Command to run the application using the run script
# CMD ["java", "-jar", "target/s0-1.0-SNAPSHOT.jar"]
CMD ["java", "-cp", "/etc/secrets/:target/s0-1.0-SNAPSHOT.jar", "edu.brown.cs.student.main.server.Server"]