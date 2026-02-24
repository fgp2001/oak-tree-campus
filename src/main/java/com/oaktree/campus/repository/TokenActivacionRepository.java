package com.oaktree.campus.repository;

import com.oaktree.campus.model.TokenActivacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenActivacionRepository extends JpaRepository<TokenActivacion, Long> {
    Optional<TokenActivacion> findByToken(String token);
}
