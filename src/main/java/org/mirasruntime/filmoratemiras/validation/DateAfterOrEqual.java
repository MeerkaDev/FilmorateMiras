package org.mirasruntime.filmoratemiras.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateAfterOrEqualValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAfterOrEqual {
    String message() default "Неверная дата";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String minDate();
}
