package org.mirasruntime.filmoratemiras.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest extends ValidatorTest {

    private User user;

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setId(1L);
        user.setEmail("valid@mail.com");
        user.setLogin("loginName");
        user.setName("Some name");
        user.setBirthday(LocalDate.of(2000, 1, 1));
    }

    @Test
    void setNullEmail_InvalidMessage() {
        user.setEmail(null);
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                validateAndGetFirstMessageTemplate(user));
    }

    @Test
    void setEmptyEmail_InvalidMessage() {
        user.setEmail("");
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                validateAndGetFirstMessageTemplate(user));
    }

    @Test
    void setEmailWithoutAtSymbol_InvalidMessage() {
        user.setEmail("somemailgmail.com");
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                validateAndGetFirstMessageTemplate(user));
    }

    @Test
    void setInvalidAtPositionInTheEmail_InvalidMessage() {
        user.setEmail("@someemail.com");
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                validateAndGetFirstMessageTemplate(user));
    }

    @Test
    void setNullLogin_InvalidMessage() {
        user.setLogin(null);
        assertEquals("Логин не может быть пустым и содержать пробелы",
                validateAndGetFirstMessageTemplate(user));
    }

    @Test
    void setEmptyLogin_InvalidMessage() {
        user.setLogin("");
        assertEquals("Логин не может быть пустым и содержать пробелы",
                validateAndGetFirstMessageTemplate(user));
    }

    @Test
    void setLoginWithSpaces_InvalidMessage() {
        user.setLogin("super login");
        assertEquals("Логин не может быть пустым и содержать пробелы",
                validateAndGetFirstMessageTemplate(user));
    }

    @Test
    void setBirthdayInTheFuture_InvalidMessage() {
        user.setBirthday(LocalDate.now().plusDays(1));
        assertEquals("Дата рождения не может быть в будущем",
                validateAndGetFirstMessageTemplate(user));
    }

    @Test
    void setCorrectProperties_NoErrors() {
        assertTrue(validator.validate(user).isEmpty());
    }
}
