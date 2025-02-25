package org.mirasruntime.filmoratemiras.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    @NotBlank(message = "Электронная почта не может быть пустой и должна содержать символ @")
    @Email(message = "Электронная почта не может быть пустой и должна содержать символ @")
    private String email;

    @NotEmpty(message = "Логин не может быть пустым и содержать пробелы")
    @Pattern(regexp = "\\S+", message = "Логин не может быть пустым и содержать пробелы")
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

//    private Set<Long> friends = new HashSet<>();
}
