# Quick Start Guide

This guide will help you get the Collections Management API up and running quickly.

## Prerequisites

- Docker and Docker Compose installed on your system
- (Alternative) Java 17 and Maven if you want to run without Docker

## Option 1: Quick Start with Docker (Recommended)

This is the easiest way to get started. Docker will handle all the dependencies.

### 1. Clone the repository

```bash
git clone https://github.com/JaviSP/collections-back.git
cd collections-back
```

### 2. Start the application

```bash
docker-compose up --build
```

This command will:
- Build the Spring Boot application
- Start a PostgreSQL database
- Start the API server

Wait for the logs to show "Started CollectionsBackApplication" (takes about 30-60 seconds on first run).

### 3. Verify it's running

The API will be available at: `http://localhost:8080/api`

Test it with:
```bash
curl http://localhost:8080/api/users
```

You should get an empty array response: `[]`

### 4. Try creating a user

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com"
  }'
```

### 5. Stop the application

Press `Ctrl+C` in the terminal, then run:
```bash
docker-compose down
```

## Option 2: Running Locally (without Docker)

### 1. Install PostgreSQL

Make sure PostgreSQL is running on your system at `localhost:5432`.

### 2. Create the database

```bash
psql -U postgres
CREATE DATABASE collections_db;
\q
```

### 3. Build and run the application

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be available at: `http://localhost:8080/api`

## Next Steps

1. Check out [API_EXAMPLES.md](./API_EXAMPLES.md) for detailed API usage examples
2. Read the [README.md](./README.md) for complete documentation
3. Start creating collections!

## Example Workflow

Here's a simple example to create a collection and add an item:

### 1. Create a user

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "email": "john@example.com"}'
```

Save the returned `id` (let's say it's `1`).

### 2. Create a collection

```bash
curl -X POST http://localhost:8080/api/collections \
  -H "Content-Type: application/json" \
  -d '{
    "name": "My Stickers",
    "description": "My sticker collection",
    "userId": 1,
    "fields": [
      {"fieldName": "Name", "fieldType": "TEXT", "required": true, "order": 1},
      {"fieldName": "Country", "fieldType": "TEXT", "required": false, "order": 2}
    ]
  }'
```

Save the collection `id` and field `id` values.

### 3. Add an item

```bash
curl -X POST http://localhost:8080/api/items \
  -H "Content-Type: application/json" \
  -d '{
    "collectionId": 1,
    "fieldValues": [
      {"fieldId": 1, "value": "Eiffel Tower"},
      {"fieldId": 2, "value": "France"}
    ]
  }'
```

### 4. Get all items in the collection

```bash
curl http://localhost:8080/api/items/collection/1
```

## Troubleshooting

### Docker issues

- **Port already in use**: If port 8080 or 5432 is already in use, you can change them in `docker-compose.yml`
- **Build fails**: Try `docker-compose down -v` to remove old volumes, then rebuild

### Database connection issues

- Make sure PostgreSQL is running
- Check the database credentials in `src/main/resources/application.yml`
- Verify the database `collections_db` exists

### API not responding

- Check if the application started successfully by looking at the logs
- Verify no firewall is blocking port 8080
- Try accessing `http://localhost:8080/api/users` instead of just `http://localhost:8080`

## Need Help?

Check the [API_EXAMPLES.md](./API_EXAMPLES.md) file for more detailed examples of all API endpoints.
