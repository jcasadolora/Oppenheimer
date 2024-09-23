package com.nisum.oppenheimer.validation;

import com.nisum.oppenheimer.util.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Pattern;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Value("${password.regex}")
    private String passwordRegex;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null ) {
            return false;
        }

        if (passwordRegex == null) {
            passwordRegex = Constants.PASSWORD_REGEX;
        }

        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(value).matches();
    }
}
