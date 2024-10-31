package com.gabriel.desafiopicpay.repository;

import com.gabriel.desafiopicpay.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "select * from tb_transaction where payer = :userId and creation_date = :creationDate", nativeQuery = true)
    Transaction findTransactionByUserId(UUID userId, LocalDateTime creationDate);

}
