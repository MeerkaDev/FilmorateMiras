package org.mirasruntime.filmoratemiras.storage;

import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.dao.UserStorage;
import org.mirasruntime.filmoratemiras.exception.UserNotFoundException;
import org.mirasruntime.filmoratemiras.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final List<User> users = new ArrayList<>();
    private long uniqueId = 0;

    @Override
    public List<User> findAll() {
        log.info("Вывод списка всех пользователей Storage");

        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Вывод найденого пользователя Storage");

        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public User create(User user) {
        log.info("Заведение нового пользователя Storage");

        user.setId(++uniqueId);
        users.add(user);

        return user;
    }

    @Override
    public User update(User user) {
        log.info("Изменение данных пользователя Storage");

        Optional<User> optionalUserFound = findById(user.getId());

        if (optionalUserFound.isPresent()) {
            User userToUpdate = optionalUserFound.get();

            userToUpdate.setId(user.getId());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setLogin(user.getLogin());
            userToUpdate.setName(user.getName());
            userToUpdate.setBirthday(user.getBirthday());

            return userToUpdate;
        } else {
            throw new UserNotFoundException(String.format("Пользователь с id = %d не найден", user.getId()));
        }
    }

    @Override
    public void addFriend(Long userId, Long friendId) {

    }

    @Override
    public void removeFriend(Long userId, Long friendId) {

    }

    @Override
    public Set<User> getFriends(User user) {
        return Set.of();
    }

    @Override
    public Set<User> getCommonFriends(User user1, User user2) {
        return Set.of();
    }
}
