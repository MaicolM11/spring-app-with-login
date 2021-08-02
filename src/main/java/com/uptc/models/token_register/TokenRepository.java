package com.uptc.models.token_register;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/***
 * Redis repository
 */

@Repository
public interface TokenRepository extends CrudRepository <Token, String> {}