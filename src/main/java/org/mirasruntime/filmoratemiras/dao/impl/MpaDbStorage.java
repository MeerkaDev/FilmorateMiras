package org.mirasruntime.filmoratemiras.dao.impl;

import lombok.RequiredArgsConstructor;
import org.mirasruntime.filmoratemiras.dao.MpaStorage;
import org.mirasruntime.filmoratemiras.model.Mpa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("newMpaStorage")
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    private final String SELECT_ALL = """
            select id as mpa_id, name as mpa_name
            from mpa
            """;

    @Override
    public List<Mpa> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRow);
    }

    @Override
    public Optional<Mpa> findById(Long id) {
        String sql = SELECT_ALL + " where id = ?";
        return jdbcTemplate.query(sql, this::mapRow, id).stream().findFirst();
    }

    private Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("mpa_id");
        String name = rs.getString("mpa_name");
        return new Mpa(id, name);
    }
}
