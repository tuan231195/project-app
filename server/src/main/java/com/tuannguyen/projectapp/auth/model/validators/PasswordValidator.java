package com.tuannguyen.projectapp.auth.model.validators;

import com.tuannguyen.projectapp.auth.model.Password;
import com.tuannguyen.projectapp.util.model.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private Password constraint;

    public void initialize(Password constraint) {
        this.constraint = constraint;
    }

    public boolean isValid(String password, ConstraintValidatorContext context) {
        password = ObjectUtils.defaultIfNull(password, "").trim();
        if (password.length() < constraint.minLength() || password.length() > constraint.maxLength()) {
            String errorMessage;
            if (constraint.maxLength() != Integer.MAX_VALUE) {
                errorMessage = String.format("Password must be between %d and %d characters", constraint.minLength(), constraint.maxLength());
            } else {
                errorMessage = "Password must not be less than " + constraint.minLength() + " characters";
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
            return false;
        } else {
            String errorMessage = "Password must have at least one special character";
            String regex = String.format("^(?=.*[\\W])(?=[a-z0-9])[\\w\\W]{%d,%s}$",
                    constraint.minLength(),
                    (constraint.maxLength() != Integer.MAX_VALUE) ? constraint.maxLength() : "");
            if (!password.matches(regex)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
