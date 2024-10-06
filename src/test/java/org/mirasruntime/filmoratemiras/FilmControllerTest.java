package org.mirasruntime.filmoratemiras;

import org.junit.jupiter.api.Test;
import org.mirasruntime.filmoratemiras.controller.FilmController;
import org.mirasruntime.filmoratemiras.exception.ValidationException;
import org.mirasruntime.filmoratemiras.model.Film;

import org.junit.jupiter.api.function.Executable;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {

    FilmController filmController = new FilmController();

    @Test
    void shouldThrowExceptionWhenReleaseDateIsLongTimeAgo() {
        Film film = new Film();
        film.setName("Valid name");
        film.setDescription("Some Text");
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        film.setDuration(150);

        System.out.println(film);
        String expected = "Release date can't be before 28 december 1895.";

        Executable executable = () -> filmController.addFilm(film);

        ValidationException validationException = assertThrows(ValidationException.class, executable);

        assertEquals(expected, validationException.getMessage());
    }
}
