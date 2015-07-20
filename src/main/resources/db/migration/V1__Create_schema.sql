DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS publishers;

CREATE TABLE IF NOT EXISTS authors (
  id   IDENTITY PRIMARY KEY,
  name VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS publishers (
  id   IDENTITY PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
  id           IDENTITY PRIMARY KEY,
  isbn         VARCHAR(13)  NOT NULL,
  title        VARCHAR(255) NOT NULL,
  author_id    BIGINT       NOT NULL,
  publisher_id BIGINT       NOT NULL,
  pages        INT          NOT NULL,
  published_on DATE         NOT NULL,
  price        INT          NOT NULL,
  UNIQUE (isbn),
  FOREIGN KEY (author_id) REFERENCES authors,
  FOREIGN KEY (publisher_id) REFERENCES publishers
);
