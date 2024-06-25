# Stage 1: Build the application
FROM maven:3.8.6-openjdk-17-slim as BUILDER

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and other necessary files to the working directory
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline

# Copy the source code to the container
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=BUILDER /app/target/*warehouse-management*.jar /app/warehouse-management.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/warehouse-management.jar"]