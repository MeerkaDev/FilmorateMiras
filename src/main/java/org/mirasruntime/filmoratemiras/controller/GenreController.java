package org.mirasruntime.filmoratemiras.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.model.Genre;
import org.mirasruntime.filmoratemiras.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<Genre> findAll() {
        log.info("Получение списка всех жанров");
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Genre> findById(@PathVariable Long id) {
        log.info("Получение жанра по id: {}", id);
        return genreService.findById(id);
    }
}
