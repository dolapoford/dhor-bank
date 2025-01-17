package com.ahmadluv2code.demo.repository;

import com.ahmadluv2code.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Boolean existsByEmail(String email);
}
