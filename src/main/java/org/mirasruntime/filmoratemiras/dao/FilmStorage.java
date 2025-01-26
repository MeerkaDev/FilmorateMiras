package org.mirasruntime.filmoratemiras.dao;

import org.mirasruntime.filmoratemiras.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> findAll();

    Optional<Film> findById(Long id);

    Film create(Film film);

    Film update(Film film);
}
