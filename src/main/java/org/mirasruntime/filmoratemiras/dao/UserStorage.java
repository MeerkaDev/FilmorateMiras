package org.mirasruntime.filmoratemiras.dao;

import org.mirasruntime.filmoratemiras.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    List<User> findAll();

    Optional<User> findById(Long id);

    User create(User user);

    User update(User user);
}
