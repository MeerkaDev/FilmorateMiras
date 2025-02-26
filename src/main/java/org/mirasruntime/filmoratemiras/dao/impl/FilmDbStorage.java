package org.mirasruntime.filmoratemiras.dao.impl;

import lombok.RequiredArgsConstructor;
import org.mirasruntime.filmoratemiras.dao.FilmStorage;
import org.mirasruntime.filmoratemiras.exception.FilmNotFoundException;
import org.mirasruntime.filmoratemiras.model.Film;
import org.mirasruntime.filmoratemiras.model.Genre;
import org.mirasruntime.filmoratemiras.model.Mpa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component("newFilmStorage")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final String SELECT = """
            select f.id as film_id,
            f.name as film_name,
            f.description as film_description,
            f.release_date as film_release_date,
            f.duration as film_duration,
            m.id as mpa_id,
            m.name as mpa_name
            from films f
            join mpa m on f.mpa_id = m.id
            """;

    @Override
    public List<Film> findAll() {
        return jdbcTemplate.query(SELECT, this::mapRow);
    }

    @Override
    public Optional<Film> findById(Long id) {
        String sqlFilms = SELECT + "where f.id = ?";
        Film film = jdbcTemplate.query(sqlFilms, this::mapRow, id)
                .stream()
                .findFirst()
                .orElseThrow();

        String sqlGenres = """
                select g.id as genre_id,
                g.name as genre_name
                from genres g
                join films_genres fg on g.id = fg.genre_id
                where fg.film_id = ?
                """;

        RowMapper<Genre> rowMapperGenre = (rs, rowNum) -> {
            long genre_id = rs.getLong("genre_id");
            String name = rs.getString("genre_name");

            return new Genre(genre_id, name);
        };

        List<Genre> genres = jdbcTemplate.query(sqlGenres, rowMapperGenre, id);
        film.getGenres().addAll(genres);

        return Optional.of(film);
    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert insert_film = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> map = new HashMap<>();
        map.put("name", film.getName());
        map.put("description", film.getDescription());
        map.put("release_date", film.getReleaseDate());
        map.put("duration", film.getDuration());
        map.put("mpa_id", film.getMpa().getId());

        Number number = insert_film.executeAndReturnKey(map);
        film.setId(number.longValue());

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            String sqlInsertGenres = "insert into films_genres (film_id, genre_id) values (?, ?)";

            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update(sqlInsertGenres, film.getId(), genre.getId());
            }
        }

        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlUpdateFilm = """
                update films set name = ?,
                description = ?,
                release_date = ?,
                duration = ?,
                mpa_id = ?
                where id = ?
                """;
        int recChanged = jdbcTemplate.update(sqlUpdateFilm, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());

        if (recChanged > 0) {
            String sqlDeleteGenres = "delete from films_genres where film_id = ?";
            jdbcTemplate.update(sqlDeleteGenres, film.getId());

            if (film.getGenres() != null && !film.getGenres().isEmpty()) {
                String sqlInsertGenres = "insert into films_genres (film_id, genre_id) values (?, ?)";

                for (Genre genre : film.getGenres()) {
                    jdbcTemplate.update(sqlInsertGenres, film.getId(), genre.getId());
                }
            }

            return film;
        }

        throw new FilmNotFoundException("Фильм не найден");
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        String sqlAddLike = "insert into likes (film_id, user_id) values (?, ?)";
        jdbcTemplate.update(sqlAddLike, filmId, userId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        String sqlRemoveLike = "delete from likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sqlRemoveLike, filmId, userId);
    }

    @Override
    public List<Film> getTopFilms(Long count) {
        String sqlTopFilms = """
            select f.id as film_id,
                   f.name as film_name,
                   f.description as film_description,
                   f.release_date as film_release_date,
                   f.duration as film_duration,
                   m.id as mpa_id,
                   m.name as mpa_name,
                   count(l.user_id) as like_count
            from films f
            join mpa m on f.mpa_id = m.id
            left join likes l on f.id = l.film_id
            group by f.id, m.id
            order by like_count desc
            limit ?
            """;

        return jdbcTemplate.query(sqlTopFilms, this::mapRow, count);
    }

    private Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("film_id");
        String name = rs.getString("film_name");
        String description = rs.getString("film_description");
        LocalDate releaseDate = rs.getDate("film_release_date").toLocalDate();
        int duration = rs.getInt("film_duration");

        long mpaId = rs.getLong("mpa_id");
        String mpaName = rs.getString("mpa_name");

        System.out.println("mpaId: " + mpaId + ", mpaName: " + mpaName); // Лог

        Mpa mpa = new Mpa(mpaId, mpaName);

        return new Film(id, name, description, releaseDate, duration, mpa);
    }
}
