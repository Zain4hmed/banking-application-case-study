package com.microservice.bankingApplication.service;

import com.microservice.bankingApplication.DTO.ResponseDTO;
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
    public ResponseDTO createTransaction(Transaction transaction, String trackingId) {
        transaction.setTransactionId(UUID.randomUUID().toString());
        transactionRepository.save(transaction);

        return new ResponseDTO(
                trackingId,
                transaction.getTransactionId(),
                transaction.getSenderAccountId(),
                transaction.getRecipientAccountId(),
                transaction.getTransactionType(),
                transaction.getTransactionAmount()
        );
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
