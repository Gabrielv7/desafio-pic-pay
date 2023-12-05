package com.gabriel.desafiopicpay.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "TB_WALLET")
@Entity
public class Wallet {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @Column(nullable = false)
    private Integer balance;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;

    public Wallet(Integer balance){
        this.balance = balance;
    }

    public boolean balanceIsBiggerThanZero(int value) {
        return balance > 0 && balance >= value;
    }

    public void subtract(int value){
        balance -= value;
    }

    public void sum(int value){
        balance += value;
    }

}


