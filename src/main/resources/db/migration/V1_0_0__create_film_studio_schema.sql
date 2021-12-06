CREATE SCHEMA IF NOT EXISTS moviedb;
CREATE TABLE IF NOT EXISTS film_studios
(
    `id`      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`    VARCHAR(100) UNIQUE NOT NULL,
    `founded` INT,
    `address` VARCHAR(255)
);

INSERT INTO film_studios (id, name, founded, address)
VALUES (1, 'Warner Bros. Pictures', 1923, 'Hollywood, Los Angeles, California, USA'),
       (2, 'New Line Cinema', 1967, 'New York, New York, USA'),
       (3, 'Touchstone Pictures', 1984, 'Burbank, California, USA'),
       (4, 'Lucasfilm', 1971, 'San Francisco, California, USA');