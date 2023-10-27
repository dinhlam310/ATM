package com.example.atm.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DivisibleByHundredValidator.class)
public @interface DivisibleByHundred {
    String message() default "Total must be divisible by 100";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
