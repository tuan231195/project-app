package com.tuannguyen.projectapp.auth.repository;

import com.tuannguyen.projectapp.auth.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVerifyRepository extends JpaRepository<VerificationToken, Long> {
    void deleteByUsernameAndType(String username, VerificationToken.TokenType tokenType);
    VerificationToken findByTokenAndType(String token, VerificationToken.TokenType type);
    void deleteByToken(String token);
}
