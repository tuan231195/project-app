package com.tuannguyen.projectapp.auth.model.validators;

import com.tuannguyen.projectapp.auth.model.UserForm;
import com.tuannguyen.projectapp.util.model.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserForm userForm = (UserForm) o;
        String confirmPassword = ObjectUtils.defaultIfNull(userForm.getConfirmPassword(), "").trim();
        String password = ObjectUtils.defaultIfNull(userForm.getPassword(), "").trim();
        if (!password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "password.not-match", "Password does not match");
        }
    }
}
