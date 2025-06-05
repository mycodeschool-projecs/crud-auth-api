# CRUD Authentication API

## Overview
The CRUD Authentication API is a microservice responsible for user authentication and authorization. It acts as a gateway/proxy for all client operations, ensuring that only authenticated users can access the system's functionality. All calls from the UI are routed through this service, which then communicates with the appropriate backend services.

## Key Features
- User registration (signup)
- User authentication (signin)
- JWT token-based authentication
- Gateway/proxy functionality for all backend services
- Proxy endpoints for client operations:
  - Add client
  - Update client
  - Delete client
  - Find client by email
  - Get all clients
- Proxy endpoints for notification operations:
  - Get all notifications
  - Get notifications by various filters
  - Mark notifications as read

## Service Interactions
- **Command API**: The Auth API communicates with the Command API to perform client operations after authenticating the user.
- **Notifications API**: The Auth API communicates with the Notifications API to retrieve and manage notifications.
- **Client Application**: The frontend client application interacts with this service for all operations, including authentication, client management, and notifications.

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

### Client Operations
- **POST /api/v1/addclient**: Add a new client
- **POST /api/v1/updclient**: Update an existing client
- **DELETE /api/v1/delclient/{email}**: Delete a client by email
- **GET /api/v1/findclient/{email}**: Find a client by email
- **GET /api/v1/getclients**: Get all clients
- **GET /api/v1/clients**: Proxy endpoint to get all clients

### Notification Operations
- **GET /api/v1/notifications**: Get all notifications
- **GET /api/v1/notifications/status/{read}**: Get notifications by read status
- **GET /api/v1/notifications/service/{sourceService}**: Get notifications by source service
- **GET /api/v1/notifications/entity-type/{entityType}**: Get notifications by entity type
- **GET /api/v1/notifications/operation/{operation}**: Get notifications by operation
- **GET /api/v1/notifications/entity-id/{entityId}**: Get notifications by entity ID
- **PUT /api/v1/notifications/{id}/mark-read**: Mark a notification as read
- **PUT /api/v1/notifications/mark-all-read**: Mark all notifications as read
