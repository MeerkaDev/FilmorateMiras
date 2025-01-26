package org.mirasruntime.filmoratemiras.dao.impl;

import lombok.RequiredArgsConstructor;
import org.mirasruntime.filmoratemiras.dao.FilmStorage;
import org.mirasruntime.filmoratemiras.model.Film;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component("filmDbStorage")
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
            m.name as mpa_name,
            from films f
            join mpa m on f.mpa_id = m.id
            """;

    @Override
    public List<Film> findAll() {
        String sql = "select * from films";

        return List.of();
    }

    @Override
    public Optional<Film> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Film create(Film film) {
        return null;
    }

    @Override
    public Film update(Film film) {
        return null;
    }

//    private Film mapRow(ResultSet rs, int rowNum) throws SQLException
//    {
//        long id = rs.getLong("id");
//        String name = rs.getString("name");
//        String description = rs.getString("description");
//        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
//        int duration = rs.getInt("duration");
//
//
//        return new ;
//    }
}
