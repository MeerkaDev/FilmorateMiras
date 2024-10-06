package org.mirasruntime.filmoratemiras;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mirasruntime.filmoratemiras.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected String validateAndGetFirstMessageTemplate(User user) {
        return validator.validate(user).stream()
                .findFirst()
                .orElseThrow()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }

    @Test
    void shouldThrowExceptionWhenEmailIsBlank() {
        User user = new User();
        user.setEmail("");
        user.setLogin("SomeText");
        user.setName("SomeText");
        user.setBirthday(LocalDate.of(2007, 7, 7));

        String expected = "Email is necessary.";

        String message = validateAndGetFirstMessageTemplate(user);

        assertEquals(expected, message);
    }

    @Test
    void shouldThrowExceptionWhenEmailWithoutAtSymbol() {
        User user = new User();
        user.setEmail("asdasd");
        user.setLogin("SomeText");
        user.setName("SomeText");
        user.setBirthday(LocalDate.of(2007, 7, 7));

        String expected = "Incorrect email format.";

        String message = validateAndGetFirstMessageTemplate(user);

        assertEquals(expected, message);
    }

    @Test
    void shouldThrowExceptionWhenLoginIsBlank() {
        User user = new User();
        user.setEmail("some@text.me");
        user.setLogin("");
        user.setName("SomeText");
        user.setBirthday(LocalDate.of(2007, 7, 7));

        String expected = "Login is necessary.";

        String message = validateAndGetFirstMessageTemplate(user);

        assertEquals(expected, message);
    }

    @Test
    void shouldThrowExceptionWhenLoginContainsSpaceSymbol() {
        User user = new User();
        user.setEmail("some@text.me");
        user.setLogin("Some Text");
        user.setName("SomeText");
        user.setBirthday(LocalDate.of(2007, 7, 7));

        String expected = "Login can't contain space-symbol.";

        String message = validateAndGetFirstMessageTemplate(user);

        assertEquals(expected, message);
    }

    @Test
    void shouldThrowExceptionWhenBirthdayIsNull() {
        User user = new User();
        user.setEmail("some@text.me");
        user.setLogin("SomeText");
        user.setName("SomeText");
        user.setBirthday(null);

        String expected = "Birthday is necessary.";

        String message = validateAndGetFirstMessageTemplate(user);

        assertEquals(expected, message);
    }

    @Test
    void shouldThrowExceptionWhenBirthdayIsFutureDate() {
        User user = new User();
        user.setEmail("some@text.me");
        user.setLogin("SomeText");
        user.setName("SomeText");
        user.setBirthday(LocalDate.now().plusDays(1));

        String expected = "Birthday can't be future date.";

        String message = validateAndGetFirstMessageTemplate(user);

        assertEquals(expected, message);
    }

    @Test
    void shouldReturnLoginIfNameIsEmpty() {
        User user = new User();
        user.setEmail("some@text.me");
        user.setLogin("Login");
        user.setName("");
        user.setBirthday(LocalDate.now().plusDays(1));

        String expected = "Login";

        String message = user.getName();

        assertEquals(expected, message);
    }
}
