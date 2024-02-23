package com.microservice.bankingApplication.service;

import com.microservice.bankingApplication.DTO.ResponseDTO;
import com.microservice.bankingApplication.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    ResponseDTO createTransaction(Transaction transaction, String trackingId);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByAccountId(String accountId);
}
