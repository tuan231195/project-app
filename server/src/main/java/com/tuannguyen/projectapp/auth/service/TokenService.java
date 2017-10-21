package com.tuannguyen.projectapp.auth.service;

import com.tuannguyen.projectapp.auth.entity.User;
import com.tuannguyen.projectapp.auth.model.VerificationToken;
import com.tuannguyen.projectapp.auth.repository.UserVerifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenService {

    private final UserVerifyRepository userVerifyRepository;

    @Autowired
    public TokenService(UserVerifyRepository userVerifyRepository) {
        this.userVerifyRepository = userVerifyRepository;
    }

    public String generateToken(User user, VerificationToken.TokenType tokenType) {
        String token = UUID.randomUUID().toString();
        userVerifyRepository.deleteByUsernameAndType(user.getUsername(), tokenType);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setCreated(new Date());
        verificationToken.setUser(user);
        verificationToken.setType(tokenType);
        userVerifyRepository.save(verificationToken);
        return token;
    }

    @Transactional
    public void deleteToken(String token) {
        userVerifyRepository.deleteByToken(token);
    }

}
