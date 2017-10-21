package com.tuannguyen.projectapp.auth.controllers;

import com.tuannguyen.projectapp.auth.entity.User;
import com.tuannguyen.projectapp.auth.model.UserForm;
import com.tuannguyen.projectapp.auth.model.VerificationToken;
import com.tuannguyen.projectapp.auth.model.validators.UserFormValidator;
import com.tuannguyen.projectapp.auth.service.UserService;
import com.tuannguyen.projectapp.core.AppConstant;
import com.tuannguyen.projectapp.core.exception.ApiException;
import com.tuannguyen.projectapp.core.exception.InvalidInputException;
import com.tuannguyen.projectapp.core.model.ViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SignupController {
    private final UserFormValidator userFormValidator;
    private final UserService userService;

    @Autowired
    public SignupController(UserFormValidator userFormValidator, UserService userService) {
        this.userFormValidator = userFormValidator;
        this.userService = userService;
    }

    @RequestMapping("/signup")
    public ViewModel getSignup() {
        return new ViewModel()
                .view("auth/signup")
                .angularApp("tn.auth");
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<List<String>> doSignup(@RequestBody @Valid UserForm userForm, BindingResult bindingResult, HttpSession session, HttpServletRequest request) throws InvalidInputException, ApiException {
        userFormValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            throw new InvalidInputException("Failed to sign up", errors);
        }
        User user = userService.saveUser(userForm, userService.getNewAccessLevel());
        session.setAttribute(AppConstant.SESSION_KEY_EMAIL, user.getEmail());
        String appUrl = (String) request.getAttribute("appUrl");
        try {
            userService.sendConfirmationEmail(user, appUrl, VerificationToken.TokenType.REGISTRATION);
        } catch (MessagingException e) {
            throw new ApiException("API Error", "Failed to send confirmation email");
        }
        return ResponseEntity.created(URI.create("/users/" + user.getId())).build();
    }
}
