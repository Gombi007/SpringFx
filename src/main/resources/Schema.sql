DROP TABLE IF EXISTS books;
CREATE TABLE books
(
    id     SERIAL       NOT NULL,
    isbn10 BIGINT       NOT NULL,
    title  VARCHAR(100) NOT NULL,
    author VARCHAR(400) NOT NULL,
    year   INTEGER      NOT NULL,
    pages  INTEGER      NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO books(isbn10,title, author, year, pages)
VALUES ('9634477453','Babel', 'Tamas Frei', 2020, 534),
       ('1612680194','Rich Dad Poor Dad', 'Robert T. Kiyosaki', 2017, 336),
       ('0735211299','Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones', 'James Clear ', 2018, 320)
;

