package com.tuannguyen.projectapp.auth.repository;

import com.tuannguyen.projectapp.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
