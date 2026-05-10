# Online Food Delivery - Identity Domain (Minimal Distributed System)

This workspace contains two Spring Boot microservices:

- `register-service` (MySQL + JPA)
- `login-service` (MongoDB Atlas + OpenFeign + JWT)

## 1) Architecture

- Register Service manages user registration and persistence in MySQL.
- Login Service authenticates users by calling Register Service over REST.
- Login Service verifies password using BCrypt and returns JWT on success.

## 2) Services

### register-service

- Port: `8081`
- Database: MySQL local (`food_identity`)
- APIs:
  - `POST /api/register`
  - `GET /api/users/{username}` (internal lookup for login service)

### login-service

- Port: `8082`
- Database: MongoDB Atlas (`food_identity_login`)
- APIs:
  - `POST /api/login`

## 3) Run Locally

Open two terminals:

### Terminal 1 - Register Service

```bash
cd register-service
mvn spring-boot:run
```

### Terminal 2 - Login Service

```bash
cd login-service
mvn spring-boot:run
```

## 4) Configuration

### MySQL config

In `register-service/src/main/resources/application.yml`, set:

- `spring.datasource.username`
- `spring.datasource.password`

### MongoDB Atlas config

In `login-service/src/main/resources/application.yml`, set:

- `spring.data.mongodb.uri`

## 5) Example API Requests

### Register user

```bash
curl -X POST http://localhost:8081/api/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "huy",
    "email": "huy@example.com",
    "password": "Secret123!"
  }'
```

Expected response (`201 Created`):

```json
{
  "id": 1,
  "username": "huy",
  "email": "huy@example.com"
}
```

### Login

```bash
curl -X POST http://localhost:8082/api/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "huy",
    "password": "Secret123!"
  }'
```

Expected response (`200 OK`):

```json
{
  "token": "<jwt_token>",
  "tokenType": "Bearer"
}
```

## 6) Build JAR

```bash
cd register-service
mvn clean package

cd ../login-service
mvn clean package
```

## 7) Docker (optional bonus)

Each service already includes a `Dockerfile`.

Example:

```bash
cd register-service
mvn clean package
docker build -t register-service:latest .

cd ../login-service
mvn clean package
docker build -t login-service:latest .
```
