package org.mirasruntime.filmoratemiras.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.exception.UserNotFoundException;
import org.mirasruntime.filmoratemiras.model.User;
import org.mirasruntime.filmoratemiras.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

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

        Optional<User> optionalUserFound = userStorage.findById(userId);
        Optional<User> optionalFriendFound = userStorage.findById(friendId);

        if (optionalUserFound.isEmpty() || optionalFriendFound.isEmpty()) {
            throw new UserNotFoundException(
                    String.format("Один или оба пользователя не найдены: userId = %d, friendId = %d", userId, friendId)
            );
        }

        optionalUserFound.ifPresent(user -> user.getFriends().add(friendId));
        optionalFriendFound.ifPresent(user -> user.getFriends().add(userId));
    }

    public void removeFriend(Long userId, Long friendId) {
        log.info("Удаление друга Service");

        Optional<User> optionalUserFound = userStorage.findById(userId);
        Optional<User> optionalFriendFound = userStorage.findById(friendId);

        if (optionalUserFound.isEmpty() || optionalFriendFound.isEmpty()) {
            throw new UserNotFoundException(
                    String.format("Один или оба пользователя не найдены: userId = %d, friendId = %d", userId, friendId)
            );
        }

        optionalUserFound.ifPresent(user -> user.getFriends().remove(friendId));
        optionalFriendFound.ifPresent(user -> user.getFriends().remove(userId));
    }

    public List<User> getFriends(Long userId) {
        log.info("Получение списка друзей поьзователя Service");

        Optional<User> optionalUserFound = userStorage.findById(userId);

        return optionalUserFound.get().getFriends().stream()
                .flatMap(id -> userStorage.findById(id).stream())
                .toList();
    }

    public List<User> getMutualFriends(Long firstUserId, Long secondUserId) {
        log.info("Получение списка общих друзей Service");

        Optional<User> optionalFirstUserFound = userStorage.findById(firstUserId);
        Optional<User> optionalSecondUserFound = userStorage.findById(secondUserId);

        if (optionalFirstUserFound.isPresent() && optionalSecondUserFound.isPresent()) {
            Set<Long> mutualFriendIds = new HashSet<>(optionalFirstUserFound.get().getFriends());
            mutualFriendIds.retainAll(optionalSecondUserFound.get().getFriends());

            return mutualFriendIds.stream()
                    .flatMap(id -> userStorage.findById(id).stream())
                    .toList();
        }
        return Collections.emptyList();
    }
}
