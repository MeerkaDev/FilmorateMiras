package org.mirasruntime.filmoratemiras.dao;

import org.mirasruntime.filmoratemiras.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {
    List<Genre> findAll();

    Optional<Genre> findById(Long id);
}
