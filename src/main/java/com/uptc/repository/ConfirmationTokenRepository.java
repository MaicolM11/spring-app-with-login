package com.uptc.repository;

import java.time.LocalDateTime;
import java.util.Optional;


import com.uptc.models.entities.ConfirmationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/***
 *  save in a db more fast (redis)
 *  Note: every so often (24h) delete expired tokens
 */

@Repository
@Transactional  // DML queries {update, delete...}
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    
    Optional<ConfirmationToken> findByToken(String token);

    @Modifying
    @Query("UPDATE ConfirmationToken c SET c.confirmedAt = ?2 WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);
    
}
