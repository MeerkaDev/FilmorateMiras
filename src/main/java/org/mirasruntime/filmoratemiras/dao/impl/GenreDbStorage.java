package org.mirasruntime.filmoratemiras.dao.impl;

import lombok.RequiredArgsConstructor;
import org.mirasruntime.filmoratemiras.dao.GenreStorage;
import org.mirasruntime.filmoratemiras.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("newGenreStorage")
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    private final String SELECT = """
            select id as genre_id, name as genre_name
            from genres
            """;

    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query(SELECT, this::mapRow);
    }

    @Override
    public Optional<Genre> findById(Long id) {
        String sql = SELECT + " where id = ?";
        return jdbcTemplate.query(sql, this::mapRow, id).stream().findFirst();
    }

    private Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("genre_id");
        String name = rs.getString("genre_name");
        return new Genre(id, name);
    }
}
