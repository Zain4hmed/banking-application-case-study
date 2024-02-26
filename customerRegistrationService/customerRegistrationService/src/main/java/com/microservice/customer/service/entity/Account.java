package com.microservice.customer.service.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    private String accountId;
    private String customerId;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @Enumerated(EnumType.STRING)
    private WealthUnit wealthUnit;
    @Enumerated(EnumType.STRING)
    private BusinessType businessType;
    private double currentBalance;
    private double totalWealth;
    private String officeAddress;
    private double totalRevenue;
}
