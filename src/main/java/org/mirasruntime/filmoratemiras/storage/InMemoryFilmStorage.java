package org.mirasruntime.filmoratemiras.storage;

import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.dao.FilmStorage;
import org.mirasruntime.filmoratemiras.exception.FilmNotFoundException;
import org.mirasruntime.filmoratemiras.model.Film;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final List<Film> films = new ArrayList<>();
    private long uniqueId = 0;

    @Override
    public List<Film> findAll() {
        log.info("Вывод всех фильмов из Storage");

        return films;
    }

    @Override
    public Optional<Film> findById(Long id) {
        log.info("Вывод найденого фильма Storage");

        return films.stream().filter(film -> film.getId().equals(id)).findFirst();
    }

    @Override
    public Film create(Film film) {
        log.info("Заведение нового фильма Storage");

        film.setId(++uniqueId);
        films.add(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        log.info("Изменение данных фильма фильма Storage");

        Optional<Film> optionalFilmFound = findById(film.getId());

        if (optionalFilmFound.isPresent()) {
            Film filmToUpdate = optionalFilmFound.get();

            filmToUpdate.setId(film.getId());
            filmToUpdate.setName(film.getName());
            filmToUpdate.setDescription(film.getDescription());
            filmToUpdate.setReleaseDate(film.getReleaseDate());
            filmToUpdate.setDuration(film.getDuration());

            return filmToUpdate;
        } else {
            throw new FilmNotFoundException(String.format("Фильм с id = %d не найден", film.getId()));
        }
    }
}
