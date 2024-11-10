package org.mirasruntime.filmoratemiras.errorHandling;

import org.mirasruntime.filmoratemiras.exception.FilmNotFoundException;
import org.mirasruntime.filmoratemiras.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException e) {
        return new ErrorResponse("Пользователь не найден",e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmNotFound(FilmNotFoundException e) {
        return new ErrorResponse("Фильм не найден",e.getMessage());
    }
}
