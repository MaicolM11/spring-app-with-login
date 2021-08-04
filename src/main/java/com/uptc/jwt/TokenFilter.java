package com.uptc.jwt;

import com.uptc.exceptions.ErrorMessage;
import java.io.IOException;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j @RequiredArgsConstructor @Component 
public class TokenFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        boolean loginUrl = request.getServletPath().equals("/login");
        try {
            if (authorizationHeader != null && !loginUrl && authorizationHeader.startsWith("Bearer ")) {  
                String token = authorizationHeader.substring("Bearer ".length());
                List<SimpleGrantedAuthority> validateJwtToken = jwtUtils.validateJwtToken(token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        validateJwtToken.remove(0).getAuthority(), null, validateJwtToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error logging in {}", e.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(FORBIDDEN.value());
            new ObjectMapper().writeValue(response.getOutputStream(),  new ErrorMessage(e, FORBIDDEN.value()));
        }
    }

}
