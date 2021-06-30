package com.uptc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class JedisConfig {

    @Bean JedisConnectionFactory jedisConnectionFactory() { 
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("127.0.0.1", 6379); 
        return new JedisConnectionFactory(redisStandaloneConfiguration); 
    }

    @Bean public RedisTemplate<String, Object> redisTemplate() { 
        RedisTemplate<String, Object> template = new RedisTemplate<>(); 
        template .setConnectionFactory(jedisConnectionFactory()); 
        return template; 
    }

}