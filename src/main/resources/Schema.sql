DROP TABLE IF EXISTS books;
CREATE TABLE books
(
    id         SERIAL       NOT NULL,
    title      VARCHAR(100) NOT NULL,
    author     VARCHAR(400) NOT NULL,
    year       INTEGER NOT NULL,
    pages      INTEGER NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO books(title, author, year, pages)
VALUES ('Test book', 'John Doe',1999,200),
       ('Test book', 'John Doe',1999,200)
;

