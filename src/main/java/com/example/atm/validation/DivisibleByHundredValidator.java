package com.example.atm.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DivisibleByHundredValidator implements ConstraintValidator<DivisibleByHundred, Integer> {
    @Override
    public void initialize(DivisibleByHundred constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Cho phép giá trị null (nếu cần)
        }
        return value % 100 == 0;
    }
}
