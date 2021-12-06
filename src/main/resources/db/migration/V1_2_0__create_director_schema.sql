CREATE TABLE IF NOT EXISTS directors
(
    `id`    BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`  VARCHAR(100) UNIQUE NOT NULL,
    `about` VARCHAR(1000)
);

INSERT INTO directors (id, name, about)
VALUES (1, 'George Lucas',
        'George Walton Lucas, Jr. was raised on a walnut ranch in Modesto, California. His father was a stationery store owner and he had three siblings.'),
       (2, 'John Landis',
        'John Landis began his career in the mail room of 20th Century-Fox. A high-school dropout, 18-year-old Landis made his way to Yugoslavia to work as a production assistant.'),
       (3, 'Tony Scott',
        'Tony Scott was a British-born film director and producer. He was the youngest of three brothers, one of whom is fellow film director Ridley Scott.');