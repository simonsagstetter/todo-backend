# Todo-Backend

![Java](https://img.shields.io/badge/jdk-21-red?logo=openjdk)
![Distribution](https://img.shields.io/badge/distribution-coretto-red?logo=openjdk)
![Distribution](https://img.shields.io/badge/spring%20boot-3.5.7-greeen?logo=springboot)
![Coverage](https://img.shields.io/badge/coverage-97%25-greeen)
[![CI](https://github.com/simonsagstetter/todo-backend/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/simonsagstetter/todo-backend/actions/workflows/ci.yml)
![Version](https://img.shields.io/badge/version-0.0.1-lightgrey)
![Server](https://img.shields.io/badge/server-localhost:8080-lightgrey)

Todo-Backend is a REST API written in Java and Spring Boot Web.

## Features

### Swagger-UI

Browse through the Todo API using an integrated Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

### Java Melody

Monitor the performance of your application with Java Melody:

```
http://localhost:8080/monitoring
```

### React Frontend

Test your API with a basic react todo-app:

```
http://localhost:8080
```

# API Documentation

## 🔗 Base URL

```
http://localhost:8080
```

## 📋 API Endpoints

### Todo Controller

#### 1️⃣ Get All Todos

Retrieves all todo entries.

```http
GET /api/todo
```

**Headers**
| Name | Type | Required | Values |
|------|------|----------|--------|
| Content-Type | string | Yes | `application/json` |
| Accept | string | Yes | `application/json` |

**Response**

- **Status Code:** `200 OK`
- **Content-Type:** `application/json`
- **Body:** Array of `Todo` objects

<details>
<summary>📄 Response Schema</summary>

```json
[
  {
    "id": "string",
    "description": "string",
    "status": "OPEN | IN_PROGRESS | DONE",
    "isGrammarChecked": "true | false",
    "currentVersion": 0,
    "created": "string",
    "lastModified": "string"
  }
]
```

</details>

---

#### 2️⃣ Create Todo

Creates a new todo entry.

```http
POST /api/todo
```

**Headers**
| Name | Type | Required | Values |
|------|------|----------|--------|
| Content-Type | string | Yes | `application/json` |
| Accept | string | Yes | `application/json` |

**Request Body** (required)

```json
{
  "description": "string",
  "status": "OPEN | IN_PROGRESS | DONE"
}
```

**Success Response**

- **Status Code:** `201 Created`
- **Content-Type:** `application/json`
- **Body:** `Todo` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "id": "string",
  "description": "string",
  "status": "OPEN | IN_PROGRESS | DONE",
  "isGrammarChecked": "true | false",
  "currentVersion": 0,
  "created": "string",
  "lastModified": "string"
}
```

</details>

**Validation Error Response**

- **Status Code:** `400 Bad Request`
- **Content-Type:** `application/json`
- **Body:** `ValidationErrorResponse` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "status": "string",
  "message": "string",
  "fields": [
    {
      "field": "string",
      "message": "string"
    }
  ]
}
```

</details>

**Error Response**

- **Status Code:** `404 Not Found`
- **Content-Type:** `application/json`
- **Body:** `ErrorResponse` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "status": "string",
  "message": "string"
}
```

</details>

---

#### 3️⃣ Get Todo by ID

Retrieves a specific todo entry.

```http
GET /api/todo/{id}
```

**Path Parameters**
| Name | Type | Required | Description |
|------|------|----------|-------------|
| id | string | Yes | Todo ID |

**Headers**
| Name | Type | Required | Values |
|------|------|----------|--------|
| Content-Type | string | Yes | `application/json` |
| Accept | string | Yes | `application/json` |

**Success Response**

- **Status Code:** `200 OK`
- **Content-Type:** `application/json`
- **Body:** `Todo` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "id": "string",
  "description": "string",
  "status": "OPEN | IN_PROGRESS | DONE",
  "isGrammarChecked": "true | false",
  "currentVersion": 0,
  "created": "string",
  "lastModified": "string"
}
```

</details>

**Error Response**

- **Status Code:** `404 Not Found`
- **Content-Type:** `application/json`
- **Body:** `ErrorResponse` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "status": "string",
  "message": "string"
}
```

</details>

---

#### 4️⃣ Update Todo

Updates an existing todo entry.

```http
PUT /api/todo/{id}
```

**Path Parameters**
| Name | Type | Required | Description |
|------|------|----------|-------------|
| id | string | Yes | Todo ID |

**Headers**
| Name | Type | Required | Values |
|------|------|----------|--------|
| Content-Type | string | Yes | `application/json` |
| Accept | string | Yes | `application/json` |

**Request Body** (required)

```json
{
  "description": "string",
  "status": "OPEN | IN_PROGRESS | DONE"
}
```

**Success Response**

- **Status Code:** `200 OK`
- **Content-Type:** `application/json`
- **Body:** `Todo` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "id": "string",
  "description": "string",
  "status": "OPEN | IN_PROGRESS | DONE",
  "isGrammarChecked": "true | false",
  "currentVersion": 0,
  "created": "string",
  "lastModified": "string"
}
```

</details>

**Validation Error Response**

- **Status Code:** `400 Bad Request`
- **Content-Type:** `application/json`
- **Body:** `ValidationErrorResponse` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "status": "string",
  "message": "string",
  "fields": [
    {
      "field": "string",
      "message": "string"
    }
  ]
}
```

</details>

**Error Response**

- **Status Code:** `404 Not Found`
- **Content-Type:** `application/json`
- **Body:** `ErrorResponse` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "status": "string",
  "message": "string"
}
```

</details>

---

#### 5️⃣ Delete Todo

Deletes a todo entry.

```http
DELETE /api/todo/{id}
```

**Path Parameters**
| Name | Type | Required | Description |
|------|------|----------|-------------|
| id | string | Yes | Todo ID |

**Headers**
| Name | Type | Required | Values |
|------|------|----------|--------|
| Content-Type | string | Yes | `application/json` |
| Accept | string | Yes | `application/json` |

**Success Response**

- **Status Code:** `200 OK`
- **Content-Type:** `application/json`
- **Body:** `Todo` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "id": "string",
  "description": "string",
  "status": "OPEN | IN_PROGRESS | DONE",
  "isGrammarChecked": "true | false",
  "currentVersion": 0,
  "created": "string",
  "lastModified": "string"
}
```

</details>

**Error Response**

- **Status Code:** `404 Not Found`
- **Content-Type:** `application/json`
- **Body:** `ErrorResponse` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "status": "string",
  "message": "string"
}
```

</details>

---

#### 6️⃣ Undo Todo Changes

Reverts the last change to a todo.

```http
POST /api/todo/{id}/undo
```

**Path Parameters**
| Name | Type | Required | Description |
|------|------|----------|-------------|
| id | string | Yes | Todo ID |

**Headers**
| Name | Type | Required | Values |
|------|------|----------|--------|
| Content-Type | string | Yes | `application/json` |
| Accept | string | Yes | `application/json` |

**Success Response**

- **Status Code:** `200 OK`
- **Content-Type:** `application/json`
- **Body:** `Todo` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "id": "string",
  "description": "string",
  "status": "OPEN | IN_PROGRESS | DONE",
  "isGrammarChecked": "true | false",
  "currentVersion": 0,
  "created": "string",
  "lastModified": "string"
}
```

</details>

**Error Response**

- **Status Code:** `404 Not Found`
- **Content-Type:** `application/json`
- **Body:** `ErrorResponse` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "status": "string",
  "message": "string"
}
```

</details>

---

#### 7️⃣ Redo Todo Changes

Restores a previously undone change.

```http
POST /api/todo/{id}/redo
```

**Path Parameters**
| Name | Type | Required | Description |
|------|------|----------|-------------|
| id | string | Yes | Todo ID |

**Headers**
| Name | Type | Required | Values |
|------|------|----------|--------|
| Content-Type | string | Yes | `application/json` |
| Accept | string | Yes | `application/json` |

**Success Response**

- **Status Code:** `200 OK`
- **Content-Type:** `application/json`
- **Body:** `Todo` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "id": "string",
  "description": "string",
  "status": "OPEN | IN_PROGRESS | DONE",
  "isGrammarChecked": "true | false",
  "currentVersion": 0,
  "created": "string",
  "lastModified": "string"
}
```

</details>

**Error Response**

- **Status Code:** `404 Not Found`
- **Content-Type:** `application/json`
- **Body:** `ErrorResponse` object

<details>
<summary>📄 Response Schema</summary>

```json
{
  "status": "string",
  "message": "string"
}
```

</details>

---

## 📊 Data Models

### Todo

Complete todo object with ID and versioning.

```json
{
  "id": "string",
  "description": "string",
  "status": "OPEN | IN_PROGRESS | DONE",
  "isGrammarChecked": "true | false",
  "currentVersion": 0,
  "created": "string",
  "lastModified": "string"
}
```

| Field          | Type            | Description            |
|----------------|-----------------|------------------------|
| id             | string          | Unique todo identifier |
| description    | string          | Todo description       |
| status         | enum            | Todo status            |
| currentVersion | integer (int64) | Current version number |

### TodoDTO

Todo object for creation and updates (without ID).

```json
{
  "description": "string",
  "status": "OPEN | IN_PROGRESS | DONE",
  "shouldGrammarCheck": "true | false"
}
```

| Field       | Type   | Description      |
|-------------|--------|------------------|
| description | string | Todo description |
| status      | enum   | Todo status      |

### Status Enum

Possible values for todo status:

- `OPEN` - Todo is open
- `IN_PROGRESS` - Todo is being worked on
- `DONE` - Todo is completed

### ErrorResponse

ErrorResponse object for 404 and 405 errors.

```json
{
  "status": "string",
  "message": "string"
}
```

| Field   | Type   | Description                          |
|---------|--------|--------------------------------------|
| status  | string | HttpStatus                           |
| message | string | Detailed information about the error |

### ValidationErrorResponse

ValidationErrorResponse object for 400.

```json
{
  "status": "string",
  "message": "string",
  "fieldErrors": [
    {
      "field": "string",
      "message": "string"
    }
  ]
}
```

| Field       | Type         | Description                |
|-------------|--------------|----------------------------|
| status      | string       | HttpStatus                 |
| message     | string       | General error message      |
| fieldErrors | FieldError[] | List of FieldError objects |

### FieldError

FieldError object for ValidationErrorResponse.

```json
{
  "field": "string",
  "message": "string"
}
```

| Field   | Type   | Description                          |
|---------|--------|--------------------------------------|
| field   | string | Name of field with error             |
| message | string | Detailed information about the error |

---

## 🔧 Quick Reference

### All Endpoints Overview

| Method | Endpoint              | Description     | Status Code   |
|--------|-----------------------|-----------------|---------------|
| GET    | `/api/todo`           | Get all todos   | 200           |
| POST   | `/api/todo`           | Create new todo | 201, 400, 404 |
| GET    | `/api/todo/{id}`      | Get todo by ID  | 200, 404      |
| PUT    | `/api/todo/{id}`      | Update todo     | 200, 400, 404 |
| DELETE | `/api/todo/{id}`      | Delete todo     | 204, 404      |
| POST   | `/api/todo/{id}/undo` | Undo changes    | 200, 404      |
| POST   | `/api/todo/{id}/redo` | Redo changes    | 200, 404      |

---

## 📝 Notes

- All endpoints use `application/json` for request and response
- The API supports versioning through the `currentVersion` field
- Undo/Redo functionality enables change history per todo
- Create todo will check for grammar and spelling via AI