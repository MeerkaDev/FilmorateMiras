package org.mirasruntime.filmoratemiras.dao;

import org.mirasruntime.filmoratemiras.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {
    List<User> findAll();

    Optional<User> findById(Long id);

    User create(User user);

    User update(User user);

    void addFriend(Long userId, Long friendId);

    void removeFriend(Long userId, Long friendId);

    Set<User> getFriends(User user);

    Set<User> getCommonFriends(User user1, User user2);
}
