package com.sout.repository;

import com.sout.common.enums.UserStatus;
import com.sout.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndStatus(Long id, UserStatus status);
    Optional<User> findByEmailAndStatus(String email, UserStatus status);
}
