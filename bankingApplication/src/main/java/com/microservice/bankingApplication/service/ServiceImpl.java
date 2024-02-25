package com.microservice.bankingApplication.service;

import com.microservice.bankingApplication.DTO.ResponseDTO;
import com.microservice.bankingApplication.entity.Transaction;
import com.microservice.bankingApplication.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    @Override
    public ResponseDTO createTransaction(Transaction transaction, String trackingId) {

        transaction.setTransactionId(UUID.randomUUID().toString());
        transactionRepository.save(transaction);
        logger.info("Tracking ID : {} , Transaction Created {} ",trackingId,transaction);

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
    public List<Transaction> getAllTransactions(String trackingId) {
        logger.info("Tracking ID : {} , Displaying all transactions ",trackingId);
        return transactionRepository.findAll();
    }


    @Override
    public List<Transaction> getTransactionsByAccountId(String accountId , String trackingId) {
        logger.info("Tracking ID : {} , Displaying all transactions for account id :{}",trackingId,accountId);
        return transactionRepository.findBySenderAccountId(accountId);
    }
}
