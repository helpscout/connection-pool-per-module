# Connection Pool per Module

This sample demonstrates how to isolate connection pools per module using a custom `AbstractRoutingDataSource`. This allows choosing a data source per `module` at runtime.

## Implementation

The implementation uses Spring's `@RestController` stereotype along with an interceptor to route to the appropriate data source based on the package the request comes from:

| Package                  | Pool                |
|--------------------------|---------------------|
| `helpscout.notification` | `notification_pool` |
| `helpscout.user`         | `user_pool`         |
| else                     | `default_pool`      |

### Data Source Settings

The data source settings are defined in `application.yml` and are loaded into a custom `ModuleDataSourceProperties` bean to allow having a structure like:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/helpscout
    password: test
    username: test
    hikari:
      connection-timeout: 20000
      max-lifetime: 300000
    instances:
      - name: default_pool
        pool-size: 10
      - name: notification_pool
        pool-size: 10
      - name: user_pool
        pool-size: 10
```

## Quick Start

1. Start the MySQL database and Flyway scripts using Docker Compose:

    ```bash
    docker compose up
    ```

2. Build and start the project:

    ```bash
    ./gradlew clean build
    ./gradlew bootRun
    ```

## Endpoints

This demo provides the following endpoints:

- `GET http://localhost:8080/v1/notifications`
- `GET http://localhost:8080/v1/notifications/{id}`
- `GET http://localhost:8080/v1/users`
- `GET http://localhost:8080/v1/users/{id}`

Depending on the endpoint you hit, you may see the following message in the console:

```
Routing datasource to {current}_pool
```

## Integration Tests

A few integration tests are available to validate that the routing works correctly.