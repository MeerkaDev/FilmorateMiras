package org.mirasruntime.filmoratemiras.dao;

import org.mirasruntime.filmoratemiras.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {
    List<Mpa> findAll();

    Optional<Mpa> findById(Long id);
}
