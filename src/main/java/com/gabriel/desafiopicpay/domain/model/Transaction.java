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
    private Integer amount;

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


}

