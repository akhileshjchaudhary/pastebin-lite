# Pastebin Lite

## Tech Stack
- Java 17
- Spring Boot
- PostgreSQL

## How to Run
1. Configure PostgreSQL database
2. Update application properties with DB credentials
3. Run the application using:
   ./gradlew bootRun

## Persistence
This application uses PostgreSQL to store paste data.

## API Endpoints
- GET /api/healthz
- POST /api/pastes
- GET /api/pastes/{id}
- GET /p/{id}

## Time Handling
Supports deterministic time testing using TEST_MODE and x-test-now-ms header.
