# Connection Pool per Module - PoC

This PoC demonstrates how to isolate connection pools per module by using a custom `AbstractRoutingDataSource` that
enables choosing a data source per both `module` and `type` (`reading|writing`) at runtime.

The implementation reuses Core-API `@ReadingController` / `@WritingController` stereotypes along with the interceptor to route to
the appropriate data source based on the request type and the package the request comes from, e.g.:

| package                  | type    | pool                   |
|--------------------------|---------|------------------------|
| `helpscout.conversation` | reading | `conversation_reading` |
|                          | writing | `conversation_writing` |
| `helpscout.mailbox`      | reading | `mailbox_reading`      |
|                          | writing | `mailbox_writing`      |

The data source settings are defined in `application.yml` and are loaded into custom `DataSourceProperties` beans to allow having a structure like:

```yaml
spring:
  datasource:
    reading:
      url: jdbc:mysql://localhost:3306/helpscout
      username: test
      password: test
      hikari:
        connection-timeout: 20000
        max-lifetime: 300000
    writing:
      ...
    instances:
      - name: conversation_reading
        pool-size: 10
      - name: conversation_writing
        pool-size: 5
      - name: mailbox_reading
        pool-size: 8
      - name: mailbox_writing
        pool-size: 4
```

## Quick start

The docker-compose file starts a MySQL database along with the Flyway scripts that create the two tables the demo + the tests need. 

To build and start the project, run `docker compose up` and then:

```bash
./gradlew clean build
./gradlew bootRun
```

This demo comes with four endpoints:
- `POST http://localhost:8080/v1/conversations`
- `GET http://localhost:8080/v1/conversations`
- `POST http://localhost:8080/v1/mailboxes`
- `GET http://localhost:8080/v1/mailboxes`

You may find the following message in the console depending on the endpoint you hit:

```
Routing datasource to mailbox_reading
```

There are also two integration tests that demonstrate how the routing works.
