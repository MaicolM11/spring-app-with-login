package com.uptc.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

@EnableCaching  // for api cache
@Configuration
public class JedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Bean 
    public JedisConnectionFactory jedisConnectionFactory() { 
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port); 
        return new JedisConnectionFactory(redisStandaloneConfiguration); 
    }

    @Bean 
    public RedisTemplate<String, Object> redisTemplate() { 
        RedisTemplate<String, Object> template = new RedisTemplate<>(); 
        template .setConnectionFactory(jedisConnectionFactory()); 
        return template; 
    }

    @PostConstruct
    public void cleanRedis(){
        redisTemplate().execute( new RedisCallback<Void>() {
            @Override
            public Void doInRedis( RedisConnection connection ) throws DataAccessException {
                connection.flushDb();
                return null;
            }
        });
    }  

}