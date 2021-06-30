package com.uptc.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@RedisHash("Token")
@NoArgsConstructor
public class Token implements Serializable {

    @Id private String token;
    private LocalDateTime createdAt;
    @TimeToLive private Long expiresAt;         // ttl, autoremove in redis caché
    private LocalDateTime confirmedAt;
    private Long userId;

    public Token(String token, LocalDateTime createdAt, Long expiresAt, Long user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.userId = user;
    }

}