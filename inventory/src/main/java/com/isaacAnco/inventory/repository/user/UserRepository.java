package com.isaacAnco.inventory.repository.user;

import com.isaacAnco.inventory.model.user.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE " +
    "LOWER(u.userName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
    "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<User> findAllWithSearch(@Param("search") String search, Pageable pageable);
}
