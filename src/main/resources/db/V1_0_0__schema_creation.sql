CREATE TABLE IF NOT EXISTS user
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    created_at TIMESTAMP   NOT NULL
);

INSERT INTO user (name, created_at)
VALUES ('John Howl', NOW()),
       ('Mary Poppins', NOW());

CREATE TABLE IF NOT EXISTS notification
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    message    VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL
);

INSERT INTO notification (message, created_at)
VALUES ('Hello World', NOW()),
       ('Goodbye World', NOW());
