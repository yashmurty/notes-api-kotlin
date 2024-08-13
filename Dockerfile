# Use a smaller base image for the runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the local file system
COPY app.jar app.jar

# Expose the port and run the application
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
