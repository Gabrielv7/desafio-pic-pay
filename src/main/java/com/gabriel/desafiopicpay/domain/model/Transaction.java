package com.gabriel.desafiopicpay.domain.model;

import com.gabriel.desafiopicpay.domain.model.enums.StatusTransaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@Table(name = "TB_TRANSACTION")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID payer;

    @Column(nullable = false)
    private UUID payee;

    @Column(nullable = false)
    private Integer value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTransaction status;

    @Column(nullable = false)
    private Boolean flagEstorno;

    @ManyToOne
    private User user;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;

    protected Transaction() {

    }

    public Transaction(UUID id, UUID payer, UUID payee, Integer value, StatusTransaction status, Boolean flagEstorno, User user, LocalDateTime creationDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.payer = payer;
        this.payee = payee;
        this.value = value;
        this.status = status;
        this.flagEstorno = flagEstorno;
        this.user = user;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public void update(StatusTransaction statusTransaction, Boolean flagEstorno) {
        this.status = statusTransaction;
        this.flagEstorno = flagEstorno;
    }
}
