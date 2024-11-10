package org.mirasruntime.filmoratemiras.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.exception.FilmNotFoundException;
import org.mirasruntime.filmoratemiras.exception.UserNotFoundException;
import org.mirasruntime.filmoratemiras.model.Film;
import org.mirasruntime.filmoratemiras.model.User;
import org.mirasruntime.filmoratemiras.storage.FilmStorage;
import org.mirasruntime.filmoratemiras.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Optional<Film> findById(long id) {
        log.info("Вывод найденого фильма Service");

        return filmStorage.findById(id);
    }

    public Film create(Film film) {
        log.info("Заведение нового фильма Service");

        return filmStorage.create(film);
    }

    public Film update(Film film) {
        log.info("Изменение данных фильма фильма Storage");

        return filmStorage.update(film);
    }

    public void addLike(Long filmId, Long userId) {
        log.info("Добавление лайка Service");

        userStorage.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь с id = %d не найден", userId))
        );

        Film filmFound = filmStorage.findById(filmId).orElseThrow(
                () ->
                        new FilmNotFoundException(String.format("Фильм с id = %d не найден", filmId))
        );

        filmFound.getLikes().add(userId);
    }

    public void removeLike(Long filmId, Long userId) {
        log.info("Удаление лайка Service");

        userStorage.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь с id = %d не найден", userId))
        );

        Film filmFound = filmStorage.findById(filmId).orElseThrow(
                () ->
                        new FilmNotFoundException(String.format("Фильм с id = %d не найден", filmId))
        );

        filmFound.getLikes().remove(userId);
    }

    public List<Film> getTopPopularFilms(Integer count) {
        log.info("Получение списка топ {} популярных фильмов Service", count);

        return filmStorage.findAll().stream()
                .sorted(Comparator.comparingInt((Film film) -> film.getLikes().size()).reversed())
                .limit(count)
                .toList();
    }
}
