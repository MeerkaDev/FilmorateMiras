package org.mirasruntime.filmoratemiras.service;

import lombok.extern.slf4j.Slf4j;
import org.mirasruntime.filmoratemiras.dao.MpaStorage;
import org.mirasruntime.filmoratemiras.exception.MpaNotFoundException;
import org.mirasruntime.filmoratemiras.model.Mpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MpaService {

    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(@Qualifier("newMpaStorage") MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> findAll() {
        log.info("Получение всех рейтингов MPA Service");
        return mpaStorage.findAll();
    }

    public Optional<Mpa> findById(long id) {
        log.info("Поиск рейтинга MPA по ID Service: {}", id);
        Optional<Mpa> mpa = mpaStorage.findById(id);
        if (mpa.isEmpty()) {
            throw new MpaNotFoundException(String.format("Рейтинг MPA с id = %d не найден", id));
        }
        return mpa;
    }
}
