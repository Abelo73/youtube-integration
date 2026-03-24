# Use official Maven + JDK image for building
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy all source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# --- Stage 2: Run application ---
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/youtube-integration-0.0.1-SNAPSHOT.jar ./youtube-integration.jar

# Expose port
EXPOSE 8080

# Set environment variable placeholder
ENV YOUTUBE_API_KEY=""

# Run the application
ENTRYPOINT ["java", "-jar", "youtube-integration.jar"]