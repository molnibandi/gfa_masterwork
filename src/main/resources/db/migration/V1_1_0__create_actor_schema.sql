CREATE TABLE IF NOT EXISTS actors
(
    `id`    BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`  VARCHAR(100) UNIQUE NOT NULL,
    `about` VARCHAR(1000)
);

INSERT INTO actors (id, name, about)
VALUES (1, 'Bruce Willis',
        'Actor and musician Bruce Willis is well known for playing wisecracking or hard-edged characters, often in spectacular action films.'),
       (2, 'Carrie Fisher',
        'Carrie Frances Fisher was born on October 21, 1956 in Burbank, California, to singers/actors Eddie Fisher and Debbie Reynolds. She was an actress and writer.'),
       (3, 'Harrison Ford',
        'Harrison Ford was born on July 13, 1942 in Chicago, Illinois, to Dorothy (Nidelman), a radio actress, and Christopher Ford (born John William Ford), an actor turned advertising executive.'),
       (4, 'Mark Hamill',
        'Mark Hamill is best known for his portrayal of Luke Skywalker in the original Star Wars trilogy.'),
       (5, 'Sylvester Stallone',
        'Sylvester Stallone is a athletically built, dark-haired American actor/screenwriter/director/producer, the movie fans worldwide have been flocking to see Stallones films for over 40 years.'),
       (6, 'Ornella Muti', 'Ornella Muti was born on March 9, 1955 in Rome, Lazio, Italy as Francesca Romana Rivelli.');