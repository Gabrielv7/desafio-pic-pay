package com.gabriel.desafiopicpay.domain.repository;

import com.gabriel.desafiopicpay.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByDocument(String document);

    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
