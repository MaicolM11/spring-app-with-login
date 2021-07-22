package com.uptc.models.entities;

import java.io.Serializable;
import java.time.Duration;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@RedisHash("Token")
@NoArgsConstructor
public class Token implements Serializable {

    public static final Long TIME_EXPIRED = Duration.ofMinutes(1).getSeconds();

    @Id private String token;
    @TimeToLive private Long expiresAt = TIME_EXPIRED;         // ttl, autoremove in redis caché
    private Long userId;

    public Token(String token, Long user) {
        this.token = token;
        this.userId = user;
    }

}