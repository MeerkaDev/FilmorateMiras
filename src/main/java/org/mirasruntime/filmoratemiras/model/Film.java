package org.mirasruntime.filmoratemiras.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {

    @EqualsAndHashCode.Include
    private int id;

    @NotBlank(message = "Name is necessary.")
    private String name;

    @Size(max = 200, message = "Description can't contain more than 200 symbols.")
    private String description;

    @NotNull(message = "Release date is necessary.")
    private LocalDate releaseDate;

    @Positive(message = "Film duration must be positive.")
    private int duration;
}
