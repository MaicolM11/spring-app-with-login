package com.uptc.repository;

import java.util.Optional;

import com.uptc.models.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional  // use for DML query
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Modifying
    @Query("UPDATE User a SET a.enabled = TRUE WHERE a.id = ?1")
    int enableUser(Long id);

}