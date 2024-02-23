package com.microservice.bankingApplication.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.microservice.bankingApplication.entity.TransactionType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonPropertyOrder({"TrackingId", "transactionId", "senderAccountId", "recipientAccountId", "transactionType", "transactionAmount"})
public class ResponseDTO {
    private String TrackingId;
    private String transactionId;
    private String senderAccountId;
    private String recipientAccountId;
    private TransactionType transactionType;
    private String transactionAmount;
}
