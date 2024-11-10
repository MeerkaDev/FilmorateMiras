package org.mirasruntime.filmoratemiras.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public abstract class ValidatorTest {
    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected <T> String validateAndGetFirstMessageTemplate(T obj) {
        return validator.validate(obj).stream()
                .findFirst()
                .orElseThrow()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }
}
