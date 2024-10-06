package org.mirasruntime.filmoratemiras.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.exception.NotFoundException;
import org.mirasruntime.filmoratemiras.exception.ValidationException;
import org.mirasruntime.filmoratemiras.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Set<Film> films = new HashSet<>();
    private int curId = 1;

    @GetMapping
    public Set<Film> getAllFilms() {
        return films;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {

        log.info("Получен запрос POST /films.");

        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Ошибка валидации в запросе POST /films.");
            throw new ValidationException("Release date can't be before 28 december 1895.");
        }

        film.setId(curId);
        films.add(film);
        curId++;
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {

        log.info("Получен запрос PUT /films.");

        if (!films.contains(film)) {
            log.info("Ошибка NotFound PUT /films.");
            throw new NotFoundException("Film is not found.");
        }

        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Ошибка валидации в запросе PUT /films.");
            throw new ValidationException("Release date can't be before 28 december 1895.");
        }

        films.remove(film);
        films.add(film);
        return film;
    }
}