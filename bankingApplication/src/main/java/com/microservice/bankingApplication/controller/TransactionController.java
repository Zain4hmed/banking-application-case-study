package com.microservice.bankingApplication.controller;

import com.microservice.bankingApplication.DTO.ResponseDTO;
import com.microservice.bankingApplication.entity.Transaction;
import com.microservice.bankingApplication.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping("/debit")
    public ResponseEntity<ResponseDTO> createTransaction(@RequestBody Transaction transaction){
        String trackingId = UUID.randomUUID().toString();
        logger.info("Tracking ID : {} , Request received to create transaction  received.",trackingId);
        return ResponseEntity.ok().body(transactionService.createTransaction(transaction,trackingId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        String trackingId = UUID.randomUUID().toString();
        logger.info("Tracking ID ; {} , Request to get all transactions.",trackingId);
        return ResponseEntity.ok().body(transactionService.getAllTransactions(trackingId));
    }

    @GetMapping("/all/{accountId}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByAccountId(@PathVariable String accountId){
        String trackingId = UUID.randomUUID().toString();
        logger.info("Tracking ID ; {} , Request to Get All Transactions by account id :{}",trackingId,accountId);
        return ResponseEntity.ok().body(transactionService.getTransactionsByAccountId(accountId,trackingId));
    }
}
