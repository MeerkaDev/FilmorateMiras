package org.mirasruntime.filmoratemiras.dao.impl;

import lombok.RequiredArgsConstructor;
import org.mirasruntime.filmoratemiras.dao.UserStorage;
import org.mirasruntime.filmoratemiras.exception.UserNotFoundException;
import org.mirasruntime.filmoratemiras.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component("newUserStorage")
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final String SELECT = """
            select * from users
            """;

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SELECT, this::mapRow);
    }

    @Override
    public Optional<User> findById(Long id) {
        String sqlUser = SELECT + " where id = ?";

        User user = jdbcTemplate.query(sqlUser, this::mapRow, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        return Optional.of(user);
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("login", user.getLogin());
        map.put("birthday", user.getBirthday());

        Number number = simpleJdbcInsert.executeAndReturnKey(map);
        user.setId(number.longValue());

        return user;
    }

    @Override
    public User update(User user) {

        if (!checkUserExistence(user.getId())) {
            throw new UserNotFoundException(String.format("Пользователь с id = %d не найден", user.getId()));
        }

        String sqlUserUpdate = """
                update users set name=?,
                email=?,
                login=?,
                birthday=?
                where id=?
                """;

        int recChanged = jdbcTemplate.update(sqlUserUpdate,
                user.getName(), user.getEmail(),
                user.getLogin(), user.getBirthday(),
                user.getId());

        if (recChanged > 0) {
            return user;
        }

        return null;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        String checkSql = """
                select count(*) from friends
                where (user_id = ? and friend_id = ?)
                   or (user_id = ? and friend_id = ?)
                """;

        Integer count = jdbcTemplate.queryForObject(checkSql,
                Integer.class, userId,
                friendId, friendId, userId);

        if (count != null && count == 0) {
            String sqlAddFriend = """
                    insert into friends (
                             user_id,
                             friend_id) values (?, ?)
                    """;
            jdbcTemplate.update(sqlAddFriend, userId, friendId);
        }
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        String sqlRemoveFriend = """
                delete from friends
                where (user_id = ? and friend_id = ?)
                """;

        jdbcTemplate.update(sqlRemoveFriend, userId, friendId);
    }

    @Override
    public Set<User> getFriends(User user) {
        String sqlGetFriends = """
                select u.*
                from users u
                join friends f on u.id = f.friend_id
                where f.user_id = ?
                """;

        return new HashSet<>(jdbcTemplate.query(sqlGetFriends, this::mapRow,
                user.getId()));
    }

    @Override
    public Set<User> getCommonFriends(User user1, User user2) {
        String sqlGetCommonFriends = """
                select u.*
                from users u
                join friends f1 on (u.id = f1.user_id or u.id = f1.friend_id)
                join friends f2 on (u.id = f2.user_id or u.id = f2.friend_id)
                where (f1.user_id = ? or f1.friend_id = ?)
                  and (f2.user_id = ? or f2.friend_id = ?)
                  and u.id != ? and u.id != ?
                """;

        return new HashSet<>(jdbcTemplate.query(sqlGetCommonFriends, this::mapRow,
                user1.getId(), user1.getId(),
                user2.getId(), user2.getId(),
                user1.getId(), user2.getId()));
    }

    private boolean checkUserExistence(long id) {
        String sqlCheckExistance = "select count(*) from users where id = ?";
        Integer count = jdbcTemplate.queryForObject(sqlCheckExistance, Integer.class, id);
        return count != null && count > 0;
    }

    private User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String login = rs.getString("login");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        return new User(id, name, login, email, birthday);
    }
}
