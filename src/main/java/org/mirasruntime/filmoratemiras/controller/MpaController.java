package org.mirasruntime.filmoratemiras.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.model.Mpa;
import org.mirasruntime.filmoratemiras.service.MpaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaService mpaService;

    @GetMapping
    public List<Mpa> findAll() {
        log.info("Получение списка всех MPA рейтингов");
        return mpaService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Mpa> findById(@PathVariable Long id) {
        log.info("Получение MPA рейтинга по id: {}", id);
        return mpaService.findById(id);
    }
}
