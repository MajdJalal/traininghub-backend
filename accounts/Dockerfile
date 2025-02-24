# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory
COPY target/accounts-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8080

# Set the default command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
