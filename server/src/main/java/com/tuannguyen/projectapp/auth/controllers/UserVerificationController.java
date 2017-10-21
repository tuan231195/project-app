package com.tuannguyen.projectapp.auth.controllers;

import com.google.common.collect.ImmutableMap;
import com.tuannguyen.projectapp.auth.entity.AccessLevel;
import com.tuannguyen.projectapp.auth.entity.User;
import com.tuannguyen.projectapp.auth.model.VerificationToken;
import com.tuannguyen.projectapp.auth.repository.UserRepository;
import com.tuannguyen.projectapp.auth.repository.UserVerifyRepository;
import com.tuannguyen.projectapp.auth.service.AuthDetailsService;
import com.tuannguyen.projectapp.auth.service.UserService;
import com.tuannguyen.projectapp.core.AppConstant;
import com.tuannguyen.projectapp.core.exception.ApiException;
import com.tuannguyen.projectapp.core.exception.NotFoundException;
import com.tuannguyen.projectapp.core.model.ViewModel;
import com.tuannguyen.projectapp.util.model.ObjectUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class UserVerificationController {

    private final UserRepository userRepository;
    private final UserVerifyRepository userVerifyRepository;
    private final UserService userService;


    public UserVerificationController(UserRepository userRepository, UserVerifyRepository userVerifyRepository, ApplicationEventPublisher eventPublisher, UserService userService) {
        this.userRepository = userRepository;
        this.userVerifyRepository = userVerifyRepository;
        this.userService = userService;
    }

    @RequestMapping("registrationSuccess")
    public ModelAndView registrationSuccess(HttpSession session) {
        String email = (String) session.getAttribute(AppConstant.SESSION_KEY_EMAIL);
        if (email == null) {
            return new ViewModel().view("redirect:/");
        }
        return new ViewModel()
                .angularApp("tn.auth")
                .view("auth/registration-success")
                .angularValues(ImmutableMap.<String, String>builder()
                        .put("email", ObjectUtils.defaultIfNull(email, "")).build());
    }

    @RequestMapping(value = "resendEmail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public void resendEmail(@RequestBody MultiValueMap<String, String> params, HttpServletRequest request) throws NotFoundException, ApiException {
        String email = params.getFirst("email");
        VerificationToken.TokenType tokenType = VerificationToken.TokenType.valueOf(params.getFirst("type"));
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException(User.DISPLAY_TABLE_NAME, "email", email);
        }
        String appUrl = (String) request.getAttribute("appUrl");
        try {
            userService.sendConfirmationEmail(user, appUrl, tokenType);
        } catch (MessagingException e) {
            throw new ApiException("API Error", "Failed to send email");
        }
    }

    @RequestMapping("verifyRegistration")
    public ViewModel confirmRegistrationEmail(@RequestParam("token") String token, RedirectAttributes redirectAttrs) throws NotFoundException {
        VerificationToken verificationToken = userVerifyRepository.findByTokenAndType(token, VerificationToken.TokenType.REGISTRATION);
        if (verificationToken == null) {
            throw new IllegalArgumentException("Invalid token");
        }
        User user = verificationToken.getUser();
        if (!AccessLevel.NEW.equals(user.getAccessLevel().getName())) {
            return new ViewModel().view("redirect: /");
        }
        userService.authenticateNewUser(user, token);
        Date expiryDate = verificationToken.getExpiryDate();
        if (expiryDate.getTime() < new Date().getTime()) {
            return new ViewModel().view("token-expired");
        }
        redirectAttrs.addFlashAttribute("verifySuccess", true);
        return new ViewModel().view("redirect:/login");
    }
}
