package com.ziplink.repository;

import com.ziplink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    Boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
