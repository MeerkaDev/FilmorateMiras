package org.mirasruntime.filmoratemiras.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.exception.UserNotFoundException;
import org.mirasruntime.filmoratemiras.model.User;
import org.mirasruntime.filmoratemiras.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь с id = %d не найден", id))
        );
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable Long id) {
        User user = userService.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь с id = %d не найден", id))
        );
        return userService.getFriends(user.getId());
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getMutualFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getMutualFriends(id, otherId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
        return userService.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь с id = %d не найден", id))
        );
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.removeFriend(id, friendId);
        return userService.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь с id = %d не найден", id))
        );
    }
}