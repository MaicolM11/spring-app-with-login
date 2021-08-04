package com.uptc.jwt;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j @Component
public class JWTUtils {

    @Value("${jwt.secret:mydefaultsecret}")
	private String secret;

    @Value("${jwt.expiration:10}")
	private int expiration;
        
	public String generateJwtToken(String subject, List<?> roles) {
        log.info("Genereting jwt token ...");
		return "Bearer " + JWT.create()
                    .withSubject(subject)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withClaim("roles", roles)
                    .sign(Algorithm.HMAC256(secret.getBytes()));
	}

    /***
     * validate a jwt token and return the decode
     * @param token
     * @return 
     * @throws Exception
     */
	public List<SimpleGrantedAuthority> validateJwtToken(String token) throws Exception {
        List<SimpleGrantedAuthority> values = new ArrayList<>();
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret.getBytes())).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);            
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        values.add(new SimpleGrantedAuthority(username));
        Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return values;
	}
}

