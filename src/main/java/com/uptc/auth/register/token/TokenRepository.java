package com.uptc.auth.register.token;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/***
 * Redis repository
 */

@Repository
public interface TokenRepository extends CrudRepository <Token, String> {}