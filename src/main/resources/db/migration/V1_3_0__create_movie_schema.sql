CREATE TABLE IF NOT EXISTS movies
(
    `id`             BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title`          VARCHAR(100) UNIQUE NOT NULL,
    `image_url`      VARCHAR(255),
    `release_year`   INT,
    `rating`         DECIMAL(3, 1),
    `description`    VARCHAR(1000),
    `gross`          DECIMAL(8, 1),
    `genre`          VARCHAR(100),
    `film_studio_id` BIGINT,
    `director_id`    BIGINT,
    FOREIGN KEY (film_studio_id) REFERENCES film_studios (id),
    FOREIGN KEY (director_id) REFERENCES directors (id)
);

INSERT INTO movies (id, title, image_url, release_year, rating, description, gross, genre, film_studio_id, director_id)
VALUES (1, 'Star Wars - A new Hope', 'https://www.imdb.com/title/tt0076759/mediaviewer/rm3263717120/', 1977, 8.6,
        'Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empires world-destroying battle station, while also attempting to rescue Princess Leia from the mysterious Darth Vader.',
        775.4, 'fantasy', 4, 1),
       (2, 'Oscar', 'https://www.imdb.com/title/tt0102603/mediaviewer/rm3571070464/', 1991, 6.5,
        'A gangster attempts to keep the promise he made to his dying father: that he would give up his life of crime and "go straight".',
        23.6, 'comedy', 3, 2);