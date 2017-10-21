package com.tuannguyen.projectapp.auth.model.validators;

import com.tuannguyen.projectapp.auth.model.Unique;
import com.tuannguyen.projectapp.util.model.StringUtils;
import com.tuannguyen.projectapp.util.service.CheckExistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    private Unique constraint;
    private final MessageSource messageSource;
    private final CheckExistsService checkExistsService;

    @Autowired
    public UniqueValidator(MessageSource messageSource, CheckExistsService checkExistsService) {
        this.messageSource = messageSource;
        this.checkExistsService = checkExistsService;
    }

    public void initialize(Unique constraint) {
        this.constraint = constraint;
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean exists = checkExistsService.checkExists(constraint.tableName(), constraint.fieldName(), value);
        if (exists) {
            String error = messageSource.getMessage("field.exists", new Object[]{StringUtils.ucFirst(constraint.fieldName())}, Locale.getDefault());
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(error).addConstraintViolation();
        }
        return !exists;
    }
}
