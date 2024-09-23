package com.nisum.oppenheimer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPasswordValidator.class)
public @interface ValidPassword {

    String message() default "invalid password";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
