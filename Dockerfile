# Stage 1: Build the jar using Gradle
FROM gradle:9.2.1-jdk17 AS builder

# Set working directory
WORKDIR /app

# Copy source code
COPY . .

# Build the jar
RUN gradle bootJar --no-daemon

# Stage 2: Create lightweight image to run the jar
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/build/libs/pastebinlite-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
