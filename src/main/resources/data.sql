INSERT INTO mpa (name)
VALUES ('G'),
       ('PG'),
       ('PG-13'),
       ('R'),
       ('NC-17');


INSERT INTO genres (name)
VALUES ('Комедия'),
       ('Драма'),
       ('Мультфильм'),
       ('Триллер'),
       ('Документальный'),
       ('Боевик');


INSERT INTO users (name, login, email, birthday)
VALUES ('Айбек', 'aibek123', 'aibek@example.com', '1990-01-01'),
       ('Мария', 'maria456', 'maria@example.com', '1985-05-15'),
       ('Артемий', 'artemiy789', 'artemiy@example.com', '2000-12-12'),
       ('Сания', 'saniya', 'saniya@example.com', '1995-07-07');


INSERT INTO films (name, description, release_date, duration, mpa_id)
VALUES ('Inception', 'A mind-bending thriller', '2010-07-16', 148, 3),
       ('The Godfather', 'A crime and family drama', '1972-03-24', 175, 4),
       ('Toy Story', 'An animated adventure of toys', '1995-11-22', 81, 2),
       ('Pulp Fiction', 'Interconnected crime stories', '1994-10-14', 154, 4),
       ('The Matrix', 'A hacker discovers the truth about his world', '1999-03-31', 136, 3);


INSERT INTO films_genres (film_id, genre_id)
VALUES (1, 5),
       (1, 3),
       (2, 3),
       (2, 1),
       (2, 4),
       (3, 2),
       (4, 3),
       (4, 5),
       (5, 5),
       (5, 3);


INSERT INTO friends (user_id, friend_id)
VALUES (1, 2),
       (2, 3),
       (3, 4),
       (4, 1);


INSERT INTO likes (user_id, film_id)
VALUES (1, 1),
       (1, 5),
       (2, 2),
       (3, 3),
       (4, 4);
