# User Management API

## Overview

This API provides endpoints for user management, including the ability to sign up users. It uses Spring Boot for the backend implementation and follows RESTful principles.

## API Endpoints

Documentation: http://localhost:8080/api/swagger-ui/

### POST /oppenheimer/api/users

- **Description**: Registers a new user in the system.
- **Request Body**:
    - Content Type: `application/vnd.nisum.oppenheimer.user.v1+json`
    - Schema:
    ```json
    {
      "name": "Jane Doe",
      "email": "jane.doe@nisum.com",
      "password": "bkPnVny19ZHaALrz8UsL/SRSKdJBNr3iuvBiaclhmmI=",
      "phones": [
        {
          "number": "8092230098",
          "citycode": "1",
          "countrycode": "57"
        },
        {
          "number": "8092230093",
          "citycode": "1",
          "countrycode": "57"
         }
      ]
    }  
    ```
- **Responses**:
  - `200 OK`: Returns user details upon successful registration.
      - Content:
      ```json
      {
          "id": "880dd4dd-bc5a-4923-a35d-bdf05c2b04ca",
          "created": "2024-09-22T23:42:26.059835",
          "modified": "2024-09-22T23:42:26.059857",
          "lastLogin": "2024-09-22T23:42:26.059835",
          "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqYW5lLmRvZUBuaXN1bS5jb20iLCJuYW1lIjoiSmFuZSBEb2UiLCJpc3MiOiJuaXN1bSIsImF1ZCI6InRlc3QiLCJleHAiOjE3MjcxNDkzNDV9.l2b-pJr2MzqHGSXX0X59SUiqEpZ9fDd3H79Fl7azUj0",
          "isActive": true
      }
      ```
  - `400 Bad Request`: Returned when input validation fails.

## Validation Rules

- `name`: Required, cannot be blank, max length defined by `Constants.NAME_MAX_SIZE`.
- `email`: Required, must be a valid email format, max length defined by `Constants.EMAIL_MAX_SIZE`.
- `password`: Required, must meet complexity requirements, min length defined by `Constants.PASSWORD_MIN_SIZE`.
- `phones`: Optional, must contain valid `PhoneDTO` objects if provided.

## Testing

### Unit Tests

The API is tested using Spock Framework, with scenarios covering both valid and invalid inputs.

### Integration Tests

Integration tests validate the full flow of user registration, ensuring the system behaves as expected under various conditions.

## Usage

### Clone the repository.

   `git clone https://github.com/jcasadolora/Oppenheimer.git`
   
### Build and download the dependencies.

    `./gradlew clean build`

### Run the application.

   `./gradlew bootRun`

## Creating a Docker Service for Oppenheimer

### Prerequisites

- Ensure you have Docker Swarm initialized. If not, you can initialize it with:

```bash
  docker swarm init
```

### Build the docker image. 
```bash
   docker build -f src/docker/Dockerfile -t com.nisum.oppenheimer-svc:latest .
```
### Run the service.
```bash
docker service rm oppenheimer-svc

docker service create --publish 8080:8080 \
                      --name oppenheimer-svc \
                      --health-cmd 'nc -vz -w 3 localhost 8080 || exit 1' \
                      --health-interval 5s  \
                      --health-start-period 60s \
                      --env SPRING_PROFILES_ACTIVE=local \
                      com.nisum.oppenheimer-svc:latest

docker service logs -f oppenheimer-svc
```

### Scaling the Docker Service

To scale the `oppenheimer-svc` service to 10 instances, run the following command:

```bash
docker service scale oppenheimer-svc=10
```

### Shutting Down the Docker Service

To shut down the `oppenheimer-svc` service, run the following command:

```bash
docker service rm oppenheimer-svc
```