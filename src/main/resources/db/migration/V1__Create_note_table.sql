CREATE TABLE note
(
    id      SERIAL PRIMARY KEY,
    title   VARCHAR(100)  NOT NULL,
    content VARCHAR(1000) NOT NULL
);
