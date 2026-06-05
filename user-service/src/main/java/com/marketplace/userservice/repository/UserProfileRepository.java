package com.marketplace.userservice.repository;

import com.marketplace.userservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserProfileRepository
        extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUserId(Long userId);

    Optional<UserProfile> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserProfile> findByRole(UserProfile.UserRole role);

    List<UserProfile> findByActiveTrue();
}