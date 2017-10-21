package com.tuannguyen.projectapp.auth.repository;


import com.tuannguyen.projectapp.auth.entity.AccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRepository extends JpaRepository<AccessLevel, Integer>{

}
