CREATE TABLE notes
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(300)   NOT NULL,
    content    VARCHAR(10000) NOT NULL,
    created_at BIGINT         NOT NULL,
    updated_at BIGINT         NOT NULL
);
