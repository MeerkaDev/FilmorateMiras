package org.mirasruntime.filmoratemiras.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmTest extends ValidatorTest {

    private Film film;

    @BeforeEach
    void beforeEach() {
        film = new Film();
        film.setId(1L);
        film.setName("Name");
        film.setDescription("Description");
        film.setDuration(120);
        film.setReleaseDate(LocalDate.now());
    }

    @Test
    void setNullName_InvalidMessage() {
        film.setName(null);
        assertEquals("Название не может быть пустым",
                validateAndGetFirstMessageTemplate(film));
    }

    @Test
    void setEmptyName_InvalidMessage() {
        film.setName("");
        assertEquals("Название не может быть пустым",
                validateAndGetFirstMessageTemplate(film));
    }

    @Test
    void setDescription250Symbols_InvalidMessage() {
        film.setDescription("*".repeat(203));
        assertEquals("Максимальная длина описания — 200 символов",
                validateAndGetFirstMessageTemplate(film));
    }

    @Test
    void setInvalidReleaseDate_InvalidMessage() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года",
                validateAndGetFirstMessageTemplate(film));
    }

    @Test
    void setZeroFilmDuration_InvalidMessage() {
        film.setDuration(0);
        assertEquals("Продолжительность фильма должна быть положительной",
                validateAndGetFirstMessageTemplate(film));
    }

    @Test
    void setCorrectProperties_NoErrors() {
        assertTrue(validator.validate(film).isEmpty());
    }
}
