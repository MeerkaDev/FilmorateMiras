package org.mirasruntime.filmoratemiras.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.exception.NotFoundException;
import org.mirasruntime.filmoratemiras.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Set<User> users = new HashSet<>();
    private int curId = 1;

    @GetMapping
    public Set<User> getAllUsers() {
        return users;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {

        log.info("Получен запрос POST /users.");

        user.setId(curId);
        users.add(user);
        curId++;
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {

        log.info("Получен запрос PUT /users.");

        if (!users.contains(user)) {
            log.info("Ошибка NotFound PUT /users.");
            throw new NotFoundException("User not found.");
        }
        users.remove(user);
        users.add(user);
        return user;
    }
}