package com.microservice.bankingApplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

    @Id
    private String transactionId;
    private String senderAccountId;
    private String recipientAccountId;
    private TransactionType transactionType;
    private String transactionAmount;

}
