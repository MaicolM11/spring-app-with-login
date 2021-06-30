package com.uptc.repository;

import com.uptc.models.entities.Token;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/***
 * Redis repository
 */

@Repository
public interface TokenRepository extends CrudRepository <Token, String> {}