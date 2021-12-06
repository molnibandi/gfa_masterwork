CREATE TABLE IF NOT EXISTS casting
(
    movie_id BIGINT,
    actor_id BIGINT,
    CONSTRAINT casting_pk PRIMARY KEY (movie_id, actor_id),
    CONSTRAINT FK_movie
        FOREIGN KEY (movie_id) REFERENCES movies (id),
    CONSTRAINT FK_actor
        FOREIGN KEY (actor_id) REFERENCES actors (id)
);

INSERT INTO casting (movie_id, actor_id)
VALUES (1, 2),
       (1, 3),
       (1, 4),
       (2, 5),
       (2, 6);