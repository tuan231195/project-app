package com.tuannguyen.projectapp.auth.service;

import com.tuannguyen.projectapp.auth.entity.AccessLevel;
import com.tuannguyen.projectapp.auth.entity.User;
import com.tuannguyen.projectapp.auth.model.Authority;
import com.tuannguyen.projectapp.auth.model.UserForm;
import com.tuannguyen.projectapp.auth.model.VerificationToken;
import com.tuannguyen.projectapp.auth.repository.AccessRepository;
import com.tuannguyen.projectapp.auth.repository.UserRepository;
import com.tuannguyen.projectapp.core.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final List<AccessLevel> accessLevels;
    private final AccessLevel newAccessLevel;
    private final MessageSource messages;
    private final EmailService emailService;
    private final TokenService tokenService;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, AccessRepository accessRepository, TokenService tokenService, MessageSource messages, EmailService emailService, TokenService tokenService1) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.accessLevels = accessRepository.findAll();
        this.messages = messages;
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.newAccessLevel = find(AccessLevel.NEW);
    }

    public User saveUser(UserForm userForm, AccessLevel accessLevel) {
        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
        User user = UserMapper.map(userForm);
        user.setAccessLevel(accessLevel);
        user.setJoinedDate(new Date());
        return userRepository.save(user);
    }

    public AccessLevel getNewAccessLevel() {
        return newAccessLevel;
    }

    public boolean canAccess(Authentication authentication, String key) {
        AccessLevel accessLevel = find(key);
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        if (accessLevel == null || grantedAuthorities == null || grantedAuthorities.isEmpty()) {
            return false;
        }
        GrantedAuthority grantedAuthority = authentication.getAuthorities().iterator().next();
        if (grantedAuthority instanceof Authority) {
            Authority authority = (Authority) grantedAuthority;
            return authority.getAccessLevel().compareTo(accessLevel) >= 0;
        } else {
            return false;
        }
    }

    private AccessLevel find(String accessLevelName) {
        return this.accessLevels.stream()
                .filter(accessLevel -> accessLevelName.equals(accessLevel.getName()))
                .findFirst().orElse(null);
    }

    @Transactional
    public void sendConfirmationEmail(User user, String appUrl, VerificationToken.TokenType tokenType) throws MessagingException {
        String token = tokenService.generateToken(user, tokenType);
        switch (tokenType) {
            case PASSWORD:
                sendForgotPasswordEmail();
                break;
            default:
                sendRegistrationEmail(user, token, appUrl);
                break;

        }
    }

    private void sendRegistrationEmail(User user, String token, String appUrl) throws MessagingException {
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = appUrl + "verifyRegistration?token=" + token;
        String message = messages.getMessage("message.registration-success", new Object[]{confirmationUrl}, Locale.getDefault());
        emailService.sendHTMLEmail(recipientAddress, subject, message);
    }

    private void sendForgotPasswordEmail() {

    }

    @Transactional
    public void authenticateNewUser(User user, String token) {
        user.setAccessLevel(find("user"));
        tokenService.deleteToken(token);
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken));
    }
}
