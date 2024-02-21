package com.microservice.bankingApplication.service;

import com.microservice.bankingApplication.entity.Transaction;
import com.microservice.bankingApplication.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction.setTransactionId(UUID.randomUUID().toString());
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(String accountId) {
        return transactionRepository.findBySenderAccountId(accountId);
    }
}
