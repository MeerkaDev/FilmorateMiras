package org.mirasruntime.filmoratemiras.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @EqualsAndHashCode.Include
    private int id;
    
    @NotBlank(message = "Email is necessary.")
    @Email(message = "Incorrect email format.")
    private String email;

    @NotBlank(message = "Login is necessary.")
    @Pattern(regexp = "\\S+", message = "Login can't contain space-symbol.")
    private String login;

    private String name;

    @NotNull(message = "Birthday is necessary.")
    @PastOrPresent(message = "Birthday can't be future date.")
    private LocalDate birthday;

    public String getName() {
        return name == null || name.isBlank() ? login : name;
    }
}
