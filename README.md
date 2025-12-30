# Pastebin Lite

A lightweight Pastebin-like application built with Spring Boot. Users can create text pastes, optionally with time-based expiry or view-count limits, and share a link to view them.

---

## Tech Stack

* Java 17
* Spring Boot
* PostgreSQL
* Docker

---

## How to Run Locally

1. Set up a PostgreSQL database locally or on a cloud provider.
2. Update `src/main/resources/application.properties` with your database credentials:

   ```
   spring.datasource.url=jdbc:postgresql://<DB_HOST>:<DB_PORT>/<DB_NAME>
   spring.datasource.username=<DB_USER>
   spring.datasource.password=<DB_PASSWORD>
   ```
3. Build the application:

   ```bash
   ./gradlew bootJar
   ```
4. Run the application:

   ```bash
   java -jar build/libs/pastebinlite-0.0.1-SNAPSHOT.jar
   ```
5. Access the app on `http://localhost:8080`.

---

## Persistence

This application uses **PostgreSQL** to store paste data. The persistence layer survives across requests and supports deterministic expiry testing.

---

## Docker Deployment

To deploy with Docker:

1. Make sure the `Dockerfile` is in the project root.
2. Build the Docker image:

   ```bash
   docker build -t pastebin-lite .
   ```
3. Run the container:

   ```bash
   docker run -p 8080:8080 -e DB_HOST=<host> -e DB_PORT=5432 -e DB_NAME=<db> -e DB_USER=<user> -e DB_PASSWORD=<password> -e TEST_MODE=1 pastebin-lite
   ```

> Render deployment URL: [https://pastebin-lite-mkk7.onrender.com](https://pastebin-lite-mkk7.onrender.com)

---

## API Endpoints

* **Health Check**

  ```
  GET /api/healthz
  ```

  Response:

  ```json
  { "ok": true }
  ```

* **Create a Paste**

  ```
  POST /api/pastes
  ```

  Body example:

  ```json
  {
    "content": "Hello World",
    "ttl_seconds": 60,
    "max_views": 5
  }
  ```

  Response:

  ```json
  {
    "id": "abc123",
    "url": "https://pastebin-lite-mkk7.onrender.com/p/abc123"
  }
  ```

* **Fetch a Paste (API)**

  ```
  GET /api/pastes/{id}
  ```

  Response:

  ```json
  {
    "content": "Hello World",
    "remaining_views": 4,
    "expires_at": "2026-01-01T00:00:00.000Z"
  }
  ```

* **View a Paste (HTML)**

  ```
  GET /p/{id}
  ```

  Returns HTML page with paste content.

---

## Time Handling

Supports deterministic testing using:

* Environment variable: `TEST_MODE=1`
* Request header: `x-test-now-ms` (milliseconds since epoch)

---

## Notes / Design Decisions

* **Docker multi-stage build** used to build the jar and create a lightweight runtime image.
* **PostgreSQL** hosted on Render (free instance) for persistence.
* **Environment variables** used for database credentials and deterministic testing.
* Paste becomes unavailable when **TTL expires** or **view count is exceeded**, whichever occurs first.
