#!/bin/bash
# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
echo "Stopping any running containers..."
docker-compose stop

echo "Removing any previous containers..."
docker-compose rm -f

# Start new deployment
echo "Starting new deployment..."
docker-compose up --build -d

echo "Deployment successful!"