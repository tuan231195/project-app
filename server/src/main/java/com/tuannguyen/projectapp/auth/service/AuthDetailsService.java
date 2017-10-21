package com.tuannguyen.projectapp.auth.service;

import com.tuannguyen.projectapp.auth.entity.User;
import com.tuannguyen.projectapp.auth.model.VerificationToken;
import com.tuannguyen.projectapp.auth.repository.UserRepository;
import com.tuannguyen.projectapp.auth.repository.UserVerifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public AuthDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.toUserDetails();
        }
        throw new UsernameNotFoundException("Username does not exist");
    }
}
