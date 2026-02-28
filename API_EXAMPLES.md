# API Examples

This document provides examples of how to use the Collections Management API.

## Base URL

When running locally or with Docker: `http://localhost:8080/api`

## User Management

### Create a User

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com"
  }'
```

Response:
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com"
}
```

### Get All Users

```bash
curl http://localhost:8080/api/users
```

### Get User by ID

```bash
curl http://localhost:8080/api/users/1
```

## Collection Management

### Create a Collection

```bash
curl -X POST http://localhost:8080/api/collections \
  -H "Content-Type: application/json" \
  -d '{
    "name": "My Sticker Collection",
    "description": "A collection of stickers from around the world",
    "userId": 1,
    "fields": [
      {
        "fieldName": "Name",
        "fieldType": "TEXT",
        "required": true,
        "order": 1
      },
      {
        "fieldName": "Country",
        "fieldType": "TEXT",
        "required": false,
        "order": 2
      },
      {
        "fieldName": "Image",
        "fieldType": "IMAGE_URL",
        "required": false,
        "order": 3
      },
      {
        "fieldName": "Purchase Date",
        "fieldType": "DATE",
        "required": false,
        "order": 4
      },
      {
        "fieldName": "Price",
        "fieldType": "NUMBER",
        "required": false,
        "order": 5
      }
    ]
  }'
```

Response:
```json
{
  "id": 1,
  "name": "My Sticker Collection",
  "description": "A collection of stickers from around the world",
  "userId": 1,
  "fields": [
    {
      "id": 1,
      "fieldName": "Name",
      "fieldType": "TEXT",
      "required": true,
      "order": 1
    },
    {
      "id": 2,
      "fieldName": "Country",
      "fieldType": "TEXT",
      "required": false,
      "order": 2
    },
    {
      "id": 3,
      "fieldName": "Image",
      "fieldType": "IMAGE_URL",
      "required": false,
      "order": 3
    },
    {
      "id": 4,
      "fieldName": "Purchase Date",
      "fieldType": "DATE",
      "required": false,
      "order": 4
    },
    {
      "id": 5,
      "fieldName": "Price",
      "fieldType": "NUMBER",
      "required": false,
      "order": 5
    }
  ]
}
```

### Get All Collections for a User

```bash
curl http://localhost:8080/api/collections/user/1
```

### Get Collection by ID

```bash
curl http://localhost:8080/api/collections/1
```

### Update a Collection

```bash
curl -X PUT http://localhost:8080/api/collections/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Sticker Collection",
    "description": "My updated collection",
    "userId": 1,
    "fields": [...]
  }'
```

### Delete a Collection

```bash
curl -X DELETE http://localhost:8080/api/collections/1
```

## Item Management

### Add an Item to a Collection

```bash
curl -X POST http://localhost:8080/api/items \
  -H "Content-Type: application/json" \
  -d '{
    "collectionId": 1,
    "fieldValues": [
      {
        "fieldId": 1,
        "value": "Eiffel Tower Sticker"
      },
      {
        "fieldId": 2,
        "value": "France"
      },
      {
        "fieldId": 3,
        "value": "https://example.com/images/eiffel-tower.jpg"
      },
      {
        "fieldId": 4,
        "value": "2024-01-15"
      },
      {
        "fieldId": 5,
        "value": "5.99"
      }
    ]
  }'
```

Response:
```json
{
  "id": 1,
  "collectionId": 1,
  "fieldValues": [
    {
      "id": 1,
      "fieldId": 1,
      "value": "Eiffel Tower Sticker"
    },
    {
      "id": 2,
      "fieldId": 2,
      "value": "France"
    },
    {
      "id": 3,
      "fieldId": 3,
      "value": "https://example.com/images/eiffel-tower.jpg"
    },
    {
      "id": 4,
      "fieldId": 4,
      "value": "2024-01-15"
    },
    {
      "id": 5,
      "fieldId": 5,
      "value": "5.99"
    }
  ]
}
```

### Get All Items in a Collection

```bash
curl http://localhost:8080/api/items/collection/1
```

### Get Item by ID

```bash
curl http://localhost:8080/api/items/1
```

### Update an Item

```bash
curl -X PUT http://localhost:8080/api/items/1 \
  -H "Content-Type: application/json" \
  -d '{
    "collectionId": 1,
    "fieldValues": [
      {
        "fieldId": 1,
        "value": "Updated Eiffel Tower Sticker"
      },
      {
        "fieldId": 2,
        "value": "France"
      }
    ]
  }'
```

### Delete an Item

```bash
curl -X DELETE http://localhost:8080/api/items/1
```

## Field Types

The following field types are supported:

- `TEXT` - Plain text
- `NUMBER` - Numeric values
- `DATE` - Date values (format: YYYY-MM-DD)
- `IMAGE_URL` - URL to an image
- `URL` - Generic URL
- `BOOLEAN` - Boolean values (true/false)

## Example Use Cases

### Mug Collection

```json
{
  "name": "Coffee Mugs",
  "description": "My favorite coffee mugs",
  "userId": 1,
  "fields": [
    {
      "fieldName": "Brand",
      "fieldType": "TEXT",
      "required": true,
      "order": 1
    },
    {
      "fieldName": "Color",
      "fieldType": "TEXT",
      "required": false,
      "order": 2
    },
    {
      "fieldName": "Photo",
      "fieldType": "IMAGE_URL",
      "required": false,
      "order": 3
    },
    {
      "fieldName": "Capacity (ml)",
      "fieldType": "NUMBER",
      "required": false,
      "order": 4
    }
  ]
}
```

### URL/Bookmarks Collection

```json
{
  "name": "Useful Resources",
  "description": "Links to useful websites",
  "userId": 1,
  "fields": [
    {
      "fieldName": "Title",
      "fieldType": "TEXT",
      "required": true,
      "order": 1
    },
    {
      "fieldName": "URL",
      "fieldType": "URL",
      "required": true,
      "order": 2
    },
    {
      "fieldName": "Category",
      "fieldType": "TEXT",
      "required": false,
      "order": 3
    },
    {
      "fieldName": "Favorite",
      "fieldType": "BOOLEAN",
      "required": false,
      "order": 4
    }
  ]
}
```

## Error Handling

The API returns appropriate HTTP status codes:

- `200 OK` - Successful GET/PUT request
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

Error responses include a message describing the error:

```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 99",
  "path": "/api/users/99"
}
```
