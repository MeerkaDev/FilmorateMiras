package org.mirasruntime.filmoratemiras.service;

import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.dao.GenreStorage;
import org.mirasruntime.filmoratemiras.dao.MpaStorage;
import org.mirasruntime.filmoratemiras.exception.FilmNotFoundException;
import org.mirasruntime.filmoratemiras.exception.UserNotFoundException;
import org.mirasruntime.filmoratemiras.exception.ValidationException;
import org.mirasruntime.filmoratemiras.model.Film;
import org.mirasruntime.filmoratemiras.dao.FilmStorage;
import org.mirasruntime.filmoratemiras.dao.UserStorage;
import org.mirasruntime.filmoratemiras.model.Genre;
import org.mirasruntime.filmoratemiras.model.Mpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    private final MpaStorage mpaStorage;

    private final GenreStorage genreStorage;

    @Autowired
    public FilmService(@Qualifier("newFilmStorage") FilmStorage filmStorage,
                       @Qualifier("newUserStorage") UserStorage userStorage,
                       @Qualifier("newGenreStorage") GenreStorage genreStorage,
                       @Qualifier("newMpaStorage") MpaStorage mpaStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreStorage = genreStorage;
        this.mpaStorage = mpaStorage;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(long id) {
        log.info("Вывод найденного фильма Service");

        return filmStorage.findById(id).orElseThrow(() -> new FilmNotFoundException("Фильм не найден"));
    }

    public Film create(Film film) {
        log.info("Заведение нового фильма Service");

        mpaStorage.findById(film.getMpa().getId()).orElseThrow(() -> new ValidationException("MPA не найден"));

        for (Genre genre : film.getGenres()) {
            genreStorage.findById(genre.getId()).orElseThrow(() -> new ValidationException("Жанр не найден"));
        }

        return filmStorage.create(film);
    }

    public Film update(Film film) {
        log.info("Изменение данных фильма Service");
        return filmStorage.update(film);
    }

    public void addLike(Long filmId, Long userId) {
        log.info("Добавление лайка Service");

        // Прямая проверка на существование пользователя
        if (userStorage.findById(userId).isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователь с id = %d не найден", userId));
        }

        // Прямая проверка на существование фильма
        if (filmStorage.findById(filmId).isEmpty()) {
            throw new FilmNotFoundException(String.format("Фильм с id = %d не найден", filmId));
        }

        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        log.info("Удаление лайка Service");

        if (userStorage.findById(userId).isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователь с id = %d не найден", userId));
        }

        if (filmStorage.findById(filmId).isEmpty()) {
            throw new FilmNotFoundException(String.format("Фильм с id = %d не найден", filmId));
        }

        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getTopPopularFilms(Integer count) {
        log.info("Получение списка топ {} популярных фильмов Service", count);
        return filmStorage.getTopFilms(Long.valueOf(count));
    }
}
