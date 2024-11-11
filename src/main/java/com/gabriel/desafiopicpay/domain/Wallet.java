package com.gabriel.desafiopicpay.domain;

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

import java.math.BigDecimal;
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
    private BigDecimal balance;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;

    public Wallet(BigDecimal balance){
        this.balance = balance;
    }

    public boolean balanceIsBiggerThanZero(BigDecimal value) {
        return balance.intValue() > 0 && balance.intValue() >= value.intValue();
    }

    public void subtract(BigDecimal value){
        balance = balance.subtract(value);
    }

    public void sum(BigDecimal value){
        balance = balance.add(value);
    }

}


