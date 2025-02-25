package org.mirasruntime.filmoratemiras.service;

import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.exception.UserNotFoundException;
import org.mirasruntime.filmoratemiras.model.User;
import org.mirasruntime.filmoratemiras.dao.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("newUserStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> findAll() {
        log.info("Вывод списка всех пользователей Service");
        return userStorage.findAll();
    }

    public Optional<User> findById(Long id) {
        log.info("Вывод найденого пользователя Service");
        return userStorage.findById(id);
    }

    public User create(User user) {
        log.info("Заведение нового пользователя Service");
        return userStorage.create(user);
    }

    public User update(User user) {
        log.info("Изменение данных пользователя Service");
        return userStorage.update(user);
    }

    public void addFriend(Long userId, Long friendId) {
        log.info("Добавление друга Service");
        if (userStorage.findById(userId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден: " + userId);
        }
        if (userStorage.findById(friendId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден: " + friendId);
        }
        userStorage.addFriend(userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        log.info("Удаление друга Service");
        if (userStorage.findById(userId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден: " + userId);
        }
        if (userStorage.findById(friendId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден: " + friendId);
        }
        userStorage.removeFriend(userId, friendId);
    }

    public Set<User> getFriends(Long userId) {
        log.info("Получение списка друзей пользователя Service");
        Optional<User> user = userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден: " + userId);
        }
        return userStorage.getFriends(user.get());
    }

    public Set<User> getCommonFriends(Long userId, Long friendId) {
        log.info("Получение списка общих друзей Service");
        Optional<User> user1 = userStorage.findById(userId);
        Optional<User> user2 = userStorage.findById(friendId);
        if (user1.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден: " + userId);
        }
        if (user2.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден: " + friendId);
        }
        return userStorage.getCommonFriends(user1.get(), user2.get());
    }
}
