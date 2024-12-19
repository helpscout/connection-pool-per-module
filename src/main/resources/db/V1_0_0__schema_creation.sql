CREATE TABLE IF NOT EXISTS user
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    created_at TIMESTAMP   NOT NULL
);

INSERT INTO user (name, created_at)
VALUES ('John Howl', NOW()),
       ('Mary Poppins', NOW());

CREATE TABLE IF NOT EXISTS purchase
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    product    VARCHAR(50) NOT NULL,
    created_at TIMESTAMP   NOT NULL
);

INSERT INTO purchase (product, created_at)
VALUES ('Product 1', NOW()),
       ('Product 2', NOW());
