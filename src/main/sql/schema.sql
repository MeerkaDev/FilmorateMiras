create table if not exists mpa
(
    id   bigserial primary key,
    name varchar not null
);

create table if not exists genres
(
    id   bigserial primary key,
    name varchar not null
);

create table if not exists films
(
    id           bigserial primary key,
    name         varchar not null,
    description  varchar,
    release_date date    not null,
    duration     int     not null,
    mpa_id       bigserial,
    foreign key (mpa_id) references mpa (id)
);

create table if not exists users
(
    id       bigserial primary key,
    name     varchar not null,
    login    varchar not null,
    email    varchar not null,
    birthday date    not null
);

create table if not exists films_genres
(
    film_id bigint,
    genre_id bigint,
    primary key (film_id, genre_id),
    foreign key (film_id) references films(id),
    foreign key (genre_id) references genre(id)
);

create table if not exists friends
(
    user_id bigint,
    friend_id bigint,
    primary key (user_id, friend_id),
    foreign key (user_id) references users(id),
    foreign key (friend_id) references users(id)
);

create table if not exists likes
(
    user_id bigint,
    film_id bigint,
    primary key (user_id, film_id),
    foreign key (user_id) references users(id),
    foreign key (film_id) references films(id)
)