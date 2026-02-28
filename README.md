# Collections Back - Backend API

A Spring Boot REST API for managing collections. This application allows users to create and manage any type of collection (stickers, mugs, URLs, etc.) with custom fields for each collection item.

## Features

- User management
- Create and manage multiple collections per user
- Define custom fields for each collection (name, image URL, purchase location, etc.)
- Add items to collections with dynamic field values
- PostgreSQL database for data persistence
- Docker support for easy deployment

## Technology Stack

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- PostgreSQL
- Maven
- Docker

## API Endpoints

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Collections
- `GET /api/collections` - Get all collections
- `GET /api/collections/{id}` - Get collection by ID
- `GET /api/collections/user/{userId}` - Get all collections for a user
- `POST /api/collections` - Create a new collection
- `PUT /api/collections/{id}` - Update collection
- `DELETE /api/collections/{id}` - Delete collection

### Items
- `GET /api/items` - Get all items
- `GET /api/items/{id}` - Get item by ID
- `GET /api/items/collection/{collectionId}` - Get all items in a collection
- `POST /api/items` - Create a new item
- `PUT /api/items/{id}` - Update item
- `DELETE /api/items/{id}` - Delete item

## Running with Docker

### Prerequisites
- Docker
- Docker Compose

### Start the application

```bash
docker-compose up --build
```

This will:
- Build the Spring Boot application
- Start a PostgreSQL database
- Start the backend API on port 8080

The API will be accessible at: `http://localhost:8080/api`

### Stop the application

```bash
docker-compose down
```

To also remove the database volume:
```bash
docker-compose down -v
```

## Running Locally (without Docker)

### Prerequisites
- Java 17
- Maven
- PostgreSQL (running on localhost:5432)

### Database Setup

Create a PostgreSQL database:
```sql
CREATE DATABASE collections_db;
```

### Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be accessible at: `http://localhost:8080/api`

## Configuration

Environment variables can be used to configure the application:

- `DB_HOST` - Database host (default: localhost)
- `DB_PORT` - Database port (default: 5432)
- `DB_NAME` - Database name (default: collections_db)
- `DB_USER` - Database user (default: postgres)
- `DB_PASSWORD` - Database password (default: postgres)
- `SERVER_PORT` - Server port (default: 8080)

## Data Model

### User
- username (unique)
- email (unique)
- collections

### Collection
- name
- description
- user (owner)
- fields (custom field definitions)
- items

### CollectionField
- fieldName
- fieldType (TEXT, NUMBER, DATE, IMAGE_URL, URL, BOOLEAN)
- required
- order

### Item
- collection
- fieldValues (values for each field)

### ItemFieldValue
- item
- field
- value