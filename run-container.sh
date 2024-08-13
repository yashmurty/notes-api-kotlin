#!/bin/bash

# Stop the running containers
echo "Stopping running containers..."
docker compose down app

# Build the JAR file using Gradle
echo "Building the JAR file with Gradle..."
./gradlew build --no-daemon

# Copy the JAR file to the current directory (or wherever the Dockerfile is located)
cp "$(ls build/libs/*.jar | grep -v 'plain' | head -1)" app.jar

# Rebuild only the app service
echo "Rebuilding the app service..."
docker compose build --no-cache app

# Run docker compose up
echo "Running docker compose up..."
docker compose up app

echo "Process completed. Your application should now be running with the latest code."
