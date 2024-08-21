package com.ali.book_network.user;

import ch.qos.logback.core.subst.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer>{
    Optional<Token> findByToken(String token);
}
