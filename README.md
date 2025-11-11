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
    "currentVersion": 0
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
  "currentVersion": 0
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
  "currentVersion": 0
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
  "currentVersion": 0
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
  "currentVersion": 0
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
  "currentVersion": 0
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
  "currentVersion": 0
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
  "currentVersion": 0
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
  "status": "OPEN | IN_PROGRESS | DONE"
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

## 💡 TypeScript Examples

### Type Definitions

```typescript
// Types
type TodoStatus = 'OPEN' | 'IN_PROGRESS' | 'DONE';

interface TodoDTO {
    description: string;
    status: TodoStatus;
}

interface Todo extends TodoDTO {
    id: string;
    currentVersion: number;
}

// API Configuration
const API_BASE_URL = 'http://localhost:8080';
const headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
};
```

### 1. Create Todo

```typescript
async function createTodo(todo: TodoDTO): Promise<Todo> {
    const response = await fetch(`${API_BASE_URL}/api/todo`, {
        method: 'POST',
        headers,
        body: JSON.stringify(todo)
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    return await response.json();
}

// Usage
const newTodo = await createTodo({
    description: 'Document project',
    status: 'OPEN'
});
console.log('Created:', newTodo);
```

### 2. Get All Todos

```typescript
async function getAllTodos(): Promise<Todo[]> {
    const response = await fetch(`${API_BASE_URL}/api/todo`, {
        method: 'GET',
        headers
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    return await response.json();
}

// Usage
const todos = await getAllTodos();
console.log('Todos:', todos);
```

### 3. Get Todo by ID

```typescript
async function getTodoById(id: string): Promise<Todo> {
    const response = await fetch(`${API_BASE_URL}/api/todo/${id}`, {
        method: 'GET',
        headers
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    return await response.json();
}

// Usage
const todo = await getTodoById('123');
console.log('Todo:', todo);
```

### 4. Update Todo

```typescript
async function updateTodo(id: string, todo: TodoDTO): Promise<Todo> {
    const response = await fetch(`${API_BASE_URL}/api/todo/${id}`, {
        method: 'PUT',
        headers,
        body: JSON.stringify(todo)
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    return await response.json();
}

// Usage
const updatedTodo = await updateTodo('123', {
    description: 'Document project',
    status: 'DONE'
});
console.log('Updated:', updatedTodo);
```

### 5. Delete Todo

```typescript
async function deleteTodo(id: string): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/api/todo/${id}`, {
        method: 'DELETE',
        headers
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    // 204 No Content - no body
}

// Usage
await deleteTodo('123');
console.log('Todo deleted');
```

### 6. Undo Changes

```typescript
async function undoTodo(id: string): Promise<Todo> {
    const response = await fetch(`${API_BASE_URL}/api/todo/${id}/undo`, {
        method: 'POST',
        headers
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    return await response.json();
}

// Usage
const undoneVersion = await undoTodo('123');
console.log('Undo completed:', undoneVersion);
```

### 7. Redo Changes

```typescript
async function redoTodo(id: string): Promise<Todo> {
    const response = await fetch(`${API_BASE_URL}/api/todo/${id}/redo`, {
        method: 'POST',
        headers
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    return await response.json();
}

// Usage
const redoneVersion = await redoTodo('123');
console.log('Redo completed:', redoneVersion);
```

### 🎯 Complete Example

```typescript
// Complete workflow
async function todoWorkflow() {
    try {
        // 1. Create new todo
        const newTodo = await createTodo({
            description: 'Test API',
            status: 'OPEN'
        });
        console.log('✅ Todo created:', newTodo.id);

        // 2. Update todo
        const updated = await updateTodo(newTodo.id, {
            description: 'Test API',
            status: 'IN_PROGRESS'
        });
        console.log('✅ Status changed to IN_PROGRESS');

        // 3. Undo change
        const undone = await undoTodo(newTodo.id);
        console.log('↩️ Undo: back to', undone.status);

        // 4. Redo change
        const redone = await redoTodo(newTodo.id);
        console.log('↪️ Redo: back to', redone.status);

        // 5. Get all todos
        const allTodos = await getAllTodos();
        console.log('📋 Number of todos:', allTodos.length);

        // 6. Delete todo
        await deleteTodo(newTodo.id);
        console.log('🗑️ Todo deleted');

    } catch (error) {
        console.error('❌ Error:', error);
    }
}

// Run workflow
todoWorkflow();
```

---

## 📝 Notes

- All endpoints use `application/json` for request and response
- The API supports versioning through the `currentVersion` field
- Undo/Redo functionality enables change history per todo
- Create todo will check for grammar and spelling via AI