package org.mirasruntime.filmoratemiras.service;

import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.dao.GenreStorage;
import org.mirasruntime.filmoratemiras.exception.GenreNotFoundException;
import org.mirasruntime.filmoratemiras.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GenreService {

    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(@Qualifier("newGenreStorage") GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> findAll() {
        log.info("Получение всех жанров Service");
        return genreStorage.findAll();
    }

    public Optional<Genre> findById(long id) {
        log.info("Поиск жанра по ID Service: {}", id);
        Optional<Genre> genre = genreStorage.findById(id);
        if (genre.isEmpty()) {
            throw new GenreNotFoundException(String.format("Жанр с id = %d не найден", id));
        }
        return genre;
    }
}
