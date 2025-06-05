# CRUD Authentication API

## Overview
The CRUD Authentication API is a microservice responsible for user authentication and authorization. It acts as a gateway/proxy to the Command API, ensuring that only authenticated users can access client data and operations.

## Key Features
- User registration (signup)
- User authentication (signin)
- JWT token-based authentication
- Proxy endpoints for client operations:
  - Add client
  - Update client
  - Delete client
  - Find client by email
  - Get all clients

## Service Interactions
- **Command API**: The Auth API communicates with the Command API to perform client operations after authenticating the user.
- **Client Application**: The frontend client application interacts with this service for authentication and client operations.

## Technologies
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- Feign Client for service-to-service communication
- RESTful API

## Running the Service
### Prerequisites
- Java 11 or higher
- Maven
- Database (configured in application properties)

### Local Development
1. Clone the repository
2. Navigate to the crud-auth-api directory
3. Run `mvn spring-boot:run`
4. The service will be available at http://localhost:8081

### Docker
The service can also be run using Docker:
```
docker build -t crud-auth-api .
docker run -p 8081:8081 crud-auth-api
```

### Kubernetes
Kubernetes deployment configurations are available in the crud-app-k8s directory.

## API Endpoints
- **POST /api/v1/auth/signup**: Register a new user
- **POST /api/v1/auth/signin**: Authenticate a user and get JWT token
- **POST /api/v1/addclient**: Add a new client
- **POST /api/v1/updclient**: Update an existing client
- **DELETE /api/v1/delclient/{email}**: Delete a client by email
- **GET /api/v1/findclient/{email}**: Find a client by email
- **GET /api/v1/getclients**: Get all clients
- **GET /api/v1/clients**: Proxy endpoint to get all clients