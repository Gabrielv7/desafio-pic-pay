package com.gabriel.desafiopicpay.repository;

import com.gabriel.desafiopicpay.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByDocument(String document);

    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
