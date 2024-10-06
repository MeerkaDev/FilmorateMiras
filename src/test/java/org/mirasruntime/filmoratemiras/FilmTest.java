package org.mirasruntime.filmoratemiras;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mirasruntime.filmoratemiras.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {

    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected String validateAndGetFirstMessageTemplate(Film film) {
        return validator.validate(film).stream()
                .findFirst()
                .orElseThrow()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        Film film = new Film();
        film.setName("");
        film.setDescription("Some Text");
        film.setReleaseDate(LocalDate.of(2007, 7, 7));
        film.setDuration(150);

        String expected = "Name is necessary.";

        String message = validateAndGetFirstMessageTemplate(film);

        assertEquals(expected, message);
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsTooLong() {
        Film film = new Film();
        film.setName("Some Text");
        film.setDescription("Q".repeat(201));
        film.setReleaseDate(LocalDate.of(2007, 7, 7));
        film.setDuration(150);

        String expected = "Description can't contain more than 200 symbols.";

        String message = validateAndGetFirstMessageTemplate(film);

        assertEquals(expected, message);
    }

    @Test
    void shouldThrowExceptionWhenDurationIsNotPositive() {
        Film film = new Film();
        film.setName("Some Text");
        film.setDescription("Some Text");
        film.setReleaseDate(LocalDate.of(2007, 7, 7));
        film.setDuration(0);

        String expected = "Film duration must be positive.";

        String message = validateAndGetFirstMessageTemplate(film);

        assertEquals(expected, message);
    }

    @Test
    void shouldThrowExceptionWhenReleaseDateIsNull() {
        Film film = new Film();
        film.setName("Valid name");
        film.setDescription("Some Text");
        film.setReleaseDate(null);
        film.setDuration(150);

        String expected = "Release date is necessary.";

        String message = validateAndGetFirstMessageTemplate(film);

        assertEquals(expected, message);
    }

    /*@Test
    void shouldPassWithValidFilm() {
        Film film = new Film();
        film.setName("Valid name");
        film.setDescription("Some Text");
        film.setReleaseDate(LocalDate.of(2007, 7, 7));
        film.setDuration(150);

        String expected = "";

        String message = validateAndGetFirstMessageTemplate(film);

        assertEquals(expected, message);
    }*/
}
